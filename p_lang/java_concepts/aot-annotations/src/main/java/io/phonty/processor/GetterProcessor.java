package io.phonty.processor;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.Set;

@SupportedAnnotationTypes("io.phonty.annotation.Getter")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class GetterProcessor extends AbstractProcessor {

    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        JavacProcessingEnvironment javacEnv = (JavacProcessingEnvironment) processingEnv;
        Context context = javacEnv.getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
        this.trees = JavacTrees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element annotated : roundEnv.getElementsAnnotatedWith(io.phonty.annotation.Getter.class)) {
            if (annotated.getKind() != ElementKind.CLASS) continue;

            JCClassDecl classDecl = (JCClassDecl) trees.getTree(annotated);
            for (Element enclosed : annotated.getEnclosedElements()) {
                if (enclosed.getKind() == ElementKind.FIELD && !enclosed.getModifiers().contains(Modifier.STATIC)) {
                    addGetter(classDecl, (VariableElement) enclosed);
                }
            }
        }
        return true;
    }

    private void addGetter(JCClassDecl classDecl, VariableElement field) {
        Name fieldName = names.fromString(field.getSimpleName().toString());
        String getterNameStr = "get" +
                Character.toUpperCase(fieldName.toString().charAt(0)) +
                fieldName.toString().substring(1);
        Name getterName = names.fromString(getterNameStr);

        // return this.fieldName;
        JCTree.JCExpression returnExpr = treeMaker.Select(
                treeMaker.Ident(names.fromString("this")), fieldName);
        JCTree.JCReturn returnStatement = treeMaker.Return(returnExpr);

        // { return this.fieldName; }
        JCTree.JCBlock body = treeMaker.Block(0, List.of(returnStatement));

        // method declaration
        JCMethodDecl getter = treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC),
                getterName,
                treeMaker.Type((com.sun.tools.javac.code.Type) ((Symbol.VarSymbol) field).asType()),
                List.nil(),
                List.nil(),
                List.nil(),
                body,
                null
        );

        // insert method at top of class body
        classDecl.defs = classDecl.defs.prepend(getter);
    }
}

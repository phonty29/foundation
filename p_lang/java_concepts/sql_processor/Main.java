package p_lang.java_concepts.sql_processor;

import java.util.List;
import java.util.Objects;

import p_lang.java_concepts.sql_processor.lexer.SqlLexer;
import p_lang.java_concepts.sql_processor.lexer.Token;
import p_lang.java_concepts.sql_processor.parser.SqlParser;
import p_lang.java_concepts.sql_processor.parser.ast.BinaryOp;
import p_lang.java_concepts.sql_processor.parser.ast.CreateStmt;
import p_lang.java_concepts.sql_processor.parser.ast.Identifier;
import p_lang.java_concepts.sql_processor.parser.ast.Literal;
import p_lang.java_concepts.sql_processor.parser.ast.SelectStmt;
import p_lang.java_concepts.sql_processor.parser.ast.TableRef;

public class Main {
    public static void main(String... args) {
        String sql = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER DEFAULT 18);";
        SqlLexer lex = new SqlLexer(sql);
        List<Token> toks = lex.tokenize();
        SqlParser p = new SqlParser(toks);
        CreateStmt s = p.parseCreateStmt();

        System.out.println(s.tableName());
        s.columns().forEach(col -> {
            System.out.println(col.name());
            col.constraints().forEach(System.out::println);
        });

        // BinaryOp[
        //  op=AND, 
        //  left=BinaryOp[op='>', left=Identifier[name=age], right=Literal[value=10]], 
        //  right=BinaryOp[op='=', left=Identifier[name=name], right=Literal[value=Chanco]]
        // ]
        // BinaryOp where = (BinaryOp) s.where();
        // System.out.println("WHERE (");
        // if (where.op().contentEquals("and")) {
        //     BinaryOp leftBinaryOp = (BinaryOp) where.left();
        //     BinaryOp rightBinaryOp = (BinaryOp) where.right();
        //     if (leftBinaryOp.op().contentEquals(">")) {
        //         Identifier ident = (Identifier) leftBinaryOp.left();
        //         Literal literal = (Literal) leftBinaryOp.right();
        //         System.out.println(String.format("%s > %d", ident.name(), literal.value()));
        //     }
        //     if (rightBinaryOp.op().contentEquals("=")) {
        //         Identifier ident = (Identifier) rightBinaryOp.left();
        //         Literal literal = (Literal) rightBinaryOp.right();
        //         System.out.println(String.format("%s = %s", ident.name(), literal.value()));
        //     }
        // }
        // System.out.println(")");
        // System.out.println("FROM: " + s.from());
    }
}

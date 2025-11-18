package p_lang.java_concepts.sql_processor;

import java.util.List;

import p_lang.java_concepts.sql_processor.lexer.SqlLexer;
import p_lang.java_concepts.sql_processor.lexer.Token;
import p_lang.java_concepts.sql_processor.parser.SqlParser;
import p_lang.java_concepts.sql_processor.parser.ast.BinaryOp;
import p_lang.java_concepts.sql_processor.parser.ast.CreateStmt;
import p_lang.java_concepts.sql_processor.parser.ast.Identifier;
import p_lang.java_concepts.sql_processor.parser.ast.Literal;
import p_lang.java_concepts.sql_processor.parser.ast.SelectStmt;

public class Main {
    public static void main(String... args) {
        String sql = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER DEFAULT 18);";
        SqlLexer lex = new SqlLexer(sql);
        List<Token> toks = lex.tokenize();
        SqlParser p = new SqlParser(toks);
        CreateStmt cs = p.parseCreateStmt();

        System.out.println(cs.tableName());
        cs.columns().forEach(col -> {
            System.out.println(col.name());
            col.constraints().forEach(System.out::println);
        });

        sql = "SELECT name from users where age > 25 and name = 'Chanco'";
        lex = new SqlLexer(sql);
        toks = lex.tokenize();
        p = new SqlParser(toks);
        SelectStmt ss = p.parseSelect();

        System.out.println("SELECT (");
        ss.selectList().forEach(System.out::println);
        System.out.println(")");
        // BinaryOp[
        //  op=AND, 
        //  left=BinaryOp[op='>', left=Identifier[name=age], right=Literal[value=10]], 
        //  right=BinaryOp[op='=', left=Identifier[name=name], right=Literal[value=Chanco]]
        // ]
        BinaryOp where = (BinaryOp) ss.where();
        System.out.println("WHERE (");
        if (where.op().contentEquals("and")) {
            BinaryOp leftBinaryOp = (BinaryOp) where.left();
            BinaryOp rightBinaryOp = (BinaryOp) where.right();
            if (leftBinaryOp.op().contentEquals(">")) {
                Identifier ident = (Identifier) leftBinaryOp.left();
                Literal literal = (Literal) leftBinaryOp.right();
                System.out.println(String.format("%s > %d", ident.name(), literal.value()));
            }
            if (rightBinaryOp.op().contentEquals("=")) {
                Identifier ident = (Identifier) rightBinaryOp.left();
                Literal literal = (Literal) rightBinaryOp.right();
                System.out.println(String.format("%s = %s", ident.name(), literal.value()));
            }
        }
        System.out.println(")");
        System.out.println("FROM: " + ss.from());
    }
}

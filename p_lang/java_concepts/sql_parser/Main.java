package p_lang.java_concepts.sql_parser;

import java.util.List;

public class Main {
    public static void main(String... args) {
        String sql = "SELECT id, name AS n FROM users WHERE age > 20";
        SqlLexer lex = new SqlLexer(sql);
        List<Token> toks = lex.tokenize();
        SqlParser p = new SqlParser(toks);
        SelectStmt s = p.parseSelect();

        System.out.println(s.selectList().size());
        System.out.println(s.where() instanceof BinaryOp);
    }
    
}

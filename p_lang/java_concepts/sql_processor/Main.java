package p_lang.java_concepts.sql_processor;

import java.util.List;
import java.util.Map;

import p_lang.java_concepts.sql_processor.lexer.SqlLexer;
import p_lang.java_concepts.sql_processor.lexer.Token;
import p_lang.java_concepts.sql_processor.parser.SqlParser;
import p_lang.java_concepts.sql_processor.parser.ast.SelectStmt;

public class Main {
    private final static List<Row> rows = List.of(
        new Row(Map.of(
            "name", "Islam Makhachev",
            "age", 35,
            "city", "Hushet"
        )),
        new Row(Map.of(
            "name", "Arman Tsarukyan",
            "age", 29,
            "city", "Akhalkalaki"
        )),
        new Row(Map.of(
            "name", "Abdul-Aziz Abdulvakhabov",
            "age", 36,
            "city", "Sernovodskoe"
        ))
    );

    public static void main(String... args) {
        String sql = "select name from users where age > 35";
        SqlLexer lex = new SqlLexer(sql);
        List<Token> toks = lex.tokenize();
        SqlParser p = new SqlParser(toks);
        SelectStmt ss = p.parseSelect();

        for (Row row : rows) {
            if (ss.where().eval(row)) {
                System.out.println(row.get("name"));
            }
        }

    }
}

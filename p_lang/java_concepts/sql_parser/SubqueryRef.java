package p_lang.java_concepts.sql_parser;

public record SubqueryRef(SelectStmt sub, String alias) implements FromItem {}
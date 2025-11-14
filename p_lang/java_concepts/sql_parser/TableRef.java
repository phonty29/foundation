package p_lang.java_concepts.sql_parser;

public record TableRef(String name, String alias) implements FromItem {}

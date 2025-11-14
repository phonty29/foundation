package p_lang.java_concepts.sql_processor.parser.ast;

public record TableRef(String name, String alias) implements FromItem {}

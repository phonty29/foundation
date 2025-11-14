package p_lang.java_concepts.sql_processor.parser.ast;

public record SelectItem(Expression expr, String alias) {}
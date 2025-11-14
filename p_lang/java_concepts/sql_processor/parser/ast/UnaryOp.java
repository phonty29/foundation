package p_lang.java_concepts.sql_processor.parser.ast;

public record UnaryOp(String op, Expression expr) implements Expression {}

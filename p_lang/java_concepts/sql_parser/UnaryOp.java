package p_lang.java_concepts.sql_parser;

public record UnaryOp(String op, Expression expr) implements Expression {}

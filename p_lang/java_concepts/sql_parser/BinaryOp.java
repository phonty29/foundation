package p_lang.java_concepts.sql_parser;

public record BinaryOp(String op, Expression left, Expression right) implements Expression {}

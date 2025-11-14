package p_lang.java_concepts.sql_processor.parser.ast;

public record BinaryOp(String op, Expression left, Expression right) implements Expression {}

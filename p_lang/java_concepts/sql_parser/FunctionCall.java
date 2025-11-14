package p_lang.java_concepts.sql_parser;

import java.util.List;

public record FunctionCall(String name, List<Expression> args) implements Expression {}

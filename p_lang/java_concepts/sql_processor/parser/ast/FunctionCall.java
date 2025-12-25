package p_lang.java_concepts.sql_processor.parser.ast;

import java.util.List;

import p_lang.java_concepts.sql_processor.Row;

public record FunctionCall(String name, List<Expression> args) implements Expression {

    @Override
    public boolean eval(Row row) {
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }}

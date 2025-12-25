package p_lang.java_concepts.sql_processor.parser.ast;

import p_lang.java_concepts.sql_processor.Row;

public record Identifier(String name) implements Expression {

    @Override
    public boolean eval(Row row) {
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }}

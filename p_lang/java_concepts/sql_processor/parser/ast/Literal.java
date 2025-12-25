package p_lang.java_concepts.sql_processor.parser.ast;

import p_lang.java_concepts.sql_processor.Row;

public record Literal(Object value) implements Expression {

    @Override
    public boolean eval(Row row) {
        return (boolean) value;
    }}

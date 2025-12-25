package p_lang.java_concepts.sql_processor.parser.ast;

import p_lang.java_concepts.sql_processor.Row;

public interface Expression {
    boolean eval(Row row);
}

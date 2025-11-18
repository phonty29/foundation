package p_lang.java_concepts.sql_processor.parser.ast;

import java.util.List;

public record Column (
    String name,
    ColumnType type,
    List<Constraint> constraints
) {
    
}

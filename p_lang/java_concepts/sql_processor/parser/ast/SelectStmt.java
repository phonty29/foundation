package p_lang.java_concepts.sql_processor.parser.ast;

import java.util.List;

public record SelectStmt(
    List<SelectItem> selectList, 
    FromItem from, 
    Expression where, 
    List<OrderItem> order, 
    Integer limit, 
    Integer offset
) implements Statement {}
package p_lang.java_concepts.sql_processor.parser.ast;

public record SubqueryRef(SelectStmt sub, String alias) implements FromItem {}
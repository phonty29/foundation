package p_lang.java_concepts.sql_parser;

import java.util.List;

public record SelectStmt(List<SelectItem> selectList, FromItem from, Expression where) implements Statement {}
package p_lang.java_concepts.sql_processor;

import java.util.Map;

public class Row {
    private Map<String, Object> values;

    Row(Map<String, Object> values) {
        this.values = values;
    }

    public Object get(String col) {
        return this.values.get(col);
    }
}

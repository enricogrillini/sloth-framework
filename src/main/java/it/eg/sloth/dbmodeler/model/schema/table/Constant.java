package it.eg.sloth.dbmodeler.model.schema.table;

import lombok.Builder;
import lombok.Data;

@Data
public class Constant {

    String name;
    Object values;

    @Builder
    public Constant(String name, Object values) {
        this.name = name;
        this.values = values;
    }

    public Constant() {
        this(null, null);
    }
}

package it.eg.sloth.dbmodeler.model.schema.table;

import lombok.Builder;
import lombok.Data;

@Data
public class Constant {

    String name;
    Object value;

    @Builder
    public Constant(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Constant() {
        this(null, null);
    }
}

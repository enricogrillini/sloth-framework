package it.eg.sloth.dbmodeler.model.schema.code;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class StoredProcedure extends Method {
    private String definition;

    public StoredProcedure() {
        this(null, null);
    }

    public StoredProcedure(String name, String definition) {
        super(name);
        this.definition = definition;
    }
}

package it.eg.sloth.dbmodeler.model.schema.code;

import lombok.Data;

@Data
public class Function extends StoredProcedure {

    private String returnType;

    public Function() {
        super(null, null);
    }

    public Function(String name, String definition) {
        super(name, definition);
    }


}

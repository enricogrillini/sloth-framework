package it.eg.sloth.dbmodeler.model.schema.code;

public class Function extends StoredProcedure {

    public Function() {
        super(null, null);
    }

    public Function(String name, String definition) {
        super(name, definition);
    }

}

package it.eg.sloth.dbmodeler.model.schema.code;

public class Procedure extends StoredProcedure {

    public Procedure() {
        this(null, null);
    }

    public Procedure(String name, String definition) {
        super(name, definition);
    }
}
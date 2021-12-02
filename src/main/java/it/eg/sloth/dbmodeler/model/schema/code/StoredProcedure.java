package it.eg.sloth.dbmodeler.model.schema.code;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class StoredProcedure extends Method {
    private String definition;

    protected StoredProcedure() {
        this(null, null);
    }

    protected StoredProcedure(String name, String definition) {
        super(name);
        this.definition = definition;
    }

    public enum Type {
        FUNCTION, PROCEDURE, PACKAGE
    }

    public static class Factory {

        private Factory() {
            // NOP
        }

        public static StoredProcedure newStoredProcedure(String name, Type type, String definition) {
            switch (type) {
                case FUNCTION:
                    return new Function(name, definition);
                case PROCEDURE:
                    return new Procedure(name, definition);
                case PACKAGE:
                    return new Package(name, definition);
                default:
                    return null;
            }
        }
    }
}

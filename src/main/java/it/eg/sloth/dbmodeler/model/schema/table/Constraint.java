package it.eg.sloth.dbmodeler.model.schema.table;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class Constraint {

    private String name;
    private ConstraintType type;
    private String searchCondition;
    private boolean generated;
    private String referenceTable;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> columns;

    @Builder
    public Constraint(String name, ConstraintType type, String searchCondition, boolean generated, String referenceTable) {
        this.name = name;
        this.type = type;
        this.searchCondition = searchCondition;
        this.generated = generated;
        this.referenceTable = referenceTable;

        columns = new ArrayList<>();
    }

    public Constraint() {
        this(null, null, null, false, null);
    }

    // Columns
    public Collection<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public void addColumn(String columnName) {
        columns.add(columnName);
    }

}

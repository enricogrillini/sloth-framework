package it.eg.sloth.dbmodeler.model.schema.table;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class Index {
    String name;
    boolean uniqueness;
    private String tablespace;
    private Long initial;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> columns;

    public Index() {
        this(null, false, null, null);
    }

    @Builder
    public Index(String name, boolean uniqueness, String tablespace, Long initial) {
        this.name = name;
        this.uniqueness = uniqueness;
        this.tablespace = tablespace;
        this.initial = initial;

        columns = new ArrayList<>();
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

package it.eg.sloth.dbmodeler.model.schema.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Table {

    private String name;
    private String description;
    private String tablespace;
    private Long initial;
    private Boolean temporary;
    private String duration;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, TableColumn> tableColumnMap;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Constraint> constraintMap;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Index> indexMap;

    public Table() {
        this(null, null, null, null, null, null);
    }

    @Builder
    public Table(String name, String description, String tablespace, Long initial, Boolean temporary, String duration) {
        this.name = name;
        this.description = description;
        this.tablespace = tablespace;
        this.initial = initial;
        this.temporary = temporary;
        this.duration = duration;

        tableColumnMap = new LinkedHashMap<>();
        constraintMap = new LinkedHashMap<>();
        indexMap = new LinkedHashMap<>();
    }

    // TableColumn
    public Collection<TableColumn> getTableColumnCollection() {
        return tableColumnMap.values();
    }

    @JsonIgnore
    public Collection<TableColumn> getPrimaryKeyCollection() {
        return getTableColumnCollection().stream()
                .filter(TableColumn::isPrimaryKey)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<TableColumn> getPlainColumnCollection() {
        return getTableColumnCollection().stream()
                .filter(TableColumn::isPlain)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<TableColumn> getClobColumnCollection() {
        return getTableColumnCollection().stream()
                .filter(TableColumn::isClob)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<TableColumn> getBlobColumnCollection() {
        return getTableColumnCollection().stream()
                .filter(TableColumn::isBlob)
                .collect(Collectors.toList());
    }

    public void setTableColumnCollection(Collection<TableColumn> tableColumnCollection) {
        tableColumnCollection.forEach(this::addTableColumn);
    }

    public Table addTableColumn(TableColumn tableColumn) {
        tableColumnMap.put(tableColumn.getName().toLowerCase(), tableColumn);
        return this;
    }

    public TableColumn getTableColumn(String tableColumnName) {
        return tableColumnMap.get(tableColumnName.toLowerCase());
    }

    public TableColumn getTableColumn(int index) {
        return (TableColumn) tableColumnMap.values().toArray()[index];
    }


    // Constraint
    public Collection<Constraint> getConstraintCollection() {
        return constraintMap.values();
    }

    public void setConstraintCollection(Collection<Constraint> constraintCollection) {
        constraintCollection.forEach(this::addConstraint);
    }

    public Table addConstraint(Constraint constraint) {
        constraintMap.put(constraint.getName().toLowerCase(), constraint);
        return this;
    }

    public Constraint getConstraint(String constraintName) {
        return constraintMap.get(constraintName.toLowerCase());
    }

    // Indexes
    public Collection<Index> getIndexCollection() {
        return indexMap.values();
    }

    public void setIndexCollection(Collection<Index> indexCollection) {
        indexCollection.forEach(this::addIndex);
    }

    public Table addIndex(Index index) {
        indexMap.put(index.getName().toLowerCase(), index);
        return this;
    }

    public Index getIndex(String indexName) {
        return indexMap.get(indexName.toLowerCase());
    }

}

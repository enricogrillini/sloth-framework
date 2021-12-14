package it.eg.sloth.dbmodeler.model.schema.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Singleton per la gestione delle Scedulazioni
 *
 * @author Enrico Grillini
 */
@Data
public class Table {

    public static final String DECODE_PREFIX = "DEC_";

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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Constant> constantMap;

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
        constantMap = new LinkedHashMap<>();
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

    @JsonIgnore
    public Collection<TableColumn> getLobColumnCollection() {
        return getTableColumnCollection().stream()
                .filter(TableColumn::isLob)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<TableColumn> getByteaColumnCollection() {
        return getTableColumnCollection().stream()
                .filter(TableColumn::isByteA)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public boolean isDecodeTable() {
        return this.getName().toUpperCase().contains(DECODE_PREFIX);
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

    // Constants
    public Collection<Constant> getConstantCollection() {
        return constantMap.values();
    }

    public void setConstantCollection(Collection<Constant> constantCollection) {
        constantCollection.forEach(this::addConstant);
    }

    public Table addConstant(Constant constant) {
        constantMap.put(constant.getName().toLowerCase(), constant);
        return this;
    }

    public Constant getConstant(String constantName) {
        return constantMap.get(constantName.toLowerCase());
    }


}

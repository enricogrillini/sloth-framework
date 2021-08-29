package it.eg.sloth.dbmodeler.model.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.eg.sloth.dbmodeler.model.schema.code.Function;
import it.eg.sloth.dbmodeler.model.schema.code.Procedure;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Constraint;
import it.eg.sloth.dbmodeler.model.schema.table.ConstraintType;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.schema.view.View;
import lombok.ToString;

import java.util.*;

@ToString
public class Schema {
    private Map<String, Table> tableMap;
    private Map<String, View> viewMap;
    private Map<String, Sequence> sequenceMap;
    private List<Function> functionList;
    private List<Procedure> procedureList;

    public Schema() {
        tableMap = new LinkedHashMap<>();
        viewMap = new LinkedHashMap<>();
        sequenceMap = new LinkedHashMap<>();
        functionList = new ArrayList<>();
        procedureList = new ArrayList<>();
    }

    // Table
    public Collection<Table> getTableCollection() {
        return tableMap.values();
    }

    public void setTableCollection(Collection<Table> tableCollection) {
        for (Table table : tableCollection) {
            addTable(table);
        }
    }

    public Schema addTable(Table table) {
        tableMap.put(table.getName().toLowerCase(), table);
        return this;
    }

    public Table getTable(String tableName) {
        return tableMap.get(tableName.toLowerCase());
    }

    // Foreign Key Table
    @JsonIgnore
    public Collection<Table> getForeignKeyTableCollection(Table table) {
        Set<Table> result = new HashSet<>();
        for (Table foreignKeyTable : tableMap.values()) {
            for (Constraint constraint : foreignKeyTable.getConstraintCollection()) {
                if (constraint.getType() == ConstraintType.FOREIGN_KEY && table.getName().equalsIgnoreCase(constraint.getReferenceTable())) {
                    result.add(foreignKeyTable);
                    break;
                }
            }

        }

        return result;
    }

    // View
    public Collection<View> getViewCollection() {
        return viewMap.values();
    }

    public void setViewCollection(Collection<View> viewCollection) {
        for (View view : viewCollection) {
            addView(view);
        }
    }

    public Schema addView(View view) {
        viewMap.put(view.getName().toLowerCase(), view);
        return this;
    }

    public View getView(String viewName) {
        return viewMap.get(viewName.toLowerCase());
    }

    // Sequence
    public Collection<Sequence> getSequenceCollection() {
        return sequenceMap.values();
    }

    public void setSequenceCollection(Collection<Sequence> sequenceCollection) {
        for (Sequence sequence : sequenceCollection) {
            addSequence(sequence);
        }
    }

    public Sequence getSequence(String sequenceName) {
        return sequenceMap.get(sequenceName.toLowerCase());
    }

    public Schema addSequence(Sequence sequence) {
        sequenceMap.put(sequence.getName().toLowerCase(), sequence);
        return this;
    }

    // Function
    public Collection<Function> getFunctionCollection() {
        return functionList;
    }

    public void setFunctionCollection(Collection<Function> functionCollection) {
        for (Function function : functionCollection) {
            addFunction(function);
        }
    }

    public Schema addFunction(Function function) {
        functionList.add(function);
        return this;
    }

    // Procedure
    public Collection<Procedure> getProcedureCollection() {
        return procedureList;
    }

    public void setProcedureCollection(Collection<Procedure> procedureCollection) {
        for (Procedure procedure : procedureCollection) {
            addProcedure(procedure);
        }
    }

    public Schema addProcedure(Procedure procedure) {
        procedureList.add(procedure);
        return this;
    }

}

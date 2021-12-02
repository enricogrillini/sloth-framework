package it.eg.sloth.dbmodeler.model.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.eg.sloth.dbmodeler.model.JsonInterface;
import it.eg.sloth.dbmodeler.model.schema.code.Function;
import it.eg.sloth.dbmodeler.model.schema.code.Package;
import it.eg.sloth.dbmodeler.model.schema.code.Procedure;
import it.eg.sloth.dbmodeler.model.schema.code.StoredProcedure;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Constraint;
import it.eg.sloth.dbmodeler.model.schema.table.ConstraintType;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.schema.view.View;
import lombok.ToString;

import java.util.*;

@ToString
public class Schema implements JsonInterface {
    private Map<String, Table> tableMap;
    private Map<String, View> viewMap;
    private Map<String, Sequence> sequenceMap;
    private Map<String, Function> functionMap;
    private Map<String, Procedure> procedureMap;
    private Map<String, Package> packageMap;

    public Schema() {
        tableMap = new LinkedHashMap<>();
        viewMap = new LinkedHashMap<>();
        sequenceMap = new LinkedHashMap<>();
        functionMap = new LinkedHashMap<>();
        procedureMap = new LinkedHashMap<>();
        packageMap = new LinkedHashMap<>();
    }

    // Table
    public Collection<Table> getTableCollection() {
        return Collections.unmodifiableCollection(tableMap.values());
    }

    public void setTableCollection(Collection<Table> tableCollection) {
        for (Table dbObject : tableCollection) {
            addTable(dbObject);
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
        return Collections.unmodifiableCollection(viewMap.values());
    }

    public void setViewCollection(Collection<View> viewCollection) {
        for (View dbObject : viewCollection) {
            addView(dbObject);
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
        return Collections.unmodifiableCollection(sequenceMap.values());
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
        return Collections.unmodifiableCollection(functionMap.values());
    }

    public void setFunctionCollection(Collection<Function> functionCollection) {
        for (Function dbObject : functionCollection) {
            addFunction(dbObject);
        }
    }

    public Function getFunction(String name) {
        return functionMap.get(name.toLowerCase());
    }

    public Schema addFunction(Function function) {
        functionMap.put(function.getName().toLowerCase(), function);
        return this;
    }

    // Procedure
    public Collection<Procedure> getProcedureCollection() {
        return Collections.unmodifiableCollection(procedureMap.values());
    }

    public void setProcedureCollection(Collection<Procedure> procedureCollection) {
        for (Procedure dbObject : procedureCollection) {
            addProcedure(dbObject);
        }
    }

    public Procedure getProcedure(String name) {
        return procedureMap.get(name.toLowerCase());
    }

    public Schema addProcedure(Procedure procedure) {
        procedureMap.put(procedure.getName().toLowerCase(), procedure);
        return this;
    }

    // Package
    public Collection<Package> getPackageCollection() {
        return Collections.unmodifiableCollection(packageMap.values());
    }

    public void setPackageCollection(Collection<Package> packageCollection) {
        for (Package dbObject : packageCollection) {
            addPackage(dbObject);
        }
    }

    public Package getPackage(String name) {
        return packageMap.get(name.toLowerCase());
    }

    public Schema addPackage(Package dbObject) {
        packageMap.put(dbObject.getName().toLowerCase(), dbObject);
        return this;
    }


    // Stored procedure
    public Schema addStoredProcedure(StoredProcedure storedProcedure) {
        if (storedProcedure instanceof Function) {
            addFunction((Function) storedProcedure);
        } else if (storedProcedure instanceof Procedure) {
            addProcedure((Procedure) storedProcedure);
        } else if (storedProcedure instanceof Package) {
            addPackage((Package) storedProcedure);
        }

        return this;
    }

    public StoredProcedure getStoredProcedure(String name, StoredProcedure.Type type) {
        switch (type) {
            case FUNCTION:
                return getFunction(name);
            case PROCEDURE:
                return getProcedure(name);
            case PACKAGE:
                return getPackage(name);
            default:
                return null;
        }
    }

}

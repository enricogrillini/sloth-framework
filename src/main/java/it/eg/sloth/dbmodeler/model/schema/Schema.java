package it.eg.sloth.dbmodeler.model.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Constraint;
import it.eg.sloth.dbmodeler.model.schema.table.ConstraintType;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import lombok.ToString;

import java.util.*;

@ToString
public class Schema {
    private Map<String, Table> tableMap;
    private Map<String, Sequence> sequenceMap;


    public Schema() {
        tableMap = new LinkedHashMap<>();
        sequenceMap = new LinkedHashMap<>();
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

    public Table addTable(Table table) {
        return tableMap.put(table.getName().toLowerCase(), table);
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

    public Sequence addSequence(Sequence sequence) {
        return sequenceMap.put(sequence.getName().toLowerCase(), sequence);
    }


}

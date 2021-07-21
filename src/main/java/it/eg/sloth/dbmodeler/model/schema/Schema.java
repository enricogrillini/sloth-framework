package it.eg.sloth.dbmodeler.model.schema;

import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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
            putTable(table);
        }
    }

    public Table putTable(Table table) {
        return tableMap.put(table.getName().toLowerCase(), table);
    }

    public Table getTable(String tableName) {
        return tableMap.get(tableName.toLowerCase());
    }

    // Sequence
    public Collection<Sequence> getSequenceCollection() {
        return sequenceMap.values();
    }

    public void setSequenceCollection(Collection<Sequence> sequenceCollection) {
        for (Sequence sequence : sequenceCollection) {
            putSequence(sequence);
        }
    }

    public Sequence getSequence(String sequenceName) {
        return sequenceMap.get(sequenceName.toLowerCase());
    }

    public Sequence putSequence(Sequence sequence) {
        return sequenceMap.put(sequence.getName().toLowerCase(), sequence);
    }

}

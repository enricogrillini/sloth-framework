package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.schema.table.TableColumn;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public abstract class DbSchemaAbstractReader implements DbSchemaReader {

    @Getter
    private DataBaseType dataBaseType;

    @Getter
    private String owner;

    protected DbSchemaAbstractReader(DataBaseType dataBaseType, String owner) {
        this.dataBaseType = dataBaseType;
        this.owner = owner;
    }

    protected abstract <R extends DataRow> DataTable<R> tablesData(Connection connection, String tableName) throws FrameworkException, SQLException, IOException;

    protected abstract <R extends DataRow> DataTable<R> sequencesData(Connection connection) throws FrameworkException, SQLException, IOException;

    protected abstract String calcColumnType(DataRow dataRow);

    @Override
    public List<Sequence> loadSequences(Connection connection) throws SQLException, IOException, FrameworkException {
        // Sequences Data
        DataTable<?> dataTable = sequencesData(connection);

        // Lettura sequence
        List<Sequence> sequenceList = new ArrayList<>();
        for (DataRow dataRow : dataTable) {
            Sequence sequence = Sequence.builder()
                    .name(StringUtil.initCap(dataRow.getString("sequence_name").toLowerCase()))
                    .build();

            sequenceList.add(sequence);
        }

        return sequenceList;
    }

    @Override
    public Collection<Table> loadTables(Connection connection, String tableName) throws SQLException, FrameworkException, IOException {
        Map<String, Table> tableCache = new HashMap<>();
        Map<String, TableColumn> columnCache = new HashMap<>();

        // Tables Data
        DataTable<?> dataTable = tablesData(connection, tableName);

        List<Table> tableList = new ArrayList<>();
        Table dbTable = new Table();
        int j = 0;
        for (DataRow dataRow : dataTable) {
            String name = dataRow.getString("table_name");
            if (!name.equalsIgnoreCase(dbTable.getName())) {
                // Inizializzo la nuova tabella
                dbTable = Table.builder()
                        .name(StringUtil.initCap(name))
                        .description(dataRow.getString("table_comments"))
                        .tablespace(dataRow.getString("tablespace_name"))
                        .initial(dataRow.getBigDecimal("initial_extent").longValue())
                        .temporary("Y".equals(dataRow.getString("temporary_table")))
                        .duration(dataRow.getString("duration"))

                        .columnCollection(new ArrayList<>())
                        .build();

//                dbTable.setIndexes(new Indexes());
//                dbTable.setConstraints(new Constraints());
//                dbTable.setTriggers(new Triggers());
//                dbTable.setForeignKeyReferences(new ForeignKeyReferences());
//                dbTable.setGrants(new Grants());
//                dbTable.setPartitions(new Partitions());

                // Aggiungo tabella alla lista e alla cache
                tableList.add(dbTable);
                tableCache.put(dbTable.getName().toUpperCase(), dbTable);

                j = 0;
            }

            TableColumn dbTableColumn = TableColumn.builder()
                    .name(dataRow.getString("column_name"))
                    .primaryKey(false)
                    .description(dataRow.getString("column_comments"))
                    .nullable("Y".equals(dataRow.getString("nullable")))
                    .type(calcColumnType(dataRow))
                    .dataLength(BigDecimalUtil.intValue(dataRow.getBigDecimal("data_length")))
                    .dataPrecision(BigDecimalUtil.intObject(dataRow.getBigDecimal("data_precision")))
                    .defaultValue(dataRow.getString("DATA_DEFAULT"))
                    .position(j++)
                    .build();

            dbTable.getColumnCollection().add(dbTableColumn);
            columnCache.put(dbTable.getName().toUpperCase() + "." + dbTableColumn.getName().toUpperCase(), dbTableColumn);
        }

//// QUI
//            // Constraints
//            it.eg.sloth.db.datasource.table.Table sqlDbConstraint = new it.eg.sloth.db.datasource.table.Table();
//
//            query = new Query(getSqlStatement(SQL_DB_CONSTRAINT));
//            query.addParameter(Types.VARCHAR, getOwner());
//            query.addParameter(Types.VARCHAR, tableName);
//            query.addParameter(Types.VARCHAR, getOwner());
//            query.addParameter(Types.VARCHAR, tableName);
//            query.populateDataTable(sqlDbConstraint);
//
//            Constraint dbConstraint = new Constraint();
//            for (DataRow dataRow : sqlDbConstraint) {
//                // Rottura su constraint
//                String name = dataRow.getString("constraint_name");
//                if (!name.equalsIgnoreCase(dbConstraint.getName())) {
//                    dbTable = tableCache.get(dataRow.getString("table_name").toUpperCase());
//
//                    if (dbTable != null) {
//                        dbConstraint = new Constraint();
//                        dbConstraint.setColumns(new ConstraintColumns());
//
//                        dbConstraint.setGenerated(GEN_GENERATED_NAME.equals(dataRow.getString("generated")));
//                        if (!dbConstraint.isGenerated()) {
//                            dbConstraint.setName(dataRow.getString("constraint_name"));
//                        }
//
//                        dbConstraint.setType(getConstraintType(dataRow.getString("constraint_type")));
//                        dbConstraint.setSearchCondition(dataRow.getString("search_condition"));
//                        dbConstraint.setTable(dataRow.getString("tabellaReferenziata"));
//                        dbTable.getConstraints().getConstraint().add(dbConstraint);
//
//                        if (ConstraintType.FOREIGN_KEY == dbConstraint.getType()) {
//                            Table table = tableCache.get(dbConstraint.getTable());
//
//                            if (table != null) {
//                                ForeignKeyReference dbForeignKeyReference = new ForeignKeyReference();
//                                dbForeignKeyReference.setTableName(dbTable.getName());
//
//                                table.getForeignKeyReferences().getForeignKeyReference().add(dbForeignKeyReference);
//                            }
//                        }
//                    }
//                }
//
//                // Colonna
//                if (dbTable != null) {
//                    String columnName = dataRow.getString("column_name");
//
//                    if (columnName != null && !"".equals(columnName)) {
//                        ConstraintColumn constraintColumn = new ConstraintColumn();
//                        constraintColumn.setName(dataRow.getString("column_name"));
//                        dbConstraint.getColumns().getConstraintColumn().add(constraintColumn);
//                    }
//
//                    if (dbConstraint.getType() == ConstraintType.PRIMARY_KEY) {
//                        columnCache.get(dbTable.getName().toUpperCase() + "." + columnName.toUpperCase()).setPrimaryKey(true);
//                    }
//                }
//            }
//
//            // Constants
//            for (Table table : tables.getTable()) {
//                table.setConstants(getConstants(table.getName(), table.getColumns().getColumn().get(0).getName()));
//            }

////// QUI

//
//        // Triggers
//        i = 1;
//        preparedStatement = getPreparedStatement(_sqlTriggers);
//        preparedStatement.setObject(i++, getOwner(), Types.VARCHAR);
//        preparedStatement.setObject(i++, tableName, Types.VARCHAR);
//        resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            if (OT_TABLE.equals(resultSet.getString("BASE_OBJECT_TYPE"))) {
//                dbTable = tables.get(resultSet.getString("TABLE_NAME"));
//
//                DbTrigger trigger = dbTable.getTriggers().add(resultSet.getString("TRIGGER_NAME"));
//                trigger.setType(resultSet.getString("TRIGGER_TYPE"));
//                trigger.setEvent(resultSet.getString("TRIGGERING_EVENT"));
//                trigger.setSource(CodeWriter.cleanCode(resultSet.getString("TRIGGER_BODY")));
//            }
//        }
//
//        resultSet.close();
//        preparedStatement.close();
//
//        // Indici
//        i = 1;
//        preparedStatement = getPreparedStatement(_sqlDbIndexes);
//        preparedStatement.setObject(i++, getOwner(), Types.VARCHAR);
//        preparedStatement.setObject(i++, tableName, Types.VARCHAR);
//        preparedStatement.setObject(i++, getOwner(), Types.VARCHAR);
//        preparedStatement.setObject(i++, tableName, Types.VARCHAR);
//        resultSet = preparedStatement.executeQuery();
//
//        PreparedStatement psICE = null;
//        ResultSet rsICE = null;
//        try {
//            DbIndex dbIndex = new DbIndex("");
//            while (resultSet.next()) {
//                String name = resultSet.getString("index_name");
//                if (!dbIndex.getName().equalsIgnoreCase(name)) {
//                    try {
//                        // Cambio indice
//                        dbIndex = tables.get(resultSet.getString("table_name")).getIndexes().add(name);
//                        dbIndex.setInitial(resultSet.getDouble("initial_extent"));
//                        dbIndex.setTablespace(resultSet.getString("tablespace_name"));
//                        dbIndex.setUniqueness(INDX_UNIQUE.equalsIgnoreCase(resultSet.getString("uniqueness")));
//                    } catch (Exception e) {
//                        logger.warn("Error on index", e);
//                    }
//                }
//                String columnName = resultSet.getString("column_name");
//
//                // GG 29-09-2014: se il nome è "di sistema" vuol dire che è un campo con
//                // "ESPRESSIONE"
//                // che devo recuperare da un'altra tabella.
//                if (columnName.startsWith("SYS_") && columnName.endsWith("$")) {
//                    if (psICE == null) {
//                        psICE = getPreparedStatement(_sqlDbIdxColumnExpr);
//                    } else {
//                        psICE.clearParameters();
//                    }
//                    i = 1;
//                    psICE.setObject(i++, resultSet.getString("owner"), Types.VARCHAR);
//                    psICE.setObject(i++, name, Types.VARCHAR);
//                    psICE.setObject(i++, getOwner(), Types.VARCHAR);
//                    psICE.setObject(i++, resultSet.getString("table_name"), Types.VARCHAR);
//                    psICE.setObject(i++, resultSet.getInt("column_position"), Types.INTEGER);
//
//                    rsICE = psICE.executeQuery();
//
//                    if (rsICE.next()) {
//                        columnName = rsICE.getString("column_expression");
//                    }
//
//                    rsICE.close();
//                    rsICE = null;
//                }
//
//                // Aggiungo il nome di colonna o espressione:
//                dbIndex.getColumns().add(columnName);
//
//            } // while
//        } finally {
//            if (rsICE != null)
//                try {
//                    rsICE.close();
//                } catch (Exception ex) {
//                }
//            if (psICE != null)
//                try {
//                    psICE.close();
//                } catch (Exception ex) {
//                }
//        }
//        resultSet.close();
//        preparedStatement.close();
//
//        // Lobs
//        i = 1;
//        preparedStatement = getPreparedStatement(_sqlLobsFull);
//        preparedStatement.setObject(i++, getOwner(), Types.VARCHAR);
//        preparedStatement.setObject(i++, tableName, Types.VARCHAR);
//        try {
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                DbTable table = tables.get(resultSet.getString("table_name"));
//                DbTableColumn tableColumn = table.getColumns().get(resultSet.getString("column_name"));
//
//                DbLob lob = new DbLob();
//                lob.setTablespace(resultSet.getString("tablespace_name"));
//                lob.setInitial(resultSet.getDouble("initial_extent"));
//                lob.setChunk(resultSet.getDouble("chunk"));
//                lob.setInRow("YES".equals(resultSet.getString("in_row")));
//                tableColumn.setLob(lob);
//            }
//
//        } catch (Exception e) {
//            i = 1;
//            preparedStatement = getPreparedStatement(_sqlLobs);
//            preparedStatement.setObject(i++, getOwner(), Types.VARCHAR);
//            preparedStatement.setObject(i++, tableName, Types.VARCHAR);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                DbTable table = tables.get(resultSet.getString("table_name"));
//                DbTableColumn tableColumn = table.getColumns().get(resultSet.getString("column_name"));
//
//                DbLob lob = new DbLob();
//                lob.setChunk(resultSet.getDouble("chunk"));
//                lob.setInRow("YES".equals(resultSet.getString("in_row")));
//                tableColumn.setLob(lob);
//            }
//        }
//
//        // Partition - Table
//        i = 1;
//        preparedStatement = getPreparedStatement(_sqlTablePartition);
//        preparedStatement.setObject(i++, getOwner(), Types.VARCHAR);
//        preparedStatement.setObject(i++, tableName, Types.VARCHAR);
//        resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            DbTable table = tables.get(resultSet.getString("table_name"));
//            if (table != null) {
//                // N.B.: la lettura dell' High_Value deve avvenire come prima cosa se no
//                // da errore (e' long)
//                table.getPartitions().setType(resultSet.getString("partitioning_type"));
//                table.getPartitions().setColumnName(resultSet.getString("Column_Name"));
//
//                DbPartition dbPartition = table.getPartitions().addPartition(resultSet.getString("Partition_Name"));
//                dbPartition.setTablespace(resultSet.getString("Tablespace_Name"));
//                dbPartition.setInitial(resultSet.getDouble("Initial_Extent"));
//                dbPartition.setHighValue(resultSet.getString("High_Value"));
//            }
//        }
//
//        // Partition - Index
//        i = 1;
//        preparedStatement = getPreparedStatement(_sqlIndexPartition);
//        preparedStatement.setObject(i++, getOwner(), Types.VARCHAR);
//        preparedStatement.setObject(i++, tableName, Types.VARCHAR);
//        resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            DbTable table = tables.get(resultSet.getString("table_name"));
//            if (table != null) {
//                DbIndex dbIndex2 = table.getIndexes().get(resultSet.getString("index_name"));
//
//                // N.B.: la lettura dell' High_Value deve avvenire come prima cosa se no
//                // da errore (e' long)
//                dbIndex2.getPartitions().setType(resultSet.getString("partitioning_type"));
//                dbIndex2.getPartitions().setColumnName(resultSet.getString("Column_Name"));
//
//                DbPartition dbPartition = dbIndex2.getPartitions().addPartition(resultSet.getString("Partition_Name"));
//                dbPartition.setTablespace(resultSet.getString("Tablespace_Name"));
//                dbPartition.setInitial(resultSet.getDouble("Initial_Extent"));
//                dbPartition.setHighValue(resultSet.getString("High_Value"));
//            }
//        }
//
        return tableList;
    }

}

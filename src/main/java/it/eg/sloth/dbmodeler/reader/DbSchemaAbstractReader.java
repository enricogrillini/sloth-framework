package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Index;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.schema.table.TableColumn;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DbSchemaAbstractReader implements DbSchemaReader {

    @Getter
    private DataBaseType dataBaseType;

    protected DbSchemaAbstractReader(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    @Override
    public void addSequences(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException {
        // Sequences Data
        DataTable<?> dataTable = sequencesData(connection, owner);

        // Lettura sequence
        for (DataRow dataRow : dataTable) {
            Sequence sequence = Sequence.builder()
                    .name(StringUtil.initCap(dataRow.getString("sequence_name").toLowerCase()))
                    .build();

            schema.addSequence(sequence);
        }
    }

    @Override
    public void addTables(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException {
        // Tables Data
        DataTable<?> dataTable = tablesData(connection, owner);

        int j = 0;
        for (DataRow dataRow : dataTable) {
            String name = dataRow.getString("table_name");
            Table dbTable = schema.getTable(name);

            if (dbTable == null) {
                // Inizializzo la nuova tabella
                dbTable = Table.builder()
                        .name(StringUtil.initCap(name))
                        .description(dataRow.getString("table_comments"))
                        .tablespace(dataRow.getString("tablespace_name"))
                        .initial(dataRow.getBigDecimal("initial_extent").longValue())
                        .temporary("Y".equals(dataRow.getString("temporary_table")))
                        .duration(dataRow.getString("duration"))
                        .build();

                // Aggiungo tabella alla lista e alla cache
                schema.addTable(dbTable);
                j = 0;
            }

            TableColumn dbTableColumn = TableColumn.builder()
                    .name(StringUtil.initCap(dataRow.getString("column_name").toLowerCase()))
                    .primaryKey(false)
                    .description(dataRow.getString("column_comments"))
                    .nullable("Y".equals(dataRow.getString("nullable")))
                    .type(calcColumnType(dataRow))
                    .dataLength(BigDecimalUtil.intValue(dataRow.getBigDecimal("data_length")))
                    .dataPrecision(BigDecimalUtil.intObject(dataRow.getBigDecimal("data_precision")))
                    .defaultValue(dataRow.getString("DATA_DEFAULT"))
                    .position(j++)
                    .build();

            dbTable.addTableColumn(dbTableColumn);
        }
    }

    public void addIndexes(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException {
        // Indexes Data
        DataTable<?> dataTable = indexesData(connection, owner);

        // Lettura sequence
        for (DataRow dataRow : dataTable) {
            String tableName = dataRow.getString("table_name");
            String indexName = dataRow.getString("index_name");

            Table table = schema.getTable(tableName);
            Index index = table.getIndex(indexName);
            if (index == null) {
                index = Index.builder()
                        .name(indexName)
                        .uniqueness("UNIQUE".equalsIgnoreCase(dataRow.getString("uniqueness")))
                        .tablespace(dataRow.getString("tablespace_name"))
                        .initial(dataRow.getBigDecimal("initial_extent").longValue())
                        .build();

                table.addIndex(index);
            }

            index.addColumn(dataRow.getString("column_name"));
        }
    }

}

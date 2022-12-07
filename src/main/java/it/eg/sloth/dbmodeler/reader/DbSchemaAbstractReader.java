package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Constant;
import it.eg.sloth.dbmodeler.model.schema.table.Index;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.schema.table.TableColumn;
import it.eg.sloth.dbmodeler.model.schema.view.View;
import it.eg.sloth.dbmodeler.model.schema.view.ViewColumn;
import it.eg.sloth.dbmodeler.model.statistics.Statistics;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
                        .initial(BigDecimalUtil.longObject(dataRow.getBigDecimal("initial_extent")))
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

        // Lettura indici
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


    @Override
    public void addConstants(Schema schema, Connection connection) throws SQLException, FrameworkException, IOException {
        for (Table table : schema.getTableCollection()) {
            if (table.isDecodeTable()) {
                addConstants(connection, table);
            }
        }
    }

    private void addConstants(Connection connection, Table table) throws FrameworkException, SQLException, IOException {
        final int MAX_CONSTANTS = 150;
        Map<String, Integer> nameCache = new HashMap<>();

        String keyName = table.getTableColumn(0).getName();

        DataTable<?> constantTable = constantsData(connection, table.getName(), keyName);
        if (constantTable.size() < MAX_CONSTANTS) {
            for (DataRow row : constantTable) {
                String name = row.getString("DESCRIZIONEBREVE").toUpperCase();
                String value = row.getString(keyName);

                // Normalizzo il nome
                name = name.replaceAll("([^A-Za-z0-9_])", "_");
                if (name.charAt(0) >= '0' && name.charAt(0) <= '9') {
                    name = "_" + name;
                }

                // Gestisco i nomi duplicati
                if (nameCache.containsKey(name)) {
                    int suff = nameCache.get(name) + 1;
                    nameCache.put(name, suff);

                    name = name + "_" + suff;
                } else {
                    nameCache.put(name, 0);
                }

                table.addConstant(new Constant(name, value));
            }
        }
    }

    @Override
    public void addViews(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException {
        // Views Data
        DataTable<?> dataTable = viewsData(connection, owner);
        for (DataRow dataRow : dataTable) {
            String name = dataRow.getString("view_name");
            View dbView = schema.getView(name);

            if (dbView == null) {
                // Inizializzo la nuova tabella
                dbView = View.builder()
                        .name(StringUtil.initCap(name))
                        .description(dataRow.getString("view_comments"))
                        .definition(dataRow.getString("definition"))
                        .build();

                // Aggiungo tabella alla lista e alla cache
                schema.addView(dbView);
            }
        }

        // Views Columns Data
        dataTable = viewsColumnsData(connection, owner);
        for (DataRow dataRow : dataTable) {
            String name = dataRow.getString("view_name");
            View dbView = schema.getView(name);

            if (dbView != null) {
                ViewColumn dbViewColumn = ViewColumn.builder()
                        .name(StringUtil.initCap(dataRow.getString("column_name").toLowerCase()))
                        .description(dataRow.getString("column_comments"))
                        .type(calcColumnType(dataRow))
                        .dataLength(BigDecimalUtil.intValue(dataRow.getBigDecimal("data_length")))
                        .dataPrecision(BigDecimalUtil.intObject(dataRow.getBigDecimal("data_precision")))
                        .position(dbView.getViewColumnCollection().size())
                        .build();

                dbView.addViewColumn(dbViewColumn);
            }
        }
    }

    public Statistics refreshStatistics(Connection connection, String owner) throws SQLException, IOException, FrameworkException {
        // Statistics Data
        DataTable<?> dataTable = statisticsData(connection, owner);

        String driverClass = connection == null ? null : DriverManager.getDriver(connection.getMetaData().getURL()).getClass().getName();

        return Statistics.builder()
                .driverClass(driverClass)
                .tableCount(BigDecimalUtil.longValue(dataTable.getBigDecimal("table_count")))
                .tableSize(BigDecimalUtil.longValue(dataTable.getBigDecimal("table_size")))
                .indexCount(BigDecimalUtil.longValue(dataTable.getBigDecimal("index_count")))
                .indexSize(BigDecimalUtil.longValue(dataTable.getBigDecimal("index_size")))
                .recycleBinCount(BigDecimalUtil.longValue(dataTable.getBigDecimal("recyclebin_count")))
                .recycleBinSize(BigDecimalUtil.longValue(dataTable.getBigDecimal("recyclebin_size")))
                .build();
    }

}

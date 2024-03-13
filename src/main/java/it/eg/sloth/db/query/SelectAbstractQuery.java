package it.eg.sloth.db.query;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.row.column.Column;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Slf4j
public abstract class SelectAbstractQuery implements SelectQueryInterface {

    private static final String TRAIL_MESSAGE = "Errore sulla query: {}";

    private String statement;

    protected SelectAbstractQuery(String statement) {
        this.statement = statement;
    }

    protected abstract String getSqlStatement();

    protected abstract void initStatement(PreparedStatement statement) throws SQLException;


    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public DataTable selectTable() throws SQLException, IOException, FrameworkException {
        Table table = new Table();
        populateDataTable(table);
        return table;
    }

    @Override
    public DataTable selectTable(String connectionName) throws SQLException, IOException, FrameworkException {
        Table table = new Table();
        populateDataTable(table, connectionName);
        return table;
    }

    @Override
    public DataTable selectTable(Connection connection) throws SQLException, IOException, FrameworkException {
        Table table = new Table();
        populateDataTable(table, connection);
        return table;
    }

    @Override
    public DataRow selectRow() throws SQLException, IOException, FrameworkException {
        DataRow dataRow = new Row();
        return populateDataRow(dataRow) ? dataRow : null;
    }

    @Override
    public DataRow selectRow(String connectionName) throws SQLException, IOException, FrameworkException {
        DataRow dataRow = new Row();
        return populateDataRow(dataRow, connectionName) ? dataRow : null;
    }

    @Override
    public DataRow selectRow(Connection connection) throws SQLException, IOException, FrameworkException {
        DataRow dataRow = new Row();
        return populateDataRow(dataRow, connection) ? dataRow : null;
    }

    @Override
    public boolean populateDataRow(DataRow dataRow, String connectionName) throws SQLException, IOException, FrameworkException {
        try (Connection connection = DataConnectionManager.getInstance().getDataSource(connectionName).getConnection()) {
            return populateDataRow(dataRow, connection);
        }
    }

    @Override
    public boolean populateDataRow(DataRow dataRow) throws SQLException, FrameworkException {
        return populateDataRow(dataRow, (Connection) null);
    }

    @Override
    public boolean populateDataRow(DataRow dataRow, Connection connection) throws SQLException, FrameworkException {
        if (connection == null) {
            try (Connection newConnection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
                return populateDataRow(dataRow, newConnection);
            }
        } else {
            log.debug("Start populateDataRow");
            log.debug(toString());

            boolean result = false;
            try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                log.debug("Start populateDataRow");
                log.debug(toString());

                initStatement(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    result = resultSet.next();
                    if (result) {
                        dataRow.loadFromResultSet(resultSet);
                    } else {
                        dataRow.clear();
                    }
                }
            } catch (SQLException e) {
                log.info(TRAIL_MESSAGE, toString());
                throw e;
            }

            log.debug("End populateDataRow ({})", result);
            return result;
        }
    }

    @Override
    public void populateDataTable(DataTable<?> dataTable, String connectionName) throws SQLException, FrameworkException {
        try (Connection connection = DataConnectionManager.getInstance().getDataSource(connectionName).getConnection()) {
            populateDataTable(dataTable, connection);
        }
    }

    @Override
    public void populateDataTable(DataTable<?> dataTable) throws SQLException, FrameworkException {
        populateDataTable(dataTable, (Connection) null);
    }

    @Override
    public void populateDataTable(DataTable<?> dataTable, Connection connection) throws SQLException, FrameworkException {
        if (connection == null) {
            try (Connection newConnection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
                populateDataTable(dataTable, newConnection);
            }
        } else {
            log.debug("Start populateDataTable");
            log.debug(toString());

            try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                initStatement(preparedStatement);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        dataTable.append().loadFromResultSet(resultSet);
                    }
                }
            } catch (SQLException e) {
                log.info(TRAIL_MESSAGE, toString());
                throw e;
            }

            log.debug("End populateDataTable");
        }
    }


    @Override
    public List<Column> columnList(Connection connection) throws SQLException, FrameworkException {
        if (connection == null) {
            try (Connection newConnection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
                return columnList( newConnection);
            }
        } else {
            log.debug("Start columnList");
            log.debug(toString());

            List<Column> result = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                log.debug("Start get resultSetMetaData");
                log.debug(toString());

                initStatement(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        Column column = new Column();
                        column.setName(resultSetMetaData.getColumnName(i));
                        column.setDescription(resultSetMetaData.getColumnLabel(i));
                        column.setJavaType(resultSetMetaData.getColumnType(i));
                        column.setNullable(true);

                        result.add(column);
                    }
                }
            } catch (SQLException e) {
                log.info(TRAIL_MESSAGE, toString());
                throw e;
            }

            log.debug("End columnList ({})", result);
            return result;
        }
    }

    public List<Column> columnList() throws SQLException, FrameworkException {
        return columnList(null);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nStatement: " + getStatement())
                .append("\nSqlStatement: " + getSqlStatement())
                .toString();

    }
}

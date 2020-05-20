package it.eg.sloth.db.query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
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
public abstract class SelectAbstractQuery extends FrameComponent implements SelectQueryInterface {


    private String statement;

    public SelectAbstractQuery(String statement) {
        this.statement = statement;
    }

    /**
     * Ritorna lo statement
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    protected abstract PreparedStatement getPreparedStatement(Connection connection) throws SQLException, FrameworkException;

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public DataTable<? extends DataRow> selectTable() throws SQLException, IOException, FrameworkException {
        Table table = new Table();
        populateDataTable(table);
        return table;
    }

    @Override
    public DataTable<?> selectTable(String connectionName) throws SQLException, IOException, FrameworkException {
        Table table = new Table();
        populateDataTable(table, connectionName);
        return table;
    }

    @Override
    public DataTable<?> selectTable(Connection connection) throws SQLException, IOException, FrameworkException {
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
    public boolean populateDataRow(DataRow dataRow) throws SQLException, IOException, FrameworkException {
        return populateDataRow(dataRow, (Connection) null);
    }

    @Override
    public boolean populateDataRow(DataRow dataRow, Connection connection) throws SQLException, IOException, FrameworkException {
        if (connection == null) {
            try (Connection newConnection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
                return populateDataRow(dataRow, newConnection);
            }
        } else {
            log.debug("Start populateDataRow");
            log.debug(toString());

            boolean result = false;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                log.debug("Start populateDataRow");
                log.debug(toString());

                preparedStatement = getPreparedStatement(connection);
                resultSet = preparedStatement.executeQuery();

                result = resultSet.next();
                if (result) {
                    dataRow.loadFromResultSet(resultSet);
                } else {
                    dataRow.clear();
                }
            } catch (SQLException | IOException e) {
                log.info("Errore sulla query: {}", toString());
                throw e;

            } finally {
                DataConnectionManager.release(resultSet);
                DataConnectionManager.release(preparedStatement);
            }

            log.debug("End populateDataRow ({})", result);
            return result;
        }
    }

    @Override
    public void populateDataTable(DataTable<?> dataTable, String connectionName) throws SQLException, IOException, FrameworkException {
        try (Connection connection = DataConnectionManager.getInstance().getDataSource(connectionName).getConnection()) {
            populateDataTable(dataTable, connection);
        }
    }

    @Override
    public void populateDataTable(DataTable<?> dataTable) throws SQLException, IOException, FrameworkException {
        populateDataTable(dataTable, (Connection) null);
    }

    @Override
    public void populateDataTable(DataTable<?> dataTable, Connection connection) throws SQLException, IOException, FrameworkException {
        if (connection == null) {
            try (Connection newConnection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
                populateDataTable(dataTable, newConnection);
            }
        } else {
            log.debug("Start populateDataTable");
            log.debug(toString());

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                preparedStatement = getPreparedStatement(connection);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    dataTable.append().loadFromResultSet(resultSet);
                }
            } catch (SQLException | IOException e) {
                log.info("Errore sulla query: {}", toString());
                throw e;

            } finally {
                DataConnectionManager.release(resultSet);
                DataConnectionManager.release(preparedStatement);
            }

            log.debug("End populateDataTable");
        }
    }

}

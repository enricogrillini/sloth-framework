package it.eg.sloth.db.query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.column.Column;
import it.eg.sloth.framework.common.exception.FrameworkException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
public interface SelectQueryInterface {

    /**
     * Ritorna lo statement
     *
     * @return
     */
    String getStatement();

    /**
     * Imposta lo statement
     *
     * @param statement
     */
    void setStatement(String statement);

    /**
     * Ritorna una tabella contenente il risultato della query
     *
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataTable<DataRow> selectTable() throws SQLException, IOException, FrameworkException;

    /**
     * Ritorna una tabella contenente il risultato della query
     *
     * @param connectionName
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataTable<DataRow> selectTable(String connectionName) throws SQLException, IOException, FrameworkException;

    /**
     * Ritorna una tabella contenente il risultato della query
     *
     * @param connection
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataTable<DataRow> selectTable(Connection connection) throws SQLException, IOException, FrameworkException;

    /**
     * Ritorna una riga contenente il risultato della query
     *
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataRow selectRow() throws SQLException, IOException, FrameworkException;

    /**
     * Ritorna una riga contenente il risultato della query
     *
     * @param connectionName
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataRow selectRow(String connectionName) throws SQLException, IOException, FrameworkException;

    /**
     * Ritorna una riga contenente il risultato della query
     *
     * @param connection
     * @return
     * @throws SQLException
     * @throws IOException
     */
    DataRow selectRow(Connection connection) throws SQLException, IOException, FrameworkException;

    /**
     * Popola la DataRow passata
     *
     * @param dataRow
     * @param connectionName
     * @return
     * @throws SQLException
     * @throws IOException
     */
    boolean populateDataRow(DataRow dataRow, String connectionName) throws SQLException, IOException, FrameworkException;

    /**
     * Popola il DataTable passato
     *
     * @param dataRow
     * @return
     * @throws SQLException
     * @throws IOException
     */
    boolean populateDataRow(DataRow dataRow) throws SQLException, FrameworkException;

    /**
     * Popola il DataTable passato
     *
     * @param dataRow
     * @param connection
     * @return
     * @throws SQLException
     * @throws IOException
     */
    boolean populateDataRow(DataRow dataRow, Connection connection) throws SQLException, IOException, FrameworkException;

    /**
     * Popola il DataTable passato
     *
     * @param dataTable
     * @param connectionName
     * @throws SQLException
     * @throws IOException
     */
    void populateDataTable(DataTable<?> dataTable, String connectionName) throws SQLException, IOException, FrameworkException;

    /**
     * Popola il DataTable passato
     *
     * @param dataTable
     * @throws SQLException
     * @throws IOException
     */
    void populateDataTable(DataTable<?> dataTable) throws SQLException, FrameworkException;

    /**
     * Popola il DataTable passato
     *
     * @param dataTable
     * @param connection
     * @throws SQLException
     * @throws IOException
     */
    void populateDataTable(DataTable<?> dataTable, Connection connection) throws SQLException, FrameworkException;


    List<Column> columnList(Connection connection) throws SQLException, FrameworkException;


    List<Column> columnList() throws SQLException, FrameworkException;
}

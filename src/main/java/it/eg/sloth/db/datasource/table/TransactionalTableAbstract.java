package it.eg.sloth.db.datasource.table;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.TransactionalDataRow;
import it.eg.sloth.db.datasource.TransactionalDataSource;
import it.eg.sloth.db.datasource.TransactionalDataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.row.column.Column;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.framework.common.exception.FrameworkException;

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
public abstract class TransactionalTableAbstract<T extends TransactionalDataRow> extends TableAbstract<T> implements TransactionalDataTable<T> {


    protected List<T> oldRows;
    protected Set<T> rowsDeleted;

    public TransactionalTableAbstract() {
        super();
        oldRows = new ArrayList<>();
        rowsDeleted = new HashSet<>();
    }

    protected abstract T createRow();

    public T remove() throws FrameworkException {
        T row = super.remove();

        if (row != null) {
            rowsDeleted.add(row);
            row.remove();
        }

        return row;
    }

    public void save() throws FrameworkException, SQLException {
        for (T row : rowsDeleted) {
            row.save();
        }

        for (T row : this) {
            row.save();
        }

        oldRows = new ArrayList<>(rows);
        rowsDeleted = new HashSet<>();
    }

    public void undo() throws FrameworkException {
        rows = new ArrayList<T>(oldRows);
        rowsDeleted = new HashSet<>();

        for (T row : this) {
            row.undo();
        }

        setCurrentRow(getCurrentRow());
    }

    public void forceClean() {
        for (T row : this) {
            row.forceClean();
        }

        oldRows = new ArrayList<>(rows);
        rowsDeleted = new HashSet<>();
    }

    public Object getOldObject(String name) {
        return (size() > 0) ? ((TransactionalDataSource) getRow()).getOldObject(name) : null;
    }

    public BigDecimal getOldBigDecimal(String name) {
        return (BigDecimal) getOldObject(name);
    }

    public Timestamp getOldTimestamp(String name) {
        return (Timestamp) getOldObject(name);
    }

    public String getOldString(String name) {
        return (String) getOldObject(name);
    }

    public byte[] getOldByte(String name) {
        return (byte[]) getOldObject(name);
    }

    public void setFromQuery(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this);
    }

    public void setFromQuery(SelectQueryInterface query, Connection connection) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this, connection);
    }

    public boolean loadFromQuery(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this);
        forceClean();
        return true;
    }

    public boolean loadFromQuery(SelectQueryInterface query, Connection connection) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this, connection);
        forceClean();
        return true;
    }

    protected void load(String statement, Column[] columns, DataRow dataRow, Connection connection) throws SQLException, IOException, FrameworkException {
        FilteredQuery filteredQuery = new FilteredQuery(statement);

        if (dataRow != null) {
            for (int i = 0; i < columns.length; i++) {
                filteredQuery.addFilter(columns[i].getName() + " = ?", columns[i].getJavaType(), dataRow.getObject(columns[i].getName()));
            }
        }

        loadFromQuery(filteredQuery, connection);
    }

    protected void load(String statement, Column[] columns, DataRow dataRow) throws SQLException, IOException, FrameworkException {
        if (dataRow == null)
            dataRow = new Row();

        FilteredQuery filteredQuery = new FilteredQuery(statement);
        for (int i = 0; i < columns.length; i++) {
            filteredQuery.addFilter(columns[i].getName() + " = ?", columns[i].getJavaType(), dataRow.getObject(columns[i].getName()));
        }

        loadFromQuery(filteredQuery);
    }
}

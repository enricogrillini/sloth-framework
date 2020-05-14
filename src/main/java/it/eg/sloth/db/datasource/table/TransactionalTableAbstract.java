package it.eg.sloth.db.datasource.table;

import java.math.BigDecimal;
import java.sql.Connection;
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

/**
 * @param <T>
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

    public T remove() {
        T row = super.remove();

        if (row != null) {
            rowsDeleted.add(row);
            row.remove();
        }

        return row;
    }

    public void save() throws Exception {
        for (T row : rowsDeleted) {
            row.save();
        }

        for (T row : this) {
            row.save();
        }

        oldRows = new ArrayList<>(rows);
        rowsDeleted = new HashSet<>();
    }

    public void undo() {
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

    public void setFromQuery(SelectQueryInterface query) {
        try {
            query.populateDataTable(this);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void setFromQuery(SelectQueryInterface query, Connection connection) {
        try {
            query.populateDataTable(this, connection);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loadFromQuery(SelectQueryInterface query) {
        try {
            query.populateDataTable(this);
        } catch (Throwable e) {
            throw new RuntimeException(this.toString(), e);
        }
        forceClean();
        return true;
    }

    public boolean loadFromQuery(SelectQueryInterface query, Connection connection) {
        try {
            query.populateDataTable(this, connection);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        forceClean();
        return true;
    }

    protected void load(String statement, Column[] columns, DataRow dataRow, Connection connection) {
        FilteredQuery filteredQuery = new FilteredQuery(statement);

        if (dataRow != null) {
            for (int i = 0; i < columns.length; i++) {
                filteredQuery.addFilter(columns[i].getName() + " = ?", columns[i].getJavaType(), dataRow.getObject(columns[i].getName()));
            }
        }

        loadFromQuery(filteredQuery, connection);
    }

    protected void load(String statement, Column[] columns, DataRow dataRow) {
        if (dataRow == null)
            dataRow = new Row();

        FilteredQuery filteredQuery = new FilteredQuery(statement);
        for (int i = 0; i < columns.length; i++) {
            filteredQuery.addFilter(columns[i].getName() + " = ?", columns[i].getJavaType(), dataRow.getObject(columns[i].getName()));
        }

        loadFromQuery(filteredQuery);
    }
}

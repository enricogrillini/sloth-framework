package it.eg.sloth.db.datasource.table;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.filter.FilterRules;
import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.db.datasource.table.sort.SortingRules;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.ToString;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

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
@ToString
public abstract class TableAbstract<T extends DataRow> implements DataTable<T> {

    private SortingRules<T> sortingRules;
    protected List<T> rows;

    protected int currentRow;
    protected int pageSize;

    protected TableAbstract() {
        pageSize = -1;

        clear();
    }

    protected TableAbstract(DataTable<?> dataTable) {
        this();

        for (DataRow dataRow : dataTable) {
            append().loadFromDataSource(dataRow);
        }
    }

    @Override
    public int getCurrentRow() {
        return currentRow;
    }

    @Override
    public void setCurrentRow(int currentRow) {
        if (currentRow >= size())
            currentRow = size() - 1;
        if (currentRow < 0)
            currentRow = 0;

        this.currentRow = currentRow;
    }

    @Override
    public int getPageStart() {
        return getCurrentPage() * getPageSize();
    }

    @Override
    public int getPageEnd() {
        int end = (getCurrentPage() + 1) * getPageSize() - 1;

        if (size() - 1 > end)
            return end;
        else
            return size() - 1;
    }

    @Override
    public boolean isFirst() {
        return getCurrentRow() == 0;
    }

    @Override
    public boolean isLast() {
        return getCurrentRow() == (size() - 1);
    }

    @Override
    public boolean isFirstPage() {
        if (getPageSize() <= 0) {
            return true;
        }

        return getCurrentRow() < getPageSize();
    }

    @Override
    public boolean isLastPage() {
        if (getPageSize() <= 0) {
            return true;
        }

        return Math.floor((double) (size() - 1) / getPageSize()) * getPageSize() <= getCurrentRow();
    }

    @Override
    public int getCurrentPage() {
        return (int) Math.floor((double) getCurrentRow() / (double) getPageSize());
    }

    @Override
    public void setCurrentPage(int currentPage) {
        if (pageSize > 0) {
            if (currentPage < 0)
                currentPage = 0;
            if (currentPage >= Math.ceil((double) size() / (double) getPageSize()))
                currentPage = (int) Math.ceil((double) size() / (double) getPageSize()) - 1;

            setCurrentRow(currentPage * pageSize);
        } else {
            setCurrentRow(0);
        }
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public int pages() {
        return (int) Math.ceil((double) size() / (double) getPageSize());
    }

    @Override
    public void first() {
        setCurrentRow(0);
    }

    @Override
    public void prevPage() {
        setCurrentRow((getCurrentPage() - 1) * getPageSize());
    }

    @Override
    public void prev() {
        setCurrentRow(getCurrentRow() - 1);
    }

    @Override
    public void next() {
        setCurrentRow(getCurrentRow() + 1);
    }

    @Override
    public void nextPage() {
        setCurrentRow((getCurrentPage() + 1) * getPageSize());
    }

    @Override
    public void last() {
        setCurrentRow(size() - 1);
    }

    @Override
    public Iterator<T> iterator() {
        return rows.iterator();
    }

    @Override
    public Iterator<T> pageIterator() {
        if (getPageSize() <= 0)
            return rows.iterator();

        List<T> pageList = new ArrayList<>();

        int start = getCurrentPage() * getPageSize();
        int end = (getCurrentPage() + 1) * getPageSize() - 1;

        int i = 0;

        for (T row : rows) {
            if (i >= start && i <= end)
                pageList.add(row);

            i++;
        }

        return pageList.iterator();
    }

    @Override
    public T getRow() {
        if (size() == 0)
            return null;

        return rows.get(getCurrentRow());
    }

    @Override
    public List<T> getRows() {
        return new ArrayList<>(rows);
    }

    protected abstract T createRow();

    @Override
    public T append() {
        T row = createRow();
        rows.add(row);

        return row;
    }

    @Override
    public T add() {
        T row = createRow();

        int newCurrentRow = getCurrentRow() + 1;
        if (newCurrentRow < size())
            rows.add(newCurrentRow, row);
        else
            rows.add(row);

        setCurrentRow(newCurrentRow);

        return row;
    }

    @Override
    public T remove() throws FrameworkException {
        T dataRow = null;

        if (size() > 0) {
            dataRow = rows.remove(getCurrentRow());
        }

        setCurrentRow(getCurrentRow());
        return dataRow;
    }

    @Override
    public void removeAllRow() throws FrameworkException {
        while (size() > 0) {
            remove();
        }
    }

    @Override
    public Object getObject(String name) {
        return (size() > 0) ? getRow().getObject(name) : null;
    }

    @Override
    public BigDecimal getBigDecimal(String name) {
        return (BigDecimal) getObject(name);
    }

    @Override
    public Timestamp getTimestamp(String name) {
        return (Timestamp) getObject(name);
    }

    @Override
    public String getString(String name) {
        return (String) getObject(name);
    }

    @Override
    public byte[] getByte(String name) {
        return (byte[]) getObject(name);
    }

    @Override
    public TableAbstract<T> setObject(String name, Object value) {
        if (size() > 0) {
            getRow().setObject(name, value);
        }

        return this;
    }

    @Override
    public void clear() {
        rows = new ArrayList<>();
        currentRow = 0;

        sortingRules = new SortingRules<>();
    }

    @Override
    public Collection<String> keys() {
        return size() > 0 ? getRow().keys() : new HashSet<>();
    }

    @Override
    public Collection<Object> values() {
        return size() > 0 ? getRow().values() : new ArrayList<>();
    }

    @Override
    public Map<String, Object> entries() {
        return size() > 0 ? getRow().entries() : new HashMap<>();
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) {
        if (size() > 0) {
            getRow().copyFromDataSource(dataSource);
        }
    }

    @Override
    public void copyFromResultSet(ResultSet resultSet) throws SQLException, FrameworkException {
        if (size() > 0) {
            getRow().copyFromResultSet(resultSet);
        }
    }

    @Override
    public void loadFromDataSource(DataSource dataSource) {
        if (size() > 0) {
            getRow().loadFromDataSource(dataSource);
        }
    }

    @Override
    public void loadFromResultSet(ResultSet resultSet) throws SQLException, FrameworkException {
        if (size() > 0) {
            getRow().loadFromResultSet(resultSet);
        }
    }

    @Override
    public void setFromQuery(SelectQueryInterface query) throws SQLException, FrameworkException {
        query.populateDataTable(this);
    }

    public void setFromQuery(SelectQueryInterface query, String connectionName) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this, connectionName);
    }

    @Override
    public void setFromQuery(SelectQueryInterface query, Connection connection) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this, connection);
    }

    @Override
    public boolean loadFromQuery(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this);

        return true;
    }

    public boolean loadFromQuery(SelectQueryInterface query, String connectionName) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this, connectionName);

        return true;
    }

    @Override
    public boolean loadFromQuery(SelectQueryInterface query, Connection connection) throws SQLException, IOException, FrameworkException {
        query.populateDataTable(this, connection);
        return true;
    }

    @Override
    public SortingRule addSortingRule(String fieldName) {
        return sortingRules.add(fieldName);
    }

    @Override
    public SortingRule addSortingRule(String fieldName, SortType sortType) {
        return sortingRules.add(fieldName, sortType);
    }

    @Override
    public void applySort(boolean preserveCurrentRow) {
        if (!preserveCurrentRow) {
            Collections.sort(rows, sortingRules);
            setCurrentRow(0);
        } else {
            if (size() > 0) {
                DataRow row = getRow();
                Collections.sort(rows, sortingRules);

                int i = 0;

                for (T dataRow : this) {
                    if (dataRow == row) {
                        setCurrentRow(i);
                    }

                    i++;
                }
            }
        }
    }

    @Override
    public void applySort() {
        applySort(false);
    }

    @Override
    public void applySort(String fieldName, SortType sortType) {
        clearSortingRules();
        sortingRules.add(fieldName, sortType);
        applySort();
    }

    @Override
    public void applySorts(SortingRules<T> sortingRules) {
        this.sortingRules = sortingRules;
        applySort();
    }

    @Override
    public SortingRules<T> getSortingRules() {
        return sortingRules;
    }

    @Override
    public void clearSortingRules() {
        sortingRules = new SortingRules<>();
    }

    protected abstract TableAbstract<T> newTable();

    public TableAbstract<T> filter(FilterRules filterRules) {
        TableAbstract<T> table = newTable();

        for (T dataRow : this) {
            if (filterRules.check(dataRow))
                table.add().copyFromDataSource(dataRow);
        }

        return table;
    }

}

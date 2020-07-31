package it.eg.sloth.db.datasource.table;

import java.util.Iterator;
import java.util.List;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.query.pagedquery.PagedQueryInterface;

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
public class DbPagedTable extends TableAbstract<Row> {

    protected PagedQueryInterface query;
    protected Table table;

    protected int size = -1;
    protected int currentRow;
    protected int currentPage;
    protected int pageSize;

    @Override
    protected Row createRow() {
        return new Row();
    }

    @Override
    protected DbPagedTable newTable() {
        return new DbPagedTable(query, pageSize);
    }

    public DbPagedTable(PagedQueryInterface query, int pageSize) {
        this.query = query;

        this.currentRow = 0;
        this.currentPage = 0;
        this.pageSize = pageSize;

        refreshPage();
    }

    protected void refreshPage() {
        try {
            size = query.getCount();

            if (currentRow < 0)
                currentRow = 0;
            if (currentRow > size - 1)
                currentRow = size - 1;

            table = new Table(query.select(getPageStart() + 1, getPageEnd() + 1));

        } catch (Throwable e) {
            throw new RuntimeException(query.getStatement(), e);
        }
    }

    @Override
    public int getCurrentRow() {
        return currentRow;
    }

    @Override
    public void setCurrentRow(int currentRow) {
        if (currentRow < getPageStart() || currentRow > getPageEnd()) {
            this.currentRow = currentRow;
            refreshPage();
        } else {
            this.currentRow = currentRow;
        }
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
    public int getCurrentPage() {
        return (int) Math.floor((double) getCurrentRow() / (double) getPageSize());
    }

    @Override
    public void setCurrentPage(int currentPage) {
        if (pageSize > 0) {
            if (currentPage < 0) {
                currentPage = 0;
            } else if (currentPage >= Math.ceil((double) size() / (double) getPageSize())) {
                currentPage = (int) Math.ceil((double) size() / (double) getPageSize()) - 1;
            }

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
        refreshPage();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int pages() {
        return (int) Math.ceil((double) size() / (double) getPageSize());
    }

    @Override
    public void prevPage() {
        setCurrentRow((getCurrentPage() - 1) * getPageSize());
    }

    @Override
    public void first() {
        setCurrentRow(0);
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
    public Iterator<Row> pageIterator() {
        return table.iterator();
    }

    @Override
    public Iterator<Row> iterator() {
        return null;
    }

    @Override
    public List<Row> getRows() {
        return null;
    }

    @Override
    public Row getRow() {
        table.setCurrentRow(getCurrentRow() - getPageStart());
        return table.getRow();
    }

    @Override
    public Row append() {
        return null;
    }

    @Override
    public Row add() {
        return null;
    }

    @Override
    public Row remove() {
        return null;
    }

}

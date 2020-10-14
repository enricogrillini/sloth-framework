package it.eg.sloth.db.datasource.table;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.ToString;

import java.io.IOException;
import java.sql.SQLException;

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
public class Table extends TableAbstract<Row> {


    public Table(DataTable<?> dataTable) {
        super(dataTable);
    }

    public Table() {
        super();
    }

    @Override
    protected Row createRow() {
        return new Row();
    }

    @Override
    protected Table newTable() {
        return new Table();
    }

    public static class Factory {

        private Factory() {
            // NOP
        }

        public static Table loadFromQuery(SelectQueryInterface query, int pageSize) throws SQLException, IOException, FrameworkException {
            Table tableBean = new Table();
            tableBean.loadFromQuery(query);
            tableBean.setPageSize(pageSize);
            return tableBean;
        }

        public static Table loadFromQuery(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
            return loadFromQuery(query, -1);
        }
    }

    @Override
    public String toString() {
        return "Table{" +
                "sortingRules=" + getSortingRules() +
                ", rows=" + rows +
                ", currentRow=" + currentRow +
                ", pageSize=" + pageSize +
                '}';
    }
}

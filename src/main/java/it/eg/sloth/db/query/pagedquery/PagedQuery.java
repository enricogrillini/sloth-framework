package it.eg.sloth.db.query.pagedquery;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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
public class PagedQuery extends FilteredQuery implements PagedQueryInterface {

  private static final String ROW_COUNT = "rowCount";

    private String sql;
    private int start;
    private int end;

    public PagedQuery(String statement) {
        super(statement);
    }

    @Override
    protected String getFilteredStatement() {
        return sql;
    }

    @Override
    protected String getSqlStatement() {
        return getFilteredStatement();
    }

    @Override
    protected void initStatement(PreparedStatement statement) throws SQLException {
        int i = addValues(statement, 1);

        if (start != -1) {
            statement.setObject(i++, new BigDecimal(start), Types.INTEGER);
            statement.setObject(i, new BigDecimal(end), Types.INTEGER);
        }
    }

    private void prepareCount() {
        this.sql = "select count(*) rowCount from (" + super.getFilteredStatement() + ")";
        this.start = -1;
    }

    @Override
    public int getCount() throws SQLException, IOException, FrameworkException {
        prepareCount();
        return super.selectRow().getBigDecimal(ROW_COUNT).intValue();
    }

    @Override
    public int getCount(String connectionName) throws SQLException, IOException, FrameworkException {
        prepareCount();
        return super.selectRow(connectionName).getBigDecimal(ROW_COUNT).intValue();
    }

    @Override
    public int getCount(Connection connection) throws SQLException, IOException, FrameworkException {
        prepareCount();
        return super.selectRow(connection).getBigDecimal(ROW_COUNT).intValue();
    }

    private void prepareSelect(int start, int end) {
        this.sql = "select * from (select rownum rowCount, baseQuery.* from (\n\n" + super.getFilteredStatement() + ") baseQuery ) where rowCount between ? and ?";
        this.start = start;
        this.end = end;
    }

    @Override
    public DataTable<?> select(int start, int end) throws SQLException, IOException, FrameworkException {
        prepareSelect(start, end);
        return super.selectTable();
    }

    @Override
    public DataTable<?> select(String connectionName, int start, int end) throws SQLException, IOException, FrameworkException {
        prepareSelect(start, end);
        return super.selectTable(connectionName);
    }

    @Override
    public DataTable<?> select(Connection connection, int start, int end) throws SQLException, IOException, FrameworkException {
        prepareSelect(start, end);
        return super.selectTable(connection);
    }

}

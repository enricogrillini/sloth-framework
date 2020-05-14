package it.eg.sloth.db.query.pagedquery;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;

/**
 * @author Enrico Grillini
 */
public class PagedQuery extends FilteredQuery implements PagedQueryInterface {

  private static final String ROW_COUNT = "rowCount";

    private String pagedQuery;
    private int start;
    private int end;

    public PagedQuery(String statement) {
        super(statement);
    }

    @Override
    protected String getFilteredStatement() {
        return pagedQuery;
    }

    @Override
    protected PreparedStatement getPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getFilteredStatement());

        int i = addValues(statement, 1);

        if (start != -1) {
            statement.setObject(i++, new BigDecimal(start), Types.INTEGER);
            statement.setObject(i, new BigDecimal(end), Types.INTEGER);
        }

        return statement;
    }

    private void prepareCount() {
        this.pagedQuery = "select count(*) rowCount from (" + super.getFilteredStatement() + ")";
        this.start = -1;
    }

    @Override
    public int getCount() throws SQLException, IOException {
        prepareCount();
        return super.selectRow().getBigDecimal(ROW_COUNT).intValue();
    }

    @Override
    public int getCount(String connectionName) throws SQLException, IOException {
        prepareCount();
        return super.selectRow(connectionName).getBigDecimal(ROW_COUNT).intValue();
    }

    @Override
    public int getCount(Connection connection) throws SQLException, IOException {
        prepareCount();
        return super.selectRow(connection).getBigDecimal(ROW_COUNT).intValue();
    }

    private void prepareSelect(int start, int end) {
        this.pagedQuery = "select * from (select rownum rowCount, baseQuery.* from (\n\n" + super.getFilteredStatement() + ") baseQuery ) where rowCount between ? and ?";
        this.start = start;
        this.end = end;
    }

    @Override
    public DataTable<?> select(int start, int end) throws SQLException, IOException {
        prepareSelect(start, end);
        return super.selectTable();
    }

    @Override
    public DataTable<?> select(String connectionName, int start, int end) throws SQLException, IOException {
        prepareSelect(start, end);
        return super.selectTable(connectionName);
    }

    @Override
    public DataTable<?> select(Connection connection, int start, int end) throws SQLException, IOException {
        prepareSelect(start, end);
        return super.selectTable(connection);
    }

}

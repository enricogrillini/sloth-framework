package it.eg.sloth.db.query;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.column.Column;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
 *
 * @author Enrico Grillini
 */
class BaseQueryTest extends AbstractDbTest {


    @Test
    void selectTableQueryTest() throws FrameworkException, SQLException, IOException {
        Query query = new Query("Select * from prova");

        selectTableAssertion(query.selectTable());
    }

    @Test
    void selectTableFilteredQueryTest() throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery("Select * from prova");

        selectTableAssertion(query.selectTable());
    }

    private void selectTableAssertion(DataTable dataTable) {
        assertEquals(2, dataTable.size());
    }

    @Test
    void selectRowQueryTest() throws FrameworkException, SQLException, IOException {
        Query query = new Query("Select * from prova where id = ?");
        query.addParameter(Types.INTEGER, BigDecimal.valueOf(1));

        selectRowAssertion(query.selectRow());
    }

    @Test
    void selectRowFilteredQueryTest() throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery("Select * from prova /*W*/");
        query.addFilter("id = ?", Types.INTEGER, BigDecimal.valueOf(1));

        selectRowAssertion(query.selectRow());
    }

    private void selectRowAssertion(DataRow dataRow) throws FrameworkException {
        assertEquals(BigDecimal.valueOf(1), dataRow.getBigDecimal("Id"));
        assertEquals("Aaaaa", dataRow.getString("Testo"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020"), dataRow.getTimestamp("Data"));
    }

    @Test
    void columnListQueryTest() throws FrameworkException, SQLException, IOException {
        Query query = new Query("Select * from prova where id = ?");
        query.addParameter(Types.INTEGER, BigDecimal.valueOf(1));

        columnListAsserion(query.columnList());
    }

    @Test
    void columnListFilteredQueryTest() throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery("Select * from prova /*W*/");
        query.addFilter("id = ?", Types.INTEGER, BigDecimal.valueOf(1));

        columnListAsserion(query.columnList());
    }

    private void columnListAsserion(List<Column> columnList) {
        assertEquals(3, columnList.size());

        Column column = columnList.get(0);
        assertEquals("ID", column.getName());
        assertEquals(Types.INTEGER, column.getJavaType());

        column = columnList.get(1);
        assertEquals("TESTO", column.getName());
        assertEquals(Types.VARCHAR, column.getJavaType());

        column = columnList.get(2);
        assertEquals("DATA", column.getName());
        assertEquals(Types.TIMESTAMP, column.getJavaType());
    }

}


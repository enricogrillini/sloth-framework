package it.eg.sloth.db;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
 *
 * @author Enrico Grillini
 */
public class TableTest {

    private static final String TO_STRING = ResourceUtil.normalizedResourceAsString("snippet-text/table-to-string.txt");

    Table table;

    @Before
    public void init() {
        table = new Table();
        Row row = table.add();
        row.setString("key1", "value1");
        row.setBigDecimal("key2", BigDecimal.valueOf(10));

        row = table.add();
        row.setString("key1", "value2");
        row.setBigDecimal("key2", BigDecimal.valueOf(30));
    }

    /**
     * Verifica il toString
     */
    @Test
    public void tableToStringTest() {
        assertEquals(TO_STRING, table.toString());
    }

    /**
     * Verifica il Sum
     */
    @Test
    public void tableSumTest() {
        assertEquals(BigDecimal.valueOf(40), table.sum("key2"));
    }

    /**
     * Verifica il Distinct
     */
    @Test
    public void tableDistinctTest() {
        Set<Object> set = table.distinct("key1");

        assertEquals(2, set.size());
        assertTrue(set.contains("value1"));
        assertTrue(set.contains("value2"));
    }
}


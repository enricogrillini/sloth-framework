package it.eg.sloth.db;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.datasource.table.filter.FilterRule;
import it.eg.sloth.db.datasource.table.filter.FilterRules;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
 *
 * @author Enrico Grillini
 */
class TableTest {

    private static final String TO_STRING = ResourceUtil.normalizedResourceAsString("snippet-text/table-to-string.txt");

    Table table;

    @BeforeEach
    void init() {
        table = new Table();
        Row row = table.add();
        row.setString("key1", "value1");
        row.setBigDecimal("key2", BigDecimal.valueOf(10));

        row = table.add();
        row.setString("key1", "value2");
        row.setBigDecimal("key2", BigDecimal.valueOf(30));

        row = table.add();
        row.setString("key1", "value3");
        row.setBigDecimal("key2", BigDecimal.valueOf(30));

        row = table.add();
        row.setString("key1", "value4");
        row.setBigDecimal("key2", BigDecimal.valueOf(100));
    }

    /**
     * Verifica il toString
     */
    @Test
    void toStringTest() {
        assertEquals(TO_STRING, table.toString());
    }

    /**
     * Verifica il Sum
     */
    @Test
    void sumTest() {
        assertEquals(BigDecimal.valueOf(170), table.sum("key2"));
    }

    /**
     * Verifica il Distinct
     */
    @Test
    void distinctTest() {
        Set<Object> set = table.distinct("key1");

        assertEquals(4, set.size());
        assertTrue(set.contains("value1"));
        assertTrue(set.contains("value2"));
    }

    /**
     * Verifica il removeAllRow
     */
    @Test
    void removeAllRowTest() throws FrameworkException {
        table.removeAllRow();

        assertEquals(0, table.size());
    }

    /**
     * Verifica il remove by Filter
     */
    @Test
    void removeByFilterTest() throws FrameworkException {
        // Test generico
        FilterRules filterRules = new FilterRules();
        filterRules.addValueRule("key2", BigDecimal.valueOf(30));
        table.removeByFilter(filterRules);

        assertEquals(2, table.size());

        // Test cancellazione ultimo record
        Row row = table.add();
        row.setString("key1", "value3");
        row.setBigDecimal("key2", BigDecimal.valueOf(30));

        assertEquals(3, table.size());
        table.removeByFilter(filterRules);
        assertEquals(2, table.size());

        // Test tabella vuota
        table.removeAllRow();

        assertEquals(0, table.size());
        table.removeByFilter(filterRules);
        assertEquals(0, table.size());


        // Test cancellazione tabella con 1 record
        row = table.add();
        row.setString("key1", "value3");
        row.setBigDecimal("key2", BigDecimal.valueOf(30));

        assertEquals(1, table.size());
        table.removeByFilter(filterRules);
        assertEquals(0, table.size());
    }
}


package it.eg.sloth.db;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.db.datasource.table.sort.SortingRules;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class SortingRuleTest {

    @Test
    void sortingRuleBigDecimalTest() {
        Row row1 = new Row();
        row1.setBigDecimal("provaBigDecimal", BigDecimal.valueOf(100));

        Row row2 = new Row();
        row2.setBigDecimal("provaBigDecimal", BigDecimal.valueOf(10));

        SortingRules sortingRules = new SortingRules();
        sortingRules.add("provaBigDecimal", SortType.SORT_ASC_NULLS_LAST);
        assertEquals(0, sortingRules.compare(row1, row1));
        assertTrue(sortingRules.compare(row1, row2) > 0);
        assertTrue(sortingRules.compare(row2, row1) < 0);


        sortingRules = new SortingRules();
        sortingRules.add("provaBigDecimal", SortType.SORT_DESC_NULLS_LAST);
        assertEquals(0, sortingRules.compare(row1, row1));
        assertTrue(sortingRules.compare(row1, row2) < 0);
        assertTrue(sortingRules.compare(row2, row1) > 0);
    }

    @Test
    void sortingRuleTimestampTest() throws FrameworkException {
        Row row1 = new Row();
        row1.setTimestamp("provaTimestamp", TimeStampUtil.parseTimestamp("01/01/2020", "dd/mm/yyyy"));

        Row row2 = new Row();
        row2.setTimestamp("provaTimestamp", TimeStampUtil.parseTimestamp("01/01/2019", "dd/mm/yyyy"));

        SortingRules sortingRules = new SortingRules();
        sortingRules.add("provaTimestamp", SortType.SORT_ASC_NULLS_LAST);
        assertEquals(0, sortingRules.compare(row1, row1));
        assertTrue(sortingRules.compare(row1, row2) > 0);
        assertTrue(sortingRules.compare(row2, row1) < 0);


        sortingRules = new SortingRules();
        sortingRules.add("provaTimestamp", SortType.SORT_DESC_NULLS_LAST);
        assertEquals(0, sortingRules.compare(row1, row1));
        assertTrue(sortingRules.compare(row1, row2) < 0);
        assertTrue(sortingRules.compare(row2, row1) > 0);
    }
}

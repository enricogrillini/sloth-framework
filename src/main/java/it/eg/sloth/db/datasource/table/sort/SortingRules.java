package it.eg.sloth.db.datasource.table.sort;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.framework.FrameComponent;

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
public class SortingRules<T extends DataRow> extends FrameComponent implements Comparator<T> {

    private List<SortingRule> list;

    public SortingRules() {
        list = new ArrayList<>();
    }

    public void clear() {
        list.clear();
    }

    public SortingRule getFirstRule() {
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public SortingRule add(String fieldName) {
        SortingRule sortingRule = new SortingRule(fieldName, SortingRule.SORT_ASC_NULLS_LAST);
        list.add(sortingRule);

        return sortingRule;
    }

    public SortingRule add(String fieldName, int sortType) {
        SortingRule sortingRule = new SortingRule(fieldName, sortType);
        list.add(sortingRule);

        return sortingRule;
    }

    private int checkNull(SortingRule sortingRule, Object value1, Object value2) {
        if (value1 == null && value2 == null)
            return 0;

        if (value1 != null && value2 == null) {
            if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_DESC_NULLS_LAST)
                return -1;
            else
                return 1;
        }

        if (value1 == null) {
            if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_DESC_NULLS_LAST)
                return 1;
            else
                return -1;
        }

        return 0;
    }

    private int check(SortingRule sortingRule, BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1.equals(bigDecimal2))
            return 0;
        else if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_FIRST)
            return bigDecimal1.compareTo(bigDecimal2) > 0 ? 1 : -1;
        else
            return bigDecimal1.compareTo(bigDecimal2) > 0 ? -1 : 1;
    }

    private int check(SortingRule sortingRule, Timestamp timestamp1, Timestamp timestamp2) {
        if (timestamp1.equals(timestamp2))
            return 0;
        else if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_FIRST)
            return timestamp1.compareTo(timestamp2) > 0 ? 1 : -1;
        else
            return timestamp1.compareTo(timestamp2) > 0 ? -1 : 1;
    }

    private int check(SortingRule sortingRule, String string1, String string2) {
        if (!string1.equals(string2))
            return string1.compareTo(string2) * sortingRule.getSortType();
        else
            return 0;
    }

    @Override
    public int compare(T row1, T row2) {
        for (SortingRule sortingRule : list) {
            Object value1 = row1.getObject(sortingRule.getFieldName());
            Object value2 = row2.getObject(sortingRule.getFieldName());

            // Verifico i null
            int result = checkNull(sortingRule, value1, value2);
            if (result != 0) {
                return result;
            }

            // Verifico BigDecimal, Timestamp, String
            if (value1 instanceof BigDecimal) {
                result = check(sortingRule, (BigDecimal) value1, (BigDecimal) value2);
            } else if (value1 instanceof Timestamp) {
                result = check(sortingRule, (Timestamp) value1, (Timestamp) value2);
            } else if (value1 instanceof String) {
                result = check(sortingRule, (String) value1, (String) value2);
            }

            if (result != 0) {
                return result;
            }
        }

        return 0;
    }


}

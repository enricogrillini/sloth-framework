package it.eg.sloth.db.datasource.table.filter;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.table.filter.impl.TextSearchRule;
import it.eg.sloth.db.datasource.table.filter.impl.ValueRule;
import lombok.ToString;

import java.util.List;
import java.util.Stack;

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
public class FilterRules {

    private List<FilterRule> list;

    public FilterRules() {
        list = new Stack<>();
    }

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public void add(FilterRule filterRule) {
        list.add(filterRule);
    }

    public void addValueRule(String fieldName, Object fieldValue) {
        list.add(new ValueRule(fieldName, fieldValue));
    }

    public void addTextSearchRule(String textSearh) {
        list.add(new TextSearchRule(textSearh));
    }

    public boolean check(DataSource dataSource) {
        for (FilterRule filterRule : list) {
            if (!filterRule.check(dataSource)) {
                return false;
            }
        }

        return true;
    }

}

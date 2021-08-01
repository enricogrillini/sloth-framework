package it.eg.sloth.db.datasource.row;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataSource;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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
@Slf4j
public class Row implements DataRow {

    protected Map<String, Object> entries;

    public Row() {
        entries = new LinkedHashMap<>();
    }

    public Row(DataSource dataSource) {
        this();
        copyFromDataSource(dataSource);
    }

    @Override
    public Object getObject(String name) {
        return entries.get(name.toLowerCase());
    }

    @Override
    public Row setObject(String name, Object value) {
        entries.put(name.toLowerCase(), value);
        return this;
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public Collection<String> keys() {
        return entries.keySet();
    }

    @Override
    public Collection<Object> values() {
        return entries.values();
    }

    @Override
    public Map<String, Object> entries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Row{" +
                "values=" + entries +
                '}';
    }
}

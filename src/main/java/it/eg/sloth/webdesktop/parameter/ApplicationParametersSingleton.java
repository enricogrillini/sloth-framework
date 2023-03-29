package it.eg.sloth.webdesktop.parameter;


import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.parameter.model.ApplicationParameter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

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
@Slf4j
public class ApplicationParametersSingleton {

    private static ApplicationParametersSingleton instance = null;

    Map<String, ApplicationParameter> map;

    private ApplicationParametersSingleton() {
        map = new LinkedHashMap<>();
        log.info("Parameters inizializzati");
    }

    public static synchronized ApplicationParametersSingleton getInstance() {
        if (instance == null) {
            instance = new ApplicationParametersSingleton();
        }

        return instance;
    }

    public synchronized void clear() {
        map.clear();
    }

    public synchronized void put(ApplicationParameter applicationParameter) {
        map.put(applicationParameter.getCodParameter(), applicationParameter);
    }

    public Table getTable() {
        Table table = new Table();
        for (ApplicationParameter parameter : map.values()) {
            table.add(parameter.toRow());
        }

        return table;
    }

    public void loadTable(DataTable<?> table) {
        clear();
        for (DataRow row : table) {
            put(new ApplicationParameter().fromRow(row));
        }
    }

    public ApplicationParameter getApplicationParameter(String name) {
        return map.get(name);
    }


    public Object getApplicationParameterParsedValue(String name, Locale locale) throws FrameworkException {
        if (map.containsKey(name)) {
            return getApplicationParameter(name).getParsedValue(locale);
        } else {
            return null;
        }
    }

}

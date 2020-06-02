package it.eg.sloth.db.decodemap.map;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.value.TableDecodeValue;

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
public class TableDecodeMap<T, R extends DataRow> extends AbstractDecodeMap<T, TableDecodeValue<T, R>> {

    public TableDecodeMap() {
        super();
    }

    public TableDecodeMap(DataTable<R> dataTable, String codeName, String descriptionName, String validName) {
        super();
        load(dataTable, codeName, descriptionName, validName);
    }

    public void load(DataTable<R> dataTable, String codeName, String descriptionName, String validName) {
        clear();

        if (dataTable != null) {
            for (R dataRow : dataTable) {
                TableDecodeValue<T, R> tableDecodeValue;
                if (validName == null) {
                    tableDecodeValue = new TableDecodeValue<>(dataRow, codeName, descriptionName);
                } else {
                    tableDecodeValue = new TableDecodeValue<>(dataRow, codeName, descriptionName, "S".equals(dataRow.getString(validName)));
                }

                put(tableDecodeValue);
            }
        }
    }

    public void load(DataTable<R> dataTable, String codeName, String descriptionName) {
        load(dataTable, codeName, descriptionName, null);
    }

    public R getRowBean(T code) {
        if (contains(code)) {
            return get(code).getDataRow();
        } else {
            return null;
        }
    }

}

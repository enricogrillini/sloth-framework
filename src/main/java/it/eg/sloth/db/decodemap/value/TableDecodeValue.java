package it.eg.sloth.db.decodemap.value;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.decodemap.DecodeValue;
import lombok.AllArgsConstructor;
import lombok.Data;

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
 * <p>
 *
 * @author Enrico Grillini
 */
@Data
@AllArgsConstructor
public class TableDecodeValue<T, R extends DataRow> implements DecodeValue<T> {

    private R dataRow;
    private String codeName;
    private String descriptionName;
    private boolean valid;

    public TableDecodeValue(R dataRow, String codeName, String descriptionName) {
        this(dataRow, codeName, descriptionName, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getCode() {
        return (T) dataRow.getObject(codeName);
    }

    @Override
    public String getDescription() {
        return dataRow.getString(descriptionName);
    }

    public R getDataRow() {
        return dataRow;
    }


}

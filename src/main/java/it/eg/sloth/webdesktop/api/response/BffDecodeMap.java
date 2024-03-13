package it.eg.sloth.webdesktop.api.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.value.BaseDecodeValue;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.api.model.BffResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString(callSuper=true)
public class BffDecodeMap extends BffResponse {
    List<DecodeValue<String>> values;

    public BffDecodeMap() {
        values = new ArrayList<>();
    }

    public void load(DataTable<?> table, String codeName, String descriptionName) throws FrameworkException {
        load(table, codeName, DataTypes.STRING, Locale.ITALY, descriptionName);
    }

    public void load(DataTable<?> table, String codeName, DataTypes dataTypes, Locale locale, String descriptionName) throws FrameworkException {
        values.clear();
        for (DataRow row : table) {
            String code = dataTypes.formatValue(row.getObject(codeName), locale);
            String description = row.getString(descriptionName);

            BaseDecodeValue<String> decodeValue = new BaseDecodeValue<>(code, description);
            values.add(decodeValue);
        }
    }

}

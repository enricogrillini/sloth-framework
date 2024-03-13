package it.eg.sloth.webdesktop.parameter.model;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.form.fields.field.base.MultipleInput;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.*;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public class ApplicationParameter {

    String codParameter;
    DataTypes dataType;
    boolean multivalue;
    String value;

    public Object getParameterValue(Locale locale) throws FrameworkException {
        return dataType.parseValue(value, locale);
    }

    public Set<Object> getParameterValues(Locale locale) throws FrameworkException {
        Set<Object> result = new LinkedHashSet<>();
        if (multivalue) {
            result.addAll(Arrays.asList(StringUtil.split(value, MultipleInput.DELIMITER)));
        } else {
            result.add(getParameterValue(locale));
        }

        return result;
    }

    public Set<String> getParameterFormatedValues(Locale locale) throws FrameworkException {
        Set<Object> input = getParameterValues(locale);

        Set<String> result = new LinkedHashSet<>();
        for (Object o : input) {
            result.add(dataType.formatValue(o, locale));
        }

        return result;
    }

    public Row toRow() {
        Row row = new Row();
        row.setString("codParameter", getCodParameter());
        row.setString("dataType", getDataType().name());
        row.setString("flagMultivalue", isMultivalue() ? "S" : "N");
        row.setString("value", getValue());

        return row;
    }

    public ApplicationParameter fromRow(DataRow row) {
        setCodParameter(row.getString("codParameter"));
        setDataType(DataTypes.valueOf(row.getString("dataType")));
        setMultivalue("S".equals(row.getString("flagMultivalue")));
        setValue(row.getString("value"));

        return this;
    }

}

package it.eg.sloth.db.datasource.row.column;

import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.CheckBox;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Types;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
@AllArgsConstructor
public class Column {
    private String name;
    private String description;
    private boolean primaryKey;
    private boolean nullable;
    private Integer dataLength;
    private int javaType;

    public <T> InputField<T> getInputField() {
        if (getName().toLowerCase().startsWith("flag")) {
            return (InputField<T>) CheckBox.builder()
                    .name(getName())
                    .description(BaseFunction.nvl(getDescription(), getName()))
                    .dataType(getDataTypes())
                    .viewModality(ViewModality.AUTO)
                    .required(!isNullable())
                    .build();
        } else {
            return Input.<T>builder()
                    .name(getName())
                    .description(BaseFunction.nvl(getDescription(), getName()))
                    .dataType(getDataTypes())
                    .viewModality(ViewModality.AUTO)
                    .maxLength(getMaxLength())
                    .required(!isNullable())
                    .build();
        }
    }

    public <T> ComboBox<T> getComboBox() {
        return ComboBox.<T>builder()
                .name(getName())
                .description(BaseFunction.nvl(getDescription(), getName()))
                .dataType(getDataTypes())
                .viewModality(ViewModality.AUTO)
                .required(!isNullable())
                .build();

    }

    private Integer getMaxLength() {
        if (getDataTypes() == DataTypes.STRING) {
            return getDataLength();
        } else {
            return null;
        }
    }

    private DataTypes getDataTypes() {
        if (javaType == Types.DECIMAL) {
            return DataTypes.DECIMAL;
        } else if (javaType == Types.INTEGER) {
            return DataTypes.INTEGER;
        } else if (javaType == Types.VARCHAR) {
            return DataTypes.STRING;
        } else if (javaType == Types.DATE) {
            return DataTypes.DATE;
        } else {
            return DataTypes.STRING;
        }
    }
}

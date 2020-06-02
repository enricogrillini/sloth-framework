package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
@Getter
@Setter
@SuperBuilder
public class CheckBox<T> extends InputField<T> {

    public static final String DEFAULT_VAL_CHECKED = "S";
    public static final String DEFAULT_VAL_UN_CHECKED = "N";

    T valChecked;
    T valUnChecked;

    public CheckBox(String name, String description, String tooltip, DataTypes dataType) {
        super(name, description, tooltip, dataType);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.CHECK_BOX;
    }

    public boolean isChecked() {
        return !BaseFunction.isBlank(getData()) && getData().equals(getValChecked());
    }

    public T getValChecked() {
        return valChecked == null ? (T) DEFAULT_VAL_CHECKED : valChecked;
    }

    public T getValUnChecked() {
        return valUnChecked == null ? (T) DEFAULT_VAL_UN_CHECKED : valUnChecked;
    }

    public void setChecked() throws FrameworkException {
        setValue(getValChecked());
    }

    public void setUnChecked() throws FrameworkException {
        setValue(getValUnChecked());
    }

    @Override
    public void post(WebRequest webRequest) {
        if (!isReadOnly()) {
            if (webRequest.getString(getName()) == null) {
                setData((String) BaseFunction.nvl(getValUnChecked(), "N"));
            } else {
                setData((String) BaseFunction.nvl(getValChecked(), "S"));
            }
        }
    }

}

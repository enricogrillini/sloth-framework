package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.jaxb.form.ForceCase;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

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
@SuperBuilder(toBuilder = true)
public class Input<T> extends InputField<T> {

    private ForceCase forceCase;
    private Integer maxLength;

    @Override
    public void setData(String data) {
        if (BaseFunction.in(getForceCase(), ForceCase.TRIM, ForceCase.INIT_CAP_TRIM, ForceCase.UPPER_TRIM, ForceCase.LOWER_TRIM)) {
            data = StringUtil.trim(data);
            data = StringUtils.normalizeSpace(data);
        }

        if (BaseFunction.in(getForceCase(), ForceCase.INIT_CAP, ForceCase.INIT_CAP_TRIM)) {
            data = StringUtil.initCap(data);
        } else if (BaseFunction.in(getForceCase(), ForceCase.UPPER, ForceCase.UPPER_TRIM)) {
            data = StringUtil.upper(data);
        } else if (BaseFunction.in(getForceCase(), ForceCase.LOWER, ForceCase.LOWER_TRIM)) {
            data = StringUtil.lower(data);
        }

        super.setData(data);
    }

    public Input(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }

    public ForceCase getForceCase() {
        return forceCase == null ? ForceCase.NONE : forceCase;
    }

    public int getMaxLength() {
        return maxLength == null ? 0 : maxLength;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.INPUT;
    }

    @Override
    public Input<T> newInstance() {
        return toBuilder().build();
    }
}

package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
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
@SuperBuilder
public class TextArea<T> extends Input<T> {

    public TextArea(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }


    @Override
    public FieldType getFieldType() {
        return FieldType.TEXT_AREA;
    }

    @Override
    public String escapeHtmlText() {
        if (getHtmlEscaper() == null) {
            return Casting.getHtml(getData(), true, false);
        } else {
            return getHtmlEscaper().escapeValue(getData());
        }
    }

}

package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.map.BaseDecodeMap;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.framework.common.casting.DataTypes;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
public abstract class DecodedTextField<T> extends TextField<T> implements DecodedDataField<T> {

    private DecodeMap<T, ? extends DecodeValue<T>> values;

    protected DecodedTextField(String name, String description, String tooltip, DataTypes dataType) {
        this(name, name, description, tooltip, dataType, null, null);
    }

    protected DecodedTextField(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink) {
        super(name, description, dataType);
        this.alias = alias;
        this.tooltip = tooltip;
        this.format = format;
        this.baseLink = baseLink;

        values = new BaseDecodeMap<>();
    }

    @Override
    public DecodeMap<T, ? extends DecodeValue<T>> getDecodeMap() {
        return values;
    }

    @Override
    public void setDecodeMap(DecodeMap<T, ? extends DecodeValue<T>> values) {
        this.values = values;
    }

}

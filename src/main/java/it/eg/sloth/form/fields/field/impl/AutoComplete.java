package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
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
@SuperBuilder(toBuilder = true)
public class AutoComplete<T> extends InputField<T> implements DecodedDataField<T> {

    public static final int DEFAULT_SIZELIMIT = 15;

    String decodeAlias;
    String decodedText;
    Integer sizeLimit;

    private DecodeMap<T, ? extends DecodeValue<T>> decodeMap;

    public AutoComplete(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.AUTO_COMPLETE;
    }

    public int getSizeLimit() {
        return sizeLimit == null ? DEFAULT_SIZELIMIT : sizeLimit;
    }

    public String getDecodeAlias() {
        if (!BaseFunction.isBlank(decodeAlias)) {
            return decodeAlias;
        } else {
            return getName();
        }
    }

    @Override
    public DecodeMap<T, ? extends DecodeValue<T>> getDecodeMap() {
        return decodeMap;
    }

    @Override
    public void setDecodeMap(DecodeMap<T, ? extends DecodeValue<T>> decodeMap) {
        this.decodeMap = decodeMap;
    }

    @Override
    public void setValue(T value) throws FrameworkException {
        super.setValue(value);
        setDecodedText(getDecodeMap().decode(value));
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            super.copyFromDataSource(dataSource);
            if (BaseFunction.isBlank(decodeAlias) && getDecodeMap() != null && !getDecodeMap().isEmpty()) {
                setDecodedText(getDecodeMap().decode(getValue()));
            } else {
                setDecodedText(DataTypes.STRING.formatValue(dataSource.getObject(getDecodeAlias()), getLocale()));
            }
        }
    }

    @Override
    public void copyToDataSource(DataSource dataSource) {
        if (dataSource != null) {
            super.copyToDataSource(dataSource);
            if (!BaseFunction.isBlank(decodeAlias)) {
                dataSource.setObject(getDecodeAlias(), getDecodedText());
            }
        }
    }

    @Override
    public Message check() {
        // Verifico che quanto imputato sia un valore ammissibile
        if (!BaseFunction.isBlank(getDecodedText()) && BaseFunction.isBlank(getData())) {
            return new Message(Level.WARN, getName(), "Il campo " + getDescription() + " non è valido", null);
        }

        // Verifico la correttezza formale di quanto contenuto nella request
        Message message = null;
        if ((message = super.check()) != null) {
            return message;
        }

        return message;
    }

    @Override
    public void post(WebRequest webRequest) throws FrameworkException {
        if (!isReadOnly()) {
            setDecodedText(webRequest.getString(getName()));
            setData(getDataType().formatValue(getDecodeMap().encode(getDecodedText()), getLocale(), getFormat()));
        }
    }

    @Override
    public AutoComplete<T> newInstance() {
        return toBuilder().build();
    }

}

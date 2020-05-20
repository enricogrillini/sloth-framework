package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.BaseMessage;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.pageinfo.ViewModality;
import lombok.Getter;
import lombok.Setter;

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
public class AutoComplete<T> extends InputField<T> implements DecodedDataField<T> {

    public static final int DEFAULT_SIZELIMIT = 15;

    static final long serialVersionUID = 1L;

    String decodeAlias;
    String decodedText;
    int sizeLimit;

    private DecodeMap<T, ? extends DecodeValue<T>> decodeMap;

    public AutoComplete(String name, String description, String tooltip, DataTypes dataType) {
        this(name, name, name, description, tooltip, dataType, null, null, false, false, false, ViewModality.VIEW_AUTO, 0);
    }


    public AutoComplete(String name, String alias, String decodeAlias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality, Integer sizeLimit) {
        super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality);
        this.decodeAlias = decodeAlias;
        this.sizeLimit = sizeLimit == null ? 0 : sizeLimit;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.AUTO_COMPLETE;
    }

    public int getSizeLimit() {
        if (sizeLimit > 0) {
            return sizeLimit;
        } else {
            return DEFAULT_SIZELIMIT;
        }
    }

    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public String getDecodeAlias() {
        if (!BaseFunction.isBlank(decodeAlias)) {
            return decodeAlias;
        } else {
            return getName();
        }
    }

    public void setDecodeAlias(String decodeAlias) {
        this.decodeAlias = decodeAlias;
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
    public String getHtmlDecodedText() {
        return getHtmlDecodedText(true, true);
    }

    @Override
    public String getHtmlDecodedText(boolean br, boolean nbsp) {
        if (BaseFunction.isNull(getDecodedText())) {
            return "";
        } else {
            return Casting.getHtml(getDecodedText(), br, nbsp);
        }
    }

    @Override
    public String getJsDecodedText() {
        return Casting.getJs(getDecodedText());
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
            return new BaseMessage(Level.WARN, "Il campo " + getDescription() + " non è valido", null);
        }

        // Verifico la correttezza formale di quanto contenuto nella request
        Message message = null;
        if ((message = super.check()) != null) {
            return message;
        }

        return message;
    }

    @Override
    public void post(WebRequest webRequest) {
        try {
            if (!isReadOnly()) {
                setDecodedText(webRequest.getString(getName()));
                setData(getDataType().formatValue(getDecodeMap().encode(getDecodedText()), getLocale(), getFormat()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore su postEscaped campo: " + getName(), e);
        }
    }

}

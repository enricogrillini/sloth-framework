package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


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
@Slf4j
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class MultipleAutoComplete<L extends List<T>, T> extends InputField<L> {

    private String decodeAlias;
    private String decodedText;
    private DecodeMap<T, ? extends DecodeValue<T>> decodeMap;

    public MultipleAutoComplete(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.MULTIPLE_AUTO_COMPLETE;
    }

    public String getDecodeAlias() {
        if (!BaseFunction.isBlank(decodeAlias)) {
            return decodeAlias;
        } else {
            return getName();
        }
    }

    public String getHtmlDecodedText() {
        return getHtmlDecodedText(true, true);
    }

    public String getHtmlDecodedText(boolean br, boolean nbsp) {
        if (BaseFunction.isNull(getDecodedText())) {
            return "";
        } else {
            return Casting.getHtml(getDecodedText(), br, nbsp);
        }
    }

    public String getJsDecodedText() {
        return Casting.getJs(getDecodedText());
    }

    @SuppressWarnings("unchecked")
    @Override
    public L getValue() {
        String text = getData();

        List<T> list = new ArrayList();
        if (!BaseFunction.isBlank(text)) {
            String[] words = StringUtil.split(text, ",");
            for (String word : words) {
                if (!BaseFunction.isBlank(word)) {
                    try {
                        list.add((T) getDataType().parseValue(word, getLocale(), getFormat()));
                    } catch (FrameworkException e) {
                        // NOP
                    }
                }
            }
        }

        return (L) list;
    }

    @SuppressWarnings("unchecked")
    public void setValue(DataTable<?> dataTable, String columnName) throws FrameworkException {
        L list = (L) new ArrayList<T>();
        for (DataRow dataRow : dataTable) {
            list.add((T) dataRow.getObject(columnName));
        }

        setValue(list);
    }

    @Override
    public void setValue(L list) throws FrameworkException {
        String text = "";
        String decodeText = "";

        if (!BaseFunction.isNull(list)) {
            for (T value : list) {
                text += BaseFunction.isBlank(text) ? getDataType().formatValue(value, getLocale(), getFormat()) : ", " + getDataType().formatValue(value, getLocale(), getFormat());
                decodeText += BaseFunction.isBlank(decodeText) ? getDecodeMap().decode(value) : ", " + getDecodeMap().decode(value);
            }
        }

        setData(text);
        setDecodedText(decodeText);
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            super.copyFromDataSource(dataSource);
            if (BaseFunction.isBlank(decodeAlias) && getDecodeMap() != null && !getDecodeMap().isEmpty()) {
                // FIXME: setDecodeText(getDecodeMap().decode(getValue()));
            } else {
                // setDecodeText((Casting.format(dataSource.getObject(getDecodeAlias()),
                // DataTypes.STRING)));
            }
        }
    }

    /**
     * Il DataSource à impostato come una string contenete la lista di valori
     * formattati
     */
    @Override
    public void copyToDataSource(DataSource dataSource) {
        if (dataSource != null) {
            dataSource.setString(getAlias(), getData());
            if (!BaseFunction.isBlank(decodeAlias)) {
                dataSource.setObject(getDecodeAlias(), getDecodedText());
            }
        }
    }

    @Override
    public Message check() {
        Message message = null;

        // Verifico che quanto imputato sia un valore ammissibile
        if (!BaseFunction.isBlank(getDecodedText()) && BaseFunction.isBlank(getData())) {
            return new Message(Level.WARN, getName(), "Il campo " + getDescription() + " non è valido", null);
        } else if (!BaseFunction.isBlank(getDecodedText()) && !BaseFunction.isBlank(getData())) {
            if (StringUtil.split(getDecodedText(), ",").length != StringUtil.split(getData(), ",").length) {
                return new Message(Level.WARN, getName(),"Non tutti i valori del campo " + getDescription() + " sono validi", null);
            }
        }

        // FIXME: Sarebbe da verificare la corettezza formale di tuti i campi
        // sottostanti
        // // Verifico la correttezza formale di quanto contenuto nella request
        // Message message = null;
        // if ((message = super.check()) != null) {
        // return message;
        // }

        return message;
    }

    private void localPost(String string) throws FrameworkException {
        String decodedText = "";
        String text = "";
        if (!BaseFunction.isBlank(string)) {
            String[] words = StringUtil.split(string, ",");
            for (String word : words) {
                if (!BaseFunction.isBlank(word)) {
                    decodedText += BaseFunction.isBlank(decodedText) ? word : ", " + word;
                    text += BaseFunction.isBlank(text) ? getDataType().formatValue(getDecodeMap().encode(word), getLocale(), getFormat()) : ", " + getDataType().formatValue(getDecodeMap().encode(word), getLocale(), getFormat());
                }
            }
        }

        setDecodedText(decodedText);
        setData(text);
    }

    @Override
    public void post(WebRequest webRequest) throws FrameworkException {
        try {

            if (!isReadOnly()) {
                log.debug("post (" + getName() + "): " + webRequest.getString(getName()));

                localPost(webRequest.getString(getName()));
            }
        } catch (Exception e) {
            throw new FrameworkException(ExceptionCode.GENERIC_BUSINESS_ERROR, "Errore su post campo: " + getName(), e);
        }
    }

    @Override
    public boolean validate(MessageList messageList) {
        Message message = check();
        if (message != null) {
            messageList.add(message);
            return false;
        }

        return true;
    }

    @Override
    public MultipleAutoComplete<L, T> newInstance() {
        return toBuilder().build();
    }

}

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
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
public class MultipleAutoComplete<T> extends InputField<List<T>> {

    public static final String DELIMITER = "|";

    private String decodedText;
    private DecodeMap<T, ? extends DecodeValue<T>> decodeMap;
    private Boolean freeInput;

    public MultipleAutoComplete(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
        freeInput = false;
    }

    public boolean isFreeInput() {
        return freeInput != null && freeInput;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.MULTIPLE_AUTO_COMPLETE;
    }

    @Override
    public List<T> getValue() throws FrameworkException {
        return valueFromString(getData());
    }

    @Override
    public void setValue(List<T> values) throws FrameworkException {
        if (values == null || values.isEmpty()) {
            setData(StringUtil.EMPTY);
            setDecodedText(null);
        } else {
            StringBuilder valueBuilder = new StringBuilder();
            StringBuilder decodeBuilder = new StringBuilder();
            for (T value : values) {
                if (valueBuilder.length() > 0) {
                    valueBuilder.append(DELIMITER);
                }
                valueBuilder.append(getDataType().formatValue(value, getLocale(), getFormat()));

                if (getDecodeMap() != null) {
                    if (decodeBuilder.length() > 0) {
                        decodeBuilder.append(DELIMITER);
                    }
                    decodeBuilder.append(getDecodeMap().decode(value));
                }
            }

            setData(valueBuilder.toString());
            setDecodedText(decodeBuilder.toString());
        }
    }

    public void setValue(DataTable<?> dataTable, String columnName) throws FrameworkException {
        List<T> list = new ArrayList<>();
        for (DataRow row : dataTable) {
            list.add((T) row.getObject(columnName));
        }

        setValue(list);
    }

    public void addValue(T value) throws FrameworkException {
        List<T> list = getValue();
        list.add(value);

        setValue(list);
    }

    private String valueToString(List<T> values) throws FrameworkException {
        if (values == null || values.isEmpty()) {
            return StringUtil.EMPTY;
        } else {
            StringBuilder valueBuilder = new StringBuilder();
            for (T value : values) {
                if (valueBuilder.length() > 0) {
                    valueBuilder.append(DELIMITER);
                }
                valueBuilder.append(getDataType().formatValue(value, getLocale(), getFormat()));
            }

            return valueBuilder.toString();
        }
    }

    private List<T> valueFromString(String valueStr) throws FrameworkException {
        List<T> result = new ArrayList<>();
        if (!BaseFunction.isBlank(valueStr)) {
            for (String value : Arrays.asList(StringUtil.split(valueStr, DELIMITER))) {
                result.add((T) getDataType().parseValue(value, getLocale(), getFormat()));
            }
        }

        return result;
    }

    @Override
    public void copyToDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            dataSource.setString(getAlias(), valueToString(getValue()));
        }
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            setValue(valueFromString(dataSource.getString(getAlias())));
        }
    }

    @Override
    public void copyToBffFields(BffFields bffFields) throws FrameworkException {
        if (bffFields != null) {
            bffFields.setString(getName(), getDecodedText());
        }
    }

    @Override
    public Message check() {
        // Verifico che quanto imputato sia un valore ammissibile
        if (!BaseFunction.isBlank(getDecodedText()) && BaseFunction.isBlank(getData())) {
            return new Message(Level.WARN, getName(), "Il campo " + getDescription() + " contiene valori non validi", null);
        }

        return null;
    }

    @Override
    public boolean validate(MessageList messageList) throws FrameworkException {
        Message message = check();
        if (message != null) {
            messageList.add(message);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void post(WebRequest webRequest) throws FrameworkException {
        postString(webRequest.getString(getName()));
    }

    @Override
    public void post(BffFields bffFields) throws FrameworkException {
        postString(bffFields.getString(getName()));
    }

    private void postString(String decodedText) throws FrameworkException {
        if (!isReadOnly()) {
            setDecodedText(decodedText);
            setData(StringUtil.EMPTY);

            if (!BaseFunction.isBlank(getDecodedText())) {
                String[] texts = StringUtil.split(decodedText, DELIMITER);
                List<T> values = new ArrayList<>();
                for (String text : texts) {
                    T value = getDecodeMap().encode(text);
                    if (value != null) {
                        values.add(value);
                    } else if (isFreeInput()) {
                        values.add((T) text);
                        getDecodeMap().put((T) text, text);
                    }
                }

                if (values.size() == texts.length) {
                    setData(valueToString(values));
                }
            }
        }
    }

    @Override
    public MultipleAutoComplete<T> newInstance() {
        return toBuilder().build();
    }

    @Override
    public String getText() throws FrameworkException {
        StringBuilder stringBuilder = new StringBuilder();
        if (getValue() != null) {
            for (T value : getValue()) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(getDecodeMap().decode(value));
            }
        }

        return stringBuilder.toString();
    }

}

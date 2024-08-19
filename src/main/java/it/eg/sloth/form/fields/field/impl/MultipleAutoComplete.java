package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.MultipleInput;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.jaxb.form.ForceCase;
import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class MultipleAutoComplete<T> extends MultipleInput<T> {

    private String decodedText;
    private String invalidDecodedText;
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
        super.setValue(values);

        if (values == null || values.isEmpty()) {
            setDecodedText(null);
        } else {
            if (getDecodeMap() != null) {
                String tmp = values.stream()
                        .map(value -> getDecodeMap().decode(value))
                        .collect(Collectors.joining("|"));

                setDecodedText(tmp);

                tmp = values.stream()
                        .filter(value -> !getDecodeMap().get(value).isValid())
                        .map(value -> getDecodeMap().decode(value))
                        .collect(Collectors.joining("|"));

                setInvalidDecodedText(tmp);
            } else {
                setDecodedText("");
                setInvalidDecodedText("");
            }
        }
    }

    public void addValue(T value) throws FrameworkException {
        List<T> list = getValue();
        list.add(value);

        setValue(list);
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

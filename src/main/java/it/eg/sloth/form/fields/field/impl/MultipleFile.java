package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.MultipleInput;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.localization.Localization;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.webdesktop.api.request.BffFields;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
public class MultipleFile extends MultipleInput<String> {


    Integer maxSize;
    private List<Part> parts;

    public MultipleFile(String name, String description) {
        super(name, description, DataTypes.STRING);
    }

    @Override
    public DataTypes getDataType() {
        return DataTypes.STRING;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.MULTIPLE_FILE;
    }

    public int getMaxSize() {
        return maxSize != null ? maxSize : 0;
    }

    @Override
    public List<String> getValue() throws FrameworkException {
        return valueFromString(getData());
    }

    @Override
    public void setValue(List<String> values) throws FrameworkException {
        setData(StringUtil.join(values, DELIMITER));
    }


    public void addValue(String value) throws FrameworkException {
        getValue().add(value);
    }

    @Override
    public void post(WebRequest webRequest) {
        if (!isReadOnly()) {
            // Escludo i part non valorizzati
            List<Part> dest = new ArrayList<>();
            for (Part part : webRequest.getPartList(getName())) {
                if (!BaseFunction.isNull(part.getSubmittedFileName())) {
                    dest.add(part);
                }
            }

            setParts(dest);
        }
    }

    @Override
    public void post(BffFields bffFields) {
        // NOP
    }

    @Override
    public boolean validate(MessageList messageList) throws FrameworkException {
        if (isRequired() && (getParts() == null || getParts().isEmpty()) && getValue().isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, getLocale());
            messageList.add(new Message(Level.WARN, getName(), MessageFormat.format(bundle.getString(Localization.ERR_MANDATORY), getDescription()), ""));

            return false;
        }
        return true;
    }


    @Override
    public MultipleFile newInstance() {
        return toBuilder().build();
    }

}

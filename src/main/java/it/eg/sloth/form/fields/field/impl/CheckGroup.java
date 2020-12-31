package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.localization.Localization;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.*;


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
public class CheckGroup<L extends List<T>, T> extends InputField<L> {

    public static final String SEPARATOR = "|";

    private String values;
    private String descriptions;
    private String tooltips;

    @Override
    public FieldType getFieldType() {
        return FieldType.CHECK_GROUP;
    }


    public String[] getSplittedValues() {
        return StringUtil.split(getValues(), SEPARATOR);
    }

    public String[] getSplittedDescriptions() {
        return StringUtil.split(getDescriptions(), SEPARATOR);
    }

    public String[] getSplittedTooltips() {
        return StringUtil.split(getTooltips(), SEPARATOR);
    }


    @Override
    public L getValue() throws FrameworkException {
        String text = getData();

        List<T> list = new ArrayList<>();
        if (!BaseFunction.isBlank(text)) {
            String[] words = StringUtil.split(text, SEPARATOR);
            for (String word : words) {
                list.add((T) getDataType().parseValue(word, getLocale(), getFormat()));
            }
        }

        return (L) list;
    }

    @Override
    public void setValue(L list) throws FrameworkException {
        if (!BaseFunction.isNull(list)) {
            setData(StringUtil.join(list.toArray(new String[0]), SEPARATOR));
        } else {
            setData(StringUtil.EMPTY);
        }
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            setData(dataSource.getString(getAlias()));
        }
    }

    /**
     * Il DataSource è impostato come una string contenente la lista di valori formattati
     *
     * @param dataSource
     */
    @Override
    public void copyToDataSource(DataSource dataSource) {
        if (dataSource != null) {
            dataSource.setString(getAlias(), getData());
        }
    }

    @Override
    public Message check() {
        Message message = null;

        String[] words = StringUtil.split(getData(), SEPARATOR);

        Set<String> valueSet = new HashSet<>(Arrays.asList(getSplittedValues()));
        for (String word : words) {
            if (!valueSet.contains(word)) {
                ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, getLocale());
                return new Message(Level.WARN, getName(), MessageFormat.format(bundle.getString(Localization.ERR_MANDATORY), getDescription()), "");
            }
        }

        return message;
    }


    public boolean isChecked(T checkValue) throws FrameworkException {
        Set<T> set = new HashSet<>(getValue());

        return set.contains(checkValue);
    }

    public void setData(List<String> dataList) {
        if (!BaseFunction.isNull(dataList)) {
            setData(StringUtil.join(dataList.toArray(new String[0]), SEPARATOR));
        } else {
            setData(StringUtil.EMPTY);
        }
    }

    @Override
    public void post(WebRequest webRequest) {
        if (!isReadOnly()) {
            setData(webRequest.getStringList(getName()));
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
    public CheckGroup<L, T> newInstance() {
        return toBuilder().build();
    }

}

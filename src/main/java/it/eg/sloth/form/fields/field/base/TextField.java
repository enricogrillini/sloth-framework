package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.row.lob.LobData;
import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.Escaper;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.casting.Validator;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Locale;

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
@ToString
@SuperBuilder(toBuilder = true)
public abstract class TextField<T> implements DataField<T> {

    String name;
    Locale locale;

    String description;
    String tooltip;

    String data;

    String alias;
    String orderByAlias;
    DataTypes dataType;
    String format;
    String baseLink;
    String linkField;

    ControlState state;
    String stateMessage;

    Boolean hidden;
    Escaper htmlEscaper;
    Escaper jsEscaper;

    protected TextField(String name, String description, DataTypes dataType) {
        this.name = name;
        this.description = description;
        this.dataType = dataType;
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    @Override
    public Locale getLocale() {
        return this.locale == null ? Locale.getDefault() : this.locale;
    }

    @Override
    public String getAlias() {
        return BaseFunction.isBlank(alias) ? getName() : alias.toLowerCase();
    }

    @Override
    public String getOrderByAlias() {
        return BaseFunction.isBlank(orderByAlias) ? getAlias() : orderByAlias.toLowerCase();
    }

    public T getValue() throws FrameworkException {
        return (T) getDataType().parseValue(data, getLocale(), getFormat());
    }

    @Override
    public void setValue(T value) throws FrameworkException {
        setData(getDataType().formatValue(value, getLocale(), getFormat()));
    }

    public boolean isHidden() {
        return hidden != null && hidden;
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            Object object = dataSource.getObject(getAlias());

            // Nota: Sarebbe più corretto rendere questo passaggio trasparente rispetto alla gestione dei LOB che dovrebbe essere demandata ai Bean
            if (object instanceof LobData) {
                object = ((LobData<?>) object).getValue();
            }

            try {
                setData(getDataType().formatValue(object, getLocale(), getFormat()));
            } catch (FrameworkException e) {
                throw new FrameworkException(ExceptionCode.FORMAT_ERROR_DETAIL, getName());
            }
        }
    }

    @Override
    public void copyToDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            dataSource.setObject(getAlias(), getValue());
        }
    }

    @Override
    public void copyToBffFields(BffFields bffFields) throws FrameworkException {
        if (bffFields != null) {
            bffFields.setString(getName(), getData());
        }
    }

    @Override
    public boolean isValid() {
        return check() == null;
    }

    @Override
    public Message check() {
        return Validator.verifyValidity(this);
    }

    @Override
    public boolean validate(MessageList messages) throws FrameworkException {
        return true;
    }

    @Override
    public void post(WebRequest webRequest) throws FrameworkException {
        // Il campo è presentato in sola visualizzazione e non è modificabile dal Browser
    }

    @Override
    public void post(BffFields bffFields) throws FrameworkException {
        // Il campo è presentato in sola visualizzazione e non è modificabile dal Browser
    }

}

package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.casting.Validator;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString(callSuper=true)
@SuperBuilder(toBuilder = true)
public abstract class InputField<T> extends TextField<T> {

    private Boolean required;
    private Boolean readOnly;
    private String placeHolder;

    private ViewModality viewModality;

    protected InputField(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }

    public boolean isRequired() {
        return required != null && required;
    }

    public boolean isReadOnly() {
        return readOnly != null && readOnly;
    }

    public ViewModality getViewModality() {
        return viewModality != null ? viewModality : ViewModality.AUTO;
    }

    @Override
    public Message check() {
        // Verifico la correttezza formale di quanto contenuto nella request
        Message message;
        if ((message = super.check()) != null) {
            return message;
        } else {
            return Validator.verifyMandatoriness(this);
        }
    }

    @Override
    public boolean validate(MessageList messageList) throws FrameworkException {
        Message message = check();
        if (message != null) {
            messageList.add(message);
            return false;
        } else {
            // Questo passaggio evita errori relativi a imputazioni formalmente correte
            // ma in formati diversi da quello previsto
            setData(getDataType().formatValue(getValue(), getLocale(), getFormat()));

            return true;
        }
    }

    private void post(String data) {
        if (!isReadOnly()) {
            setData(data);
        }
    }

    @Override
    public void post(BffFields bffFields) throws FrameworkException {
        post(bffFields.getString(getName()));
    }

    @Override
    public void post(WebRequest webRequest) throws FrameworkException {
        post(webRequest.getString(getName()));
    }

}

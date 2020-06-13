package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.BaseMessage;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
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
public abstract class InputField<T> extends TextField<T> {

    private Boolean required;
    private Boolean readOnly;
    private Boolean hidden;
    private ViewModality viewModality;

    public InputField(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }

    public boolean isRequired() {
        return required != null && required;
    }

    public boolean isReadOnly() {
        return readOnly != null && readOnly;
    }

    public boolean isHidden() {
        return hidden != null && hidden;
    }

    public ViewModality getViewModality() {
        return viewModality != null ? viewModality : ViewModality.VIEW_AUTO;
    }

    @Override
    public Message check() {
        // Verifico la correttezza formale di quanto contenuto nella request
        Message message = super.check();
        if (message != null)
            return message;

        // Controllo l'obbligatorietà
        if (BaseFunction.isNull(getValue()) && isRequired()) {
            return new BaseMessage(Level.WARN, "Il campo " + getDescription() + " è obbligatorio", null);
        }

        return message;
    }

    @Override
    public boolean validate(MessageList messageList) throws FrameworkException {
        Message message = check();
        if (message != null) {
            messageList.add(message);
            return false;
        }

        // Questo passaggio evita errori relativi a imputazioni formalmente correte
        // ma in formati diversi da quello previsto
        setData(getDataType().formatValue(getValue(), getLocale(), getFormat()));

        return true;
    }

    private void post(String data) {
        if (!isReadOnly()) {
            setData(data);
        }
    }

    @Override
    public void post(WebRequest webRequest) throws FrameworkException {
        post(webRequest.getString(getName()));
    }

}

package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.common.message.BaseMessage;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;

public abstract class InputField<T> extends TextField<T> {

    private boolean required;
    private boolean readOnly;
    private boolean hidden;
    private ViewModality viewModality;

    public InputField(String name, String description, String tooltip, DataTypes dataType) {
        this(name, name, description, tooltip, dataType, null);
    }

    public InputField(String name, String alias, String tooltip, String description, DataTypes dataType, String format) {
        this(name, alias, description, tooltip, dataType, format, null, false, false, false, ViewModality.VIEW_AUTO);
    }

    public InputField(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality) {
        super(name, alias, description, tooltip, dataType, format, baseLink);
        this.required = required == null ? false : required;
        this.readOnly = readOnly == null ? false : readOnly;
        this.hidden = hidden == null ? false : hidden;
        this.viewModality = viewModality;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public ViewModality getViewModality() {
        return viewModality;
    }

    public void setViewModality(ViewModality viewModality) {
        this.viewModality = viewModality;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public Message check() {
        // Verifico la correttezza formale di quanto contenuto nella request
        Message message = super.check();
        if (message != null)
            return message;

        // Controllo l'obbligatorietà
        if (BaseFunction.isNull(getValue()) && isRequired()) {
            return new BaseMessage(Level.WARN, "Il campo " + getDescription() + " à obbligatorio", null);
        }

        return message;
    }

    @Override
    public boolean validate(MessageList messageList) throws BusinessException {
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
    public void post(WebRequest webRequest) {
        try {
            post(webRequest.getString(getName()));
        } catch (Exception e) {
            throw new RuntimeException("Errore su postEscaped campo: " + getName(), e);
        }
    }

}

package it.eg.sloth.framework.common.casting;

import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.localization.Localization;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Validator {
    private Validator() {
        // NOP
    }

    public static Message verifyIfIsValid(String fieldName, String fieldDescription, String data, Locale locale, DataTypes dataTypes, String format) {
        Message message = null;
        try {
            dataTypes.parseValue(data, locale, format);

        } catch (FrameworkException e) {
            ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, locale);
            message = new Message(Level.WARN, fieldName, MessageFormat.format(bundle.getString(dataTypes.getErrorProperties()), fieldDescription), "");
        }

        return message;
    }

    public static Message verifyIfIsNotEmpty(String fieldName, String fieldDescription, Object data, Locale locale) {
        // Controllo l'obbligatorietà
        if (BaseFunction.isNull(data)) {
            ResourceBundle bundle = ResourceBundle.getBundle(Localization.VALUE_BUNDLE, locale);
            return new Message(Level.WARN, fieldName, MessageFormat.format(bundle.getString(Localization.ERR_MANDATORY), fieldDescription), "");
        } else {
            return null;
        }
    }

    /**
     * Verifica la validità formale del field passato
     *
     * @param dataField
     * @return
     */
    public static Message verifyValidity(DataField<?> dataField) {
        return verifyIfIsValid(dataField.getName(), dataField.getDescription(), dataField.getData(), dataField.getLocale(), dataField.getDataType(), dataField.getFormat());
    }


    /**
     * Verifica l'obbligatorietà del field passato
     *
     * @param dataField
     * @return
     */
    public static Message verifyMandatoriness(InputField<?> dataField) {
        if (dataField.isRequired()) {
            return verifyIfIsNotEmpty(dataField.getName(), dataField.getDescription(), dataField.getData(), dataField.getLocale());
        } else {
            return null;
        }
    }

}

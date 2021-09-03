package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.base.Elements;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

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
 *
 * @author Enrico Grillini
 */
public class TextControlWriter extends HtmlWriter {

    public static String writeControl(SimpleField simpleField, Elements<?> parentElement) throws FrameworkException {
        switch (simpleField.getFieldType()) {
            case AUTO_COMPLETE:
                return writeDecoDecodedDataField((AutoComplete<?>) simpleField, parentElement);
            case BUTTON:
                return FormControlWriter.writeButton((Button) simpleField);
            case CHECK_BOX:
                return writeCheckBox((CheckBox) simpleField);
            case COMBO_BOX:
                return writeDecoDecodedDataField((ComboBox<?>) simpleField, parentElement);
            case DECODED_TEXT:
                return writeDecoDecodedDataField((DecodedText<?>) simpleField, parentElement);
            case FILE:
                return StringUtil.EMPTY;
            case HIDDEN:
                return FormControlWriter.writeHidden((Hidden<?>) simpleField);
            case INPUT:
                return writeDataField((Input<?>) simpleField, parentElement);
            case INPUT_TOTALIZER:
                return writeDataField((InputTotalizer) simpleField, parentElement);
            case SEMAPHORE:
                return writeSemaphore((Semaphore) simpleField);
            case SWITCH:
                return writeSwitch((Switch) simpleField);
            case TEXT:
                return writeDataField((Text<?>) simpleField, parentElement);
            case TEXT_AREA:
                return writeTextArea((TextArea<?>) simpleField);
            case TEXT_TOTALIZER:
                return writeDataField((TextTotalizer) simpleField, parentElement);
            default:
                return "Implementare il controllo";
        }
    }

    /**
     * CheckBox
     *
     * @param checkBox
     * @return
     */
    private static String writeCheckBox(CheckBox<?> checkBox) {
        if (checkBox.isHidden())
            return StringUtil.EMPTY;

        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-checkbox\"><input")
                .append(getAttribute("type", "checkbox"))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CHECK_CLASS))
                .append(getAttribute("checked", checkBox.getValChecked().toString().equalsIgnoreCase(checkBox.getData()), ""))
                .append(getAttribute("disabled", ""))
                .append("/><span")
                .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                .append("></span></div>");

        return result.toString();
    }

    /**
     * Semaforo
     *
     * @param semaphore
     * @return
     */
    private static String writeSemaphore(Semaphore semaphore) throws FrameworkException {
        return semaphore.escapeHtmlDecodedText();
    }

    /**
     * TextArea
     *
     * @param textArea
     * @return
     */
    private static String writeTextArea(TextArea<?> textArea) {
        return textArea.escapeHtmlText();
    }

    private static String writeDataField(DataField<?> dataField, Elements<?> parentElement) {
        if (!BaseFunction.isBlank(dataField.getBaseLink())) {
            if (BaseFunction.isBlank(dataField.getLinkField())) {
                return getLink(dataField.getBaseLink() + dataField.escapeHtmlValue(), dataField.escapeHtmlText());
            } else {
                DataField<?> linkField = (DataField<?>) parentElement.getElement(dataField.getLinkField());
                return getLink(dataField.getBaseLink() + linkField.escapeHtmlValue(), dataField.escapeHtmlText());
            }
        } else if (DataTypes.URL == dataField.getDataType()) {
            return getLink(dataField.escapeHtmlValue(), dataField.escapeHtmlText(), true);
        } else {
            return dataField.escapeHtmlText();
        }
    }

    private static String writeDecoDecodedDataField(DecodedDataField<?> dataField, Elements<?> parentElement) throws FrameworkException {
        if (!BaseFunction.isBlank(dataField.getBaseLink())) {
            if (BaseFunction.isBlank(dataField.getLinkField())) {
                return getLink(dataField.getBaseLink() + dataField.escapeHtmlValue(), dataField.escapeHtmlDecodedText());
            } else {
                DataField<?> linkField = (DataField<?>) parentElement.getElement(dataField.getLinkField());
                return getLink(dataField.getBaseLink() + linkField.escapeHtmlValue(), dataField.escapeHtmlDecodedText());
            }
        } else if (DataTypes.URL == dataField.getDataType()) {
            return getLink(dataField.escapeHtmlValue(), dataField.escapeHtmlDecodedText(), true);
        } else {
            return dataField.escapeHtmlDecodedText();
        }
    }

    /**
     * Switch
     *
     * @param field
     * @return
     */
    private static String writeSwitch(Switch<?> field) {
        if (field.isHidden())
            return StringUtil.EMPTY;

        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-switch\"><input")
                .append(getAttribute("type", "checkbox"))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CHECK_CLASS))
                .append(getAttribute("checked", field.getValChecked().toString().equalsIgnoreCase(field.getData()), ""))
                .append(getAttribute("disabled", ""))
                .append("/><span")
                .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                .append("></span></div>");

        return result.toString();
    }


}

package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;

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
public class TextControlWriter extends AbstractHtmlWriter {

    public static String writeControl(SimpleField simpleField) {
        switch (simpleField.getFieldType()) {
            case BUTTON:
                return writeButton((Button) simpleField);
            case CHECK_BOX:
                return writeCheckBox((CheckBox<?>) simpleField);
            case COMBO_BOX:
                return writeComboBox((ComboBox<?>) simpleField);
            case DECODED_TEXT:
                return writeDecodedText((DecodedText<?>) simpleField);
            case FILE:
                return StringUtil.EMPTY;
            case HIDDEN:
                return writeHidden((Hidden<?>) simpleField);
            case INPUT:
                return writeInput((Input<?>) simpleField);
            case INPUT_TOTALIZER:
                return writeInputTotalizer((InputTotalizer) simpleField);
            case SEMAPHORE:
                return writeSemaphore((Semaphore) simpleField);
            case TEXT:
                return writeText((Text<?>) simpleField);
            case TEXT_AREA:
                return writeTextArea((TextArea<?>) simpleField);
            case TEXT_TOTALIZER:
                return writeTextTotalizer((TextTotalizer) simpleField);
            default:
                return "Implementare il controllo";
        }
    }

    /**
     * CheckBox
     *
     * @param button
     * @return
     */
    public static String writeButton(Button button) {
        return FormControlWriter.writeButton(button, null, null);
    }

    /**
     * CheckBox
     *
     * @param checkBox
     * @return
     */
    public static String writeCheckBox(CheckBox<?> checkBox) {
        if (checkBox.isHidden())
            return StringUtil.EMPTY;

        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-checkbox\"><input")
                .append(getAttribute("type", "checkbox"))
                .append(getAttribute("class", BootStrapClass.CHECK_CLASS))
                .append(getAttribute("checked", checkBox.getValChecked().toString().equalsIgnoreCase(checkBox.getData()), ""))
                .append(getAttribute("disabled", ""))
                .append("/><span")
                .append(getAttribute("class", "custom-control-label"))
                .append("></span></div>");

        return result.toString();
    }

    /**
     * ComboBox
     *
     * @param comboBox
     * @return
     */
    public static String writeComboBox(ComboBox<?> comboBox) {
        if (!BaseFunction.isBlank(comboBox.getBaseLink())) {
            return "<a href=\"" + comboBox.getBaseLink() + comboBox.escapeHtmlValue() + "\" >" + comboBox.getHtmlDecodedText() + "</a>";
        } else if (DataTypes.URL == comboBox.getDataType()) {
            return "<a href=\"" + comboBox.escapeHtmlValue() + "\" target=\"_blank\">" + comboBox.getHtmlDecodedText() + "</a>";
        } else {
            return comboBox.getHtmlDecodedText();
        }
    }

    /**
     * DecodedText
     *
     * @param decodedText
     * @return
     */
    public static String writeDecodedText(DecodedText<?> decodedText) {
        if (!BaseFunction.isBlank(decodedText.getBaseLink())) {
            return "<a href=\"" + decodedText.getBaseLink() + decodedText.escapeHtmlValue() + "\" >" + decodedText.getHtmlDecodedText() + "</a>";
        } else if (DataTypes.URL == decodedText.getDataType()) {
            return "<a href=\"" + decodedText.escapeHtmlValue() + "\" target=\"_blank\">" + decodedText.getHtmlDecodedText() + "</a>";
        } else {
            return decodedText.getHtmlDecodedText();
        }
    }

    /**
     * Hidden
     *
     * @param hidden
     * @return
     */
    public static String writeHidden(Hidden<?> hidden) {
        return FormControlWriter.writeHidden(hidden);
    }

    /**
     * Input
     *
     * @param input
     * @return
     */
    public static String writeInput(Input<?> input) {
        if (!BaseFunction.isBlank(input.getBaseLink())) {
            return "<a href=\"" + input.getBaseLink() + input.escapeHtmlValue() + "\" >" + input.escapeHtmlText() + "</a>";
        } else if (DataTypes.URL == input.getDataType()) {
            return "<a href=\"" + input.escapeHtmlValue() + "\" target=\"_blank\">" + input.escapeHtmlText() + "</a>";
        } else {
            return input.escapeHtmlText();
        }
    }

    /**
     * InputTotalizer
     *
     * @param inputTotalizer
     * @return
     */
    public static String writeInputTotalizer(InputTotalizer inputTotalizer) {
        return writeInput(inputTotalizer);
    }

    /**
     * Semaforo
     *
     * @param semaphore
     * @return
     */
    public static String writeSemaphore(Semaphore semaphore) {
        return semaphore.getHtmlDecodedText();
    }

    /**
     * Text
     *
     * @param text
     * @return
     */
    public static String writeText(Text<?> text) {
        if (!BaseFunction.isBlank(text.getBaseLink())) {
            return "<a href=\"" + text.getBaseLink() + text.escapeHtmlValue() + "\" >" + text.escapeHtmlText() + "</a>";
        } else if (DataTypes.URL == text.getDataType()) {
            return "<a href=\"" + text.escapeHtmlValue() + "\" target=\"_blank\">" + text.escapeHtmlText() + "</a>";
        } else {
            return text.escapeHtmlText();
        }
    }

    /**
     * TextArea
     *
     * @param textArea
     * @return
     */
    public static String writeTextArea(TextArea<?> textArea) {
        return textArea.escapeHtmlText();
    }

    /**
     * TextTotalizer
     *
     * @param textTotalizer
     * @return
     */
    public static String writeTextTotalizer(TextTotalizer textTotalizer) {
        return writeText(textTotalizer);
    }

}

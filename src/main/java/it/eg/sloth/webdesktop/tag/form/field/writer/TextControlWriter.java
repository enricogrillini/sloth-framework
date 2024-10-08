package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.base.Elements;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.md.MdUtil;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
public class TextControlWriter extends HtmlWriter {

    public static String writeControlSpace(SimpleField simpleField, Elements<?> parentElement) throws FrameworkException {
        String result = writeControl(simpleField, parentElement);
        if (BaseFunction.isBlank(result)) {
            return "&nbsp;";
        }

        return result;
    }

    public static String writeMd(SimpleField simpleField) {
        if (simpleField instanceof DataField) {
            DataField<?> field = (DataField<?>) simpleField;

            return MdUtil.getHtml(field.getData());
        }

        return StringUtil.EMPTY;
    }

    public static String writeControl(SimpleField simpleField, Elements<?> parentElement) throws FrameworkException {
        switch (simpleField.getFieldType()) {
            case AUTO_COMPLETE:
                return writeAutoComplete((AutoComplete<?>) simpleField, parentElement);
            case BUTTON:
                return FormControlWriter.writeButton((Button) simpleField);
            case CHECK_BOX:
                return writeCheckBox((CheckBox) simpleField);
            case COMBO_BOX:
                return writeComboBox((ComboBox<?>) simpleField, parentElement);
            case DECODED_TEXT:
                return writeDecodedText((DecodedText<?>) simpleField, parentElement);
            case FILE:
                return StringUtil.EMPTY;
            case HIDDEN:
                return FormControlWriter.writeHidden((Hidden<?>) simpleField);
            case INPUT:
                return writeInput((Input<?>) simpleField, parentElement);
            case INPUT_TOTALIZER:
                return writeInputTotalizer((InputTotalizer) simpleField, parentElement);
            case MULTIPLE_AUTO_COMPLETE:
                return writeMultipleAutoComplete((MultipleAutoComplete<?>) simpleField, parentElement);
            case SEMAPHORE:
                return writeSemaphore((Semaphore) simpleField);
            case SWITCH:
                return writeSwitch((Switch) simpleField);
            case TEXT:
                return writeText((Text<?>) simpleField, parentElement);
            case TEXT_AREA:
                return writeTextArea((TextArea<?>) simpleField);
            case TEXT_TOTALIZER:
                return writeDataField((TextTotalizer) simpleField, parentElement);
            default:
                return "Implementare il controllo";
        }
    }

    // AutoComplete
    public static String writeAutoComplete(AutoComplete<?> dataField, Elements<?> parentElement) throws FrameworkException {
        return writeDecoDecodedDataField(dataField, parentElement);
    }

    // CheckBox
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

    // ComboBox
    public static String writeComboBox(ComboBox<?> dataField, Elements<?> parentElement) throws FrameworkException {
        return writeDecoDecodedDataField(dataField, parentElement);
    }

    // DecodedText
    public static String writeDecodedText(DecodedText<?> dataField, Elements<?> parentElement) throws FrameworkException {
        return writeDecoDecodedDataField(dataField, parentElement);
    }

    // Input
    public static String writeInput(Input<?> dataField, Elements<?> parentElement) throws FrameworkException {
        return writeDataField(dataField, parentElement);
    }

    // Semaforo
    private static String writeSemaphore(Semaphore semaphore) throws FrameworkException {
        return semaphore.escapeHtmlDecodedText();
    }

    // Text
    public static String writeText(Text<?> dataField, Elements<?> parentElement) throws FrameworkException {
        return writeDataField(dataField, parentElement);
    }

    // Totalizer
    public static String writeInputTotalizer(InputTotalizer dataField, Elements<?> parentElement) throws FrameworkException {
        return writeDataField(dataField, parentElement);
    }

    public static <T> String writeMultipleAutoComplete(MultipleAutoComplete<T> multipleAutoComplete, Elements<?> parentElement) throws FrameworkException {
        StringBuilder builder = new StringBuilder();
        List<T> values = multipleAutoComplete.getValue();
        if (values != null) {
            for (T value : values) {
                String decodedText = multipleAutoComplete.getDecodeMap().decode(value);
                String htmlDecodedText;
                if (multipleAutoComplete.getHtmlEscaper() == null) {
                    htmlDecodedText = Casting.getHtml(decodedText);
                } else {
                    htmlDecodedText = multipleAutoComplete.getHtmlEscaper().escapeText(decodedText);
                }

                if (!BaseFunction.isBlank(multipleAutoComplete.getBaseLink())) {
                    if (BaseFunction.isBlank(multipleAutoComplete.getLinkField())) {
                        htmlDecodedText = getLink(multipleAutoComplete.getBaseLink() + Casting.getHtml(multipleAutoComplete.getDataType().formatValue(value, multipleAutoComplete.getLocale())), htmlDecodedText);
                    } else {
                        DataField<?> linkField = (DataField<?>) parentElement.getElement(multipleAutoComplete.getLinkField());
                        htmlDecodedText = getLink(multipleAutoComplete.getBaseLink() + linkField.escapeHtmlValue(), htmlDecodedText, true);
                    }
                }

                if (builder.length() > 0) {
                    builder.append(" <i class=\"fas fa-ellipsis-v\"></i> ");
                }

                if (multipleAutoComplete.getDecodeMap() != null && multipleAutoComplete.getDecodeMap().get(value) != null && !multipleAutoComplete.getDecodeMap().get(value).isValid()) {
                    builder.append("<i>" + htmlDecodedText + "</i>");
                } else {
                    builder.append(htmlDecodedText);
                }
            }
        }

        return builder.toString();
    }

    // Switch
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

    // TextArea
    private static String writeTextArea(TextArea<?> textArea) {
        return textArea.escapeHtmlText();
    }

    private static String writeDataField(DataField<?> dataField, Elements<?> parentElement) throws FrameworkException {
        if (!BaseFunction.isBlank(dataField.getBaseLink()) || !BaseFunction.isBlank(dataField.getLinkField())) {
            DataField<?> linkField = dataField;
            if (!BaseFunction.isBlank(dataField.getLinkField())) {
                linkField = (DataField<?>) parentElement.getElement(dataField.getLinkField());
            }

            if (dataField.getValue() != null) {
                return getLink(BaseFunction.nvl(dataField.getBaseLink(), StringUtil.EMPTY) + linkField.escapeHtmlValue(), dataField.escapeHtmlText());
            } else {
                return StringUtil.EMPTY;
            }

        } else if (DataTypes.URL == dataField.getDataType()) {
            if (dataField.getValue() != null) {
                return getLink(dataField.escapeHtmlValue(), dataField.escapeHtmlText(), true);
            } else {
                return StringUtil.EMPTY;
            }
        } else {
            return dataField.escapeHtmlText();
        }
    }

    private static String writeDecoDecodedDataField(DecodedDataField<?> dataField, Elements<?> parentElement) throws FrameworkException {
        if (!BaseFunction.isBlank(dataField.getBaseLink())) {
            DataField<?> linkField = dataField;
            if (!BaseFunction.isBlank(dataField.getLinkField())) {
                linkField = (DataField<?>) parentElement.getElement(dataField.getLinkField());
            }

            if (dataField.getValue() != null) {
                return getLink(dataField.getBaseLink() + linkField.escapeHtmlValue(), dataField.escapeHtmlDecodedText());
            } else {
                return StringUtil.EMPTY;
            }

        } else if (DataTypes.URL == dataField.getDataType()) {
            if (dataField.getValue() != null) {
                return getLink(dataField.escapeHtmlValue(), dataField.escapeHtmlDecodedText(), true);
            } else {
                return StringUtil.EMPTY;
            }

        } else {
            return dataField.escapeHtmlDecodedText();
        }
    }

}

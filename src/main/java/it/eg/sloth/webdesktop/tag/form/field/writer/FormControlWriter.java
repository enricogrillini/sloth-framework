package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.*;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
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
public class FormControlWriter extends HtmlWriter {

    public static String writeControl(SimpleField element, Element parentElement, ViewModality pageViewModality) throws FrameworkException {
        switch (element.getFieldType()) {
            case AUTO_COMPLETE:
                return writeAutoComplete((AutoComplete<?>) element, parentElement, pageViewModality);
            case BUTTON:
                return writeButton((Button) element);
            case CHECK_BOX:
                return writeCheckBox((CheckBox<?>) element, pageViewModality);
            case COMBO_BOX:
                return writeComboBox((ComboBox<?>) element, pageViewModality);
            case DECODED_TEXT:
                return writeDecodedText((DecodedText<?>) element);
            case FILE:
                return writeFile((File) element, pageViewModality);
            case HIDDEN:
                return writeHidden((Hidden<?>) element);
            case INPUT:
                return writeInput((Input<?>) element, pageViewModality);
            case INPUT_TOTALIZER:
                return writeInputTotalizer((InputTotalizer) element, pageViewModality);
            case LINK:
                return writeLink((Link) element);
            case RADIO_GROUP:
                return writeRadioGroup((RadioGroup<?>) element, pageViewModality);
            case SEMAPHORE:
                return writeSemaforo((Semaphore) element, pageViewModality);
            case TEXT:
                return writeText((Text<?>) element);
            case TEXT_AREA:
                return writeTextArea((TextArea<?>) element, pageViewModality);
            case TEXT_TOTALIZER:
                return writeTextTotalizer((TextTotalizer) element);
            default:
                throw new FrameworkException(ExceptionCode.INVALID_CONTROL);
        }

    }

    public static boolean hasLabel(SimpleField element) {
        return !(element instanceof Button);
    }

    public static String writeLabel(SimpleField simpleField) {
        if (!hasLabel(simpleField)) {
            return StringUtil.EMPTY;
        }

        String strRequired = simpleField instanceof InputField && ((InputField<?>) simpleField).isRequired() ? "*" : "";

        return new StringBuilder()
                .append("<label")
                .append(getAttribute("for", simpleField.getName()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.LABEL_CLASS))
                .append(getAttributeTooltip(simpleField.getTooltip()))
                .append(">" + simpleField.getHtmlDescription() + strRequired + ":&nbsp;</label>")
                .toString();
    }

    /**
     * Scrive un campo: Text
     *
     * @param text
     * @return
     */

    public static String writeText(Text<?> text) {
        StringBuilder result = new StringBuilder()
                .append(BEGIN_INPUT)
                .append(getAttribute(ATTR_ID, text.getName()))
                .append(getAttribute(ATTR_NAME, text.getName()))
                .append(getAttribute(ATTR_TYPE, "text"))
                .append(getAttribute(ATTR_VALUE, text.escapeHtmlText()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS))
                .append(getAttribute(ATTR_DISABLED, ""))
                .append(getAttributeTooltip(text.getTooltip()))
                .append("/>");

        return result.toString();
    }

    /**
     * Scrive un campo: TextTotalizer
     *
     * @param textTotalizer
     * @return
     */

    public static String writeTextTotalizer(TextTotalizer textTotalizer) {
        return writeText(textTotalizer);
    }

    /**
     * Scrive un campo: DecodedText
     *
     * @param decodedText
     * @return
     */
    public static String writeDecodedText(DecodedText<?> decodedText) {
        StringBuilder input = new StringBuilder()
                .append(BEGIN_INPUT)
                .append(getAttribute(ATTR_ID, decodedText.getName()))
                .append(getAttribute(ATTR_NAME, decodedText.getName()))
                .append(getAttribute(ATTR_VALUE, decodedText.escapeHtmlDecodedText()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS))
                .append(getAttribute(ATTR_DISABLED, ""))
                .append("/>");

        // Gestione link
        if (!BaseFunction.isBlank(decodedText.getBaseLink())) {
            StringBuilder result = new StringBuilder()
                    .append("<div class=\"input-group input-group-sm\">")
                    .append(input)
                    .append("<div class=\"input-group-append\">")
                    .append("<a href=\"" + decodedText.getBaseLink() + decodedText.escapeHtmlValue() + "\" class=\"btn btn-outline-secondary\"><i class=\"fas fa-link\"></i></a>")
                    .append("</div>")
                    .append("</div>");

            return result.toString();
        } else {
            return input.toString();
        }
    }

    /**
     * Scrive un campo: Hidden
     *
     * @param input
     * @return
     */
    public static String writeHidden(Hidden<?> input) {
        return new StringBuilder()
                .append(BEGIN_INPUT)
                .append(getAttribute(ATTR_ID, input.getName()))
                .append(getAttribute(ATTR_NAME, input.getName()))
                .append(getAttribute(ATTR_TYPE, "hidden"))
                .append(getAttribute(ATTR_VALUE, input.escapeHtmlText()))
                .append("/>")
                .toString();
    }


    /**
     * Scrive un campo: Input
     *
     * @param input
     * @param pageViewModality
     * @return
     */

    public static String writeInput(Input<?> input, ViewModality pageViewModality) {
        if (input.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = input.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : input.getViewModality();

        StringBuilder field = new StringBuilder()
                .append(BEGIN_INPUT)
                .append(getAttribute(ATTR_ID, input.getName()))
                .append(getAttribute(ATTR_NAME, input.getName()));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            field
                    .append(getAttribute(ATTR_TYPE, "text"))
                    .append(getAttribute(ATTR_VALUE, input.escapeHtmlText()))
                    .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS))
                    .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            field
                    .append(getAttribute(ATTR_TYPE, input.getDataType().getHtmlType()))
                    .append(getAttribute(ATTR_VALUE, input.escapeHtmlValue()))
                    .append(getAttribute("step", DataTypes.DATETIME == input.getDataType() || DataTypes.TIME == input.getDataType(), "1"))
                    .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS))
                    .append(getAttribute(ATTR_READONLY, input.isReadOnly(), ""))
                    .append(getAttribute("maxlength", input.getMaxLength() > 0, "" + input.getMaxLength()));
        }

        field
                .append(getAttributeTooltip(input.getTooltip()))
                .append("/>");


        // Gestione link
        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE && !BaseFunction.isBlank(input.getBaseLink())) {
            StringBuilder result = new StringBuilder()
                    .append("<div class=\"input-group input-group-sm\">")
                    .append(field)
                    .append("<div class=\"input-group-append\">")
                    .append("<a href=\"" + input.getBaseLink() + input.escapeHtmlValue() + "\" class=\"btn btn-outline-secondary\"><i class=\"fas fa-link\"></i></a>")
                    .append("</div>")
                    .append("</div>");

            return result.toString();
        } else {
            return field.toString();
        }
    }

    /**
     * Scrive un campo: InputTotalizer
     *
     * @param inputTotalizer
     * @param pageViewModality
     * @return
     */
    public static String writeInputTotalizer(InputTotalizer inputTotalizer, ViewModality pageViewModality) {
        return writeInput(inputTotalizer, pageViewModality);
    }

    /**
     * Scrive un campo: AutoComplete
     *
     * @param autocomplete
     * @param parentElement
     * @param pageViewModality
     * @return
     */
    public static String writeAutoComplete(AutoComplete<?> autocomplete, Element parentElement, ViewModality pageViewModality) {
        if (autocomplete.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = autocomplete.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : autocomplete.getViewModality();

        StringBuilder input = new StringBuilder()
                .append(BEGIN_INPUT)
                .append(getAttribute(ATTR_ID, autocomplete.getName()))
                .append(getAttribute(ATTR_NAME, autocomplete.getName()))
                .append(getAttribute(ATTR_VALUE, autocomplete.escapeHtmlDecodedText()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS + " autoComplete"));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            input.append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            input
                    .append(getAttribute("fields", parentElement.getName()))
                    .append(getAttribute(ATTR_READONLY, autocomplete.isReadOnly(), ""));
        }

        input.append("/>");

        // Gestione link
        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE && !BaseFunction.isBlank(autocomplete.getBaseLink())) {
            StringBuilder result = new StringBuilder()
                    .append("<div class=\"input-group input-group-sm\">")
                    .append(input)
                    .append("<div class=\"input-group-append\">")
                    .append("<a href=\"" + autocomplete.getBaseLink() + autocomplete.escapeHtmlValue() + "\" class=\"btn btn-outline-secondary\"><i class=\"fas fa-link\"></i></a>")
                    .append("</div>")
                    .append("</div>");

            return result.toString();
        } else {
            return input.toString();
        }
    }

    /**
     * Scrive un campo: TextArea
     *
     * @param textArea
     * @param pageViewModality
     * @return
     */
    public static String writeTextArea(TextArea<?> textArea, ViewModality pageViewModality) {
        if (textArea.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = textArea.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : textArea.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<textarea")
                .append(getAttribute(ATTR_ID, textArea.getName()))
                .append(getAttribute(ATTR_NAME, textArea.getName()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result.append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            result
                    .append(getAttribute(ATTR_READONLY, textArea.isReadOnly(), ""))
                    .append(getAttribute("maxlength", textArea.getMaxLength() > 0, "" + textArea.getMaxLength()));

        }
        result.append(">" + textArea.escapeHtmlValue() + "</textarea>");

        return result.toString();
    }

    /**
     * Scrive un campo: ComboBox
     *
     * @param comboBox
     * @param pageViewModality
     * @return
     */
    public static String writeComboBox(ComboBox<?> comboBox, ViewModality pageViewModality) throws FrameworkException {
        if (comboBox.isHidden())
            return StringUtil.EMPTY;

        ViewModality viewModality = comboBox.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : comboBox.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<select")
                .append(getAttribute(ATTR_ID, comboBox.getName()))
                .append(getAttribute(ATTR_NAME, comboBox.getName()))
                .append(getAttribute(ATTR_VALUE, comboBox.escapeHtmlValue()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result.append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            result.append(getAttribute(ATTR_READONLY, comboBox.isReadOnly(), ""));
        }

        result.append(">");

        if (!comboBox.isRequired()) {
            result.append("<option value=\"\"></option>");
        }

        DecodeMap<?, ?> values = comboBox.getDecodeMap();
        if (values != null) {
            for (DecodeValue<?> value : values) {
                if (BaseFunction.isNull(value.getCode())) {
                    continue;
                }

                String valueHtml = Casting.getHtml(comboBox.getDataType().formatValue(value.getCode(), comboBox.getLocale(), comboBox.getFormat()), false, false);
                String descriptionHtml = Casting.getHtml(value.getDescription());

                result.append("<option")
                        .append(getAttribute(ATTR_VALUE, valueHtml))
                        .append(getAttribute("selected", value.getCode().equals(comboBox.getValue()), "selected"))
                        .append(getAttribute(ATTR_CLASS, !value.isValid(), "notValid"))
                        .append(">" + descriptionHtml + "</option>");
            }
        }

        result.append("</select>");

        return result.toString();
    }

    /**
     * Scrive un campo: CheckBox
     *
     * @param checkBox
     * @param pageViewModality
     * @return
     */
    public static String writeCheckBox(CheckBox<?> checkBox, ViewModality pageViewModality) {
        if (checkBox.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = checkBox.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : checkBox.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-checkbox\"><input")
                .append(getAttribute(ATTR_ID, checkBox.getName()))
                .append(getAttribute(ATTR_NAME, checkBox.getName()))
                .append(getAttribute(ATTR_TYPE, "checkbox"))
                .append(getAttribute(ATTR_VALUE, checkBox.getValChecked().toString()))
                .append(getAttribute(ATTR_READONLY, checkBox.isReadOnly(), ""))
                .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CHECK_CLASS))
                .append(getAttribute("checked", checkBox.getValChecked().toString().equalsIgnoreCase(checkBox.getData()), ""))
                .append("/>");

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result.append("<div")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append("></div>");
        } else {
            result
                    .append("<label")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append(getAttribute("for", checkBox.getName()))
                    .append("></label>");
        }

        return result
                .append("</div>")
                .toString();
    }

    /**
     * Scrive un campo: RadioGroup
     *
     * @param radioGroup
     * @param pageViewModality
     * @return
     */
    public static String writeRadioGroup(RadioGroup<?> radioGroup, ViewModality pageViewModality) throws FrameworkException {
        if (radioGroup.isHidden()) {
            return StringUtil.EMPTY;
        }


        ViewModality viewModality = radioGroup.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : radioGroup.getViewModality();
        StringBuilder result = new StringBuilder();

        // Opzioni
        int i = 0;
        DecodeMap<?, ?> decodeMap = radioGroup.getDecodeMap();
        if (decodeMap != null) {
            for (DecodeValue<?> value : decodeMap) {
                String htmlValue = Casting.getHtml(radioGroup.getDataType().formatValue(value.getCode(), radioGroup.getLocale(), radioGroup.getFormat()));

                result
                        .append(" <div class=\"custom-control custom-radio custom-control-inline form-control-sm\">\n")
                        .append("  <input")
                        .append(getAttribute(ATTR_ID, radioGroup.getName() + i))
                        .append(getAttribute(ATTR_NAME, radioGroup.getName()))
                        .append(getAttribute(ATTR_TYPE, "radio"))
                        .append(getAttribute(ATTR_VALUE, htmlValue))
                        .append(getAttribute(ATTR_READONLY, radioGroup.isReadOnly(), ""))
                        .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""))
                        .append(getAttribute(ATTR_CHECKED, value.getCode() != null && value.getCode().equals(radioGroup.getValue()), ""))
                        .append(getAttribute(ATTR_CLASS, "custom-control-input"))
                        .append(">");

                if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
                    result.append("<div class=\"custom-control-label\">")
                            .append(Casting.getHtml(value.getDescription()))
                            .append("</div>\n");
                } else {
                    result.append("<label class=\"custom-control-label\"")
                            .append(getAttribute(ATTR_FOR, radioGroup.getName() + i))
                            .append(">")
                            .append(Casting.getHtml(value.getDescription()))
                            .append("</label>\n");
                }

                result.append(" </div>\n");

                i++;
            }
        }

        return result.toString();
    }

    /**
     * Scrive un campo: Semaforo
     *
     * @param semaforo
     * @param pageViewModality
     * @return
     */
    public static String writeSemaforo(Semaphore semaforo, ViewModality pageViewModality) {
        if (semaforo.isHidden())
            return "";

        ViewModality viewModality = semaforo.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : semaforo.getViewModality();
        if (viewModality == ViewModality.VIEW_MODIFICA) {
            // FIXME: Implementare
            return "";
        } else {
            return semaforo.escapeHtmlDecodedText();
        }
    }

    /**
     * Scrive un campo di input di tipo File
     *
     * @param file
     * @param pageViewModality
     * @return
     */
    public static String writeFile(File file, ViewModality pageViewModality) {
        if (file.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = file.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : file.getViewModality();

        StringBuilder result = new StringBuilder();
        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result
                    .append("<div class=\"input-group input-group-sm\"><a href=\"ClientiPage.html?idcliente=3\" class=\"form-control form-control-sm btn btn-outline-secondary\">Download</a></div>");
        } else {
            result
                    .append("<div class=\"custom-file small\"><input")
                    .append(getAttribute(ATTR_ID, file.getName()))
                    .append(getAttribute(ATTR_NAME, file.getName()))
                    .append(getAttribute(ATTR_TYPE, "file"))
                    .append(getAttributeTooltip(file.getTooltip()))
                    .append(" class=\"custom-file-input\"><label class=\"custom-file-label\"")
                    .append(getAttribute("for", file.getName()))
                    .append(">Choose file</label></div>");
        }

        return result.toString();

    }

    /**
     * Scrive un campo: Link
     *
     * @param link
     * @return
     */
    public static String writeLink(Link link) {
        if (link.isHidden()) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder()
                .append("<a")
                .append(getAttribute("href", !BaseFunction.isBlank(link.getHref()), link.getHref()))
                .append(getAttribute("target", !BaseFunction.isBlank(link.getTarget()), link.getTarget()))
                .append(getAttribute(ATTR_CLASS, MessageFormat.format(BootStrapClass.BUTTON_CLASS, link.getButtonType().value())))
                .append(getAttribute(ATTR_DISABLED, link.isDisabled(), ""))
                .append(getAttributeTooltip(link.getTooltip()))
                .append("/>")
                .append(BaseFunction.isBlank(link.getImgHtml()) ? "" : link.getImgHtml() + "&nbsp;&nbsp;")
                .append(Casting.getHtml(link.getDescription()))
                .append("</a>");

        return result.toString();
    }

    /**
     * Scrive un campo: Button
     *
     * @param button
     * @return
     */
    public static String writeButton(Button button) {
        if (button.isHidden()) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder()
                .append("<button")
                .append(getAttribute(ATTR_ID, button.getHtlmName()))
                .append(getAttribute(ATTR_NAME, button.getHtlmName()))
                .append(getAttribute(ATTR_CLASS, MessageFormat.format(BootStrapClass.BUTTON_CLASS, button.getButtonType().value())))
                .append(getAttribute(ATTR_DISABLED, button.isDisabled(), ""))
                .append(getAttributeTooltip(button.getTooltip()))
                .append("/>")
                .append(BaseFunction.isBlank(button.getImgHtml()) ? "" : button.getImgHtml() + "&nbsp;&nbsp;")
                .append(Casting.getHtml(button.getDescription()))
                .append("</button>");

        return result.toString();
    }
}

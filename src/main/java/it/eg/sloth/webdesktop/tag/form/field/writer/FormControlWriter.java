package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.form.base.Elements;
import it.eg.sloth.form.fields.field.SimpleField;
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

    public static String writeControl(SimpleField element, Elements<?> parentElement, ViewModality pageViewModality) throws FrameworkException {
        switch (element.getFieldType()) {
            case AUTO_COMPLETE:
                return writeAutoComplete((AutoComplete) element, parentElement, pageViewModality);
            case BUTTON:
                return writeButton((Button) element);
            case CHECK_BOX:
                return writeCheckBox((CheckBox) element, pageViewModality);
            case CHECK_BUTTONS:
                return writeCheckButtons((CheckButtons<?, Object>) element, pageViewModality);
            case CHECK_GROUP:
                return writeCheckGroups((CheckGroup<?, Object>) element, pageViewModality);
            case COMBO_BOX:
                return writeComboBox((ComboBox) element, pageViewModality);
            case DECODED_TEXT:
                return writeDecodedText((DecodedText) element);
            case FILE:
                return writeFile((File) element, pageViewModality);
            case HIDDEN:
                return writeHidden((Hidden) element);
            case INPUT:
                return writeInput((Input) element, pageViewModality);
            case INPUT_TOTALIZER:
                return writeInputTotalizer((InputTotalizer) element, pageViewModality);
            case LINK:
                return writeLink((Link) element);
            case RADIO_BUTTONS:
                return writeRadioButtons((RadioButtons) element, pageViewModality);
            case RADIO_GROUP:
                return writeRadioGroup((RadioGroup) element, pageViewModality);
            case SEMAPHORE:
                return writeSemaphore((Semaphore) element, pageViewModality);
            case SWITCH:
                return writeSwitch((Switch) element, pageViewModality);
            case TEXT:
                return writeText((Text) element);
            case TEXT_AREA:
                return writeTextArea((TextArea) element, pageViewModality);
            case TEXT_TOTALIZER:
                return writeTextTotalizer((TextTotalizer) element);
            default:
                throw new FrameworkException(ExceptionCode.INVALID_CONTROL);
        }

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
                .append(getAttribute(ATTR_VALUE, Casting.getHtml(autocomplete.getDecodedText(), true, true)))
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
                    .append(MessageFormat.format(LINK, autocomplete.getBaseLink() + autocomplete.escapeHtmlValue()))
                    .append("</div>");

            return result.toString();
        } else {
            return input.toString();
        }
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
                .append(getAttribute(ON_CLICK, !BaseFunction.isBlank(button.getConfirmMessage()), "buttonConfirm(this);"))
                .append(getAttribute(ATTR_DATA_TITLE, !BaseFunction.isBlank(button.getConfirmMessage()), "Conferma"))
                .append(getAttribute(ATTR_DATA_DESCRIPTION, !BaseFunction.isBlank(button.getConfirmMessage()), Casting.getHtml(button.getConfirmMessage())))
                .append(getAttributeTooltip(button.getTooltip()))
                .append(">")
                .append(BaseFunction.nvl(button.getImgHtml(), ""))
                .append(BaseFunction.isBlank(button.getImgHtml()) || BaseFunction.isBlank(button.getDescription()) ? "" : "&nbsp;")
                .append(Casting.getHtml(button.getDescription()))
                .append("</button>");

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
                .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                .append(getAttribute(ATTR_VALUE, checkBox.getValChecked().toString()))
                .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE || checkBox.isReadOnly(), ""))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CHECK_CLASS))
                .append(getAttribute(ATTR_CHECKED, checkBox.isChecked(), ""))
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
     * Scrive un campo: CheckButtons
     *
     * @param checkButtons
     * @param pageViewModality
     * @return
     * @throws FrameworkException
     */
    public static String writeCheckButtons(CheckButtons<?, Object> checkButtons, ViewModality pageViewModality) throws FrameworkException {
        if (checkButtons.isHidden()) {
            return StringUtil.EMPTY;
        }

        String[] values = checkButtons.getSplittedValues();
        String[] descriptions = checkButtons.getSplittedDescriptions();

        ViewModality viewModality = checkButtons.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : checkButtons.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\">");

        int i = 0;
        for (String value : values) {
            String htmlValue = Casting.getHtml(value);
            String description = descriptions.length > i ? descriptions[i] : StringUtil.EMPTY;
            boolean active = checkButtons.isChecked(checkButtons.getDataType().parseValue(value, checkButtons.getLocale(), checkButtons.getFormat()));

            if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
                result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-primary btn-sm disabled", "btn btn-outline-primary btn-sm disabled")))
                        .append(Casting.getHtml(description));
            } else {
                result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-outline-primary btn-sm active", "btn btn-outline-primary btn-sm ")))
                        .append("<input ")
                        .append(getAttribute(ATTR_ID, checkButtons.getName()))
                        .append(getAttribute(ATTR_NAME, checkButtons.getName()))
                        .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                        .append(getAttribute(ATTR_VALUE, htmlValue))
                        .append(getAttribute(ATTR_CHECKED, active, ""))
                        .append(">" + Casting.getHtml(description));
            }

            result.append("</label>");

            i++;
        }

        return result
                .append("</div>")
                .toString();
    }

    /**
     * Scrive un campo: CheckGroups
     *
     * @param checkGroup
     * @param pageViewModality
     * @return
     * @throws FrameworkException
     */
    public static String writeCheckGroups(CheckGroup<?, Object> checkGroup, ViewModality pageViewModality) throws FrameworkException {
        if (checkGroup.isHidden()) {
            return StringUtil.EMPTY;
        }

        String[] values = checkGroup.getSplittedValues();
        String[] descriptions = checkGroup.getSplittedDescriptions();

        ViewModality viewModality = checkGroup.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : checkGroup.getViewModality();
        StringBuilder result = new StringBuilder();

        int i = 0;
        for (String value : values) {
            String htmlValue = Casting.getHtml(value);
            String description = descriptions.length > i ? descriptions[i] : StringUtil.EMPTY;
            boolean active = checkGroup.isChecked(checkGroup.getDataType().parseValue(value, checkGroup.getLocale(), checkGroup.getFormat()));

            result
                    .append(" <div class=\"custom-control custom-checkbox custom-control-inline form-control-sm\">\n")
                    .append("  <input")
                    .append(getAttribute(ATTR_ID, checkGroup.getName() + i))
                    .append(getAttribute(ATTR_NAME, checkGroup.getName()))
                    .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                    .append(getAttribute(ATTR_VALUE, htmlValue))
                    .append(getAttribute(ATTR_READONLY, checkGroup.isReadOnly(), ""))
                    .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""))
                    .append(getAttribute(ATTR_CHECKED, active, ""))
                    .append(getAttribute(ATTR_CLASS, "custom-control-input"))
                    .append(">");

            if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
                result.append("<div class=\"custom-control-label\">")
                        .append(Casting.getHtml(description))
                        .append("</div>\n");
            } else {
                result.append("<label class=\"custom-control-label\"")
                        .append(getAttribute(ATTR_FOR, checkGroup.getName() + i))
                        .append(">")
                        .append(Casting.getHtml(description))
                        .append("</label>\n");
            }

            result.append(" </div>\n");

            i++;
        }

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
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CONTROL_CLASS))
                .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE || comboBox.isReadOnly(), ""));

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
     * Scrive un campo: DecodedText
     *
     * @param decodedText
     * @return
     */
    public static String writeDecodedText(DecodedText<?> decodedText) throws FrameworkException {
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
                    .append(MessageFormat.format(LINK, decodedText.getBaseLink() + decodedText.escapeHtmlValue()))
                    .append("</div>");

            return result.toString();
        } else {
            return input.toString();
        }
    }

    /**
     * Scrive un campo: File
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
                    .append(getAttribute("placeholder", DataTypes.MONTH == input.getDataType(), "yyyy-mm"))
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
                    .append(MessageFormat.format(LINK, input.getBaseLink() + input.escapeHtmlValue()))
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
                .append(BaseFunction.nvl(link.getImgHtml(), ""))
                .append(BaseFunction.isBlank(link.getImgHtml()) || BaseFunction.isBlank(link.getDescription()) ? "" : "&nbsp;")
                .append(Casting.getHtml(link.getDescription()))
                .append("</a>");

        return result.toString();
    }

    /**
     * Scrive un campo: RadioButtons
     *
     * @param radioButtons
     * @param pageViewModality
     * @return
     * @throws FrameworkException
     */
    public static String writeRadioButtons(RadioButtons<?> radioButtons, ViewModality pageViewModality) throws FrameworkException {
        if (radioButtons.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = radioButtons.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : radioButtons.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\">");

        // Opzioni
        DecodeMap<?, ?> decodeMap = radioButtons.getDecodeMap();
        if (decodeMap != null) {
            for (DecodeValue<?> value : decodeMap) {
                String htmlValue = Casting.getHtml(radioButtons.getDataType().formatValue(value.getCode(), radioButtons.getLocale(), radioButtons.getFormat()));
                boolean active = value.getCode() != null && value.getCode().equals(radioButtons.getValue());

                if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-primary btn-sm disabled", "btn btn-outline-primary btn-sm disabled")))
                            .append(Casting.getHtml(value.getDescription()));
                } else {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn btn-outline-primary btn-sm active", "btn btn-outline-primary btn-sm ")))
                            .append("<input ")
                            .append(getAttribute(ATTR_ID, radioButtons.getName()))
                            .append(getAttribute(ATTR_NAME, radioButtons.getName()))
                            .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_RADIO))
                            .append(getAttribute(ATTR_VALUE, htmlValue))
                            .append(getAttribute(ATTR_CHECKED, active, ""))
                            .append(">" + Casting.getHtml(value.getDescription()));
                }

                result.append("</label>");
            }
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
                        .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_RADIO))
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
     * @param semaphore
     * @param pageViewModality
     * @return
     */
    public static String writeSemaphore(Semaphore semaphore, ViewModality pageViewModality) throws FrameworkException {
        if (semaphore.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = semaphore.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : semaphore.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"btn-group btn-group-toggle d-flex\" data-toggle=\"buttons\">");

        // Opzioni
        DecodeMap<String, ?> decodeMap = semaphore.getDecodeMap();
        if (decodeMap != null) {
            for (DecodeValue<String> value : decodeMap) {
                String htmlValue = Casting.getHtml(semaphore.getDataType().formatValue(value.getCode(), semaphore.getLocale(), semaphore.getFormat()));
                boolean active = value.getCode() != null && value.getCode().equals(semaphore.getValue());

                String btnOutline;
                String btn;
                switch (value.getCode()) {
                    case Semaphore.GREEN:
                        btnOutline = "btn-outline-success";
                        btn = "btn-success";
                        break;
                    case Semaphore.YELLOW:
                        btnOutline = "btn-outline-warning";
                        btn = "btn-warning";
                        break;
                    case Semaphore.RED:
                        btnOutline = "btn-outline-danger";
                        btn = "btn-danger";
                        break;
                    case Semaphore.BLACK:
                        btnOutline = "btn-outline-dark";
                        btn = "btn-dark";
                        break;
                    default:
                        btnOutline = "btn-outline-secondary";
                        btn = "btn-secondary";
                }


                if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn " + btn + " btn-sm disabled", "btn " + btnOutline + " btn-sm disabled")))
                            .append("<i class=\"far fa-circle\"></i>");
                } else {
                    result.append(MessageFormat.format("<label{0}>", getAttribute(ATTR_CLASS, active, "btn " + btnOutline + " btn-sm active", "btn " + btnOutline + " btn-sm ")))
                            .append("<input ")
                            .append(getAttribute(ATTR_ID, semaphore.getName()))
                            .append(getAttribute(ATTR_NAME, semaphore.getName()))
                            .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_RADIO))
                            .append(getAttribute(ATTR_VALUE, htmlValue))
                            .append(getAttribute(ATTR_CHECKED, active, ""))
                            .append(">" + "<i class=\"far fa-circle\"></i>");
                }

                result.append("</label>");
            }
        }

        return result
                .append("</div>")
                .toString();
    }

    /**
     * Scrive un campo: Switch
     *
     * @param field
     * @param pageViewModality
     * @return
     */
    public static String writeSwitch(Switch<?> field, ViewModality pageViewModality) {
        if (field.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = field.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : field.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-switch\"><input")
                .append(getAttribute(ATTR_ID, field.getName()))
                .append(getAttribute(ATTR_NAME, field.getName()))
                .append(getAttribute(ATTR_TYPE, VAL_ATTR_TYPE_CHECKBOX))
                .append(getAttribute(ATTR_VALUE, field.getValChecked().toString()))
                .append(getAttribute(ATTR_DISABLED, viewModality == ViewModality.VIEW_VISUALIZZAZIONE || field.isReadOnly(), ""))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.CHECK_CLASS))
                .append(getAttribute(ATTR_CHECKED, field.isChecked(), ""))
                .append("/>");

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result.append("<div")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append("></div>");
        } else {
            result
                    .append("<label")
                    .append(getAttribute(ATTR_CLASS, "custom-control-label"))
                    .append(getAttribute("for", field.getName()))
                    .append("></label>");
        }

        return result
                .append("</div>")
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
     * Scrive un campo: TextTotalizer
     *
     * @param textTotalizer
     * @return
     */

    public static String writeTextTotalizer(TextTotalizer textTotalizer) {
        return writeText(textTotalizer);
    }
}

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
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
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
public class FormControlWriter extends AbstractHtmlWriter {

    public static String writeControl(SimpleField element, Element parentElement, String lastController, ViewModality pageViewModality, String className, String style) throws FrameworkException {
        switch (element.getFieldType()) {
            case AUTO_COMPLETE:
                return writeAutoComplete((AutoComplete<?>) element, parentElement, pageViewModality, className, style);
            case BUTTON:
                return writeButton((Button) element, className, style);
            case CHECK_BOX:
                return writeCheckBox((CheckBox<?>) element, pageViewModality, className, style);
            case COMBO_BOX:
                return writeComboBox((ComboBox<?>) element, pageViewModality, className, style);
            case DECODED_TEXT:
                return writeDecodedText((DecodedText<?>) element, className, style);
            case FILE:
                return writeFile((File) element, pageViewModality, className, style);
            case HIDDEN:
                return writeHidden((Hidden<?>) element);
            case INPUT:
                return writeInput((Input<?>) element, pageViewModality, className, style);
            case INPUT_TOTALIZER:
                return writeInputTotalizer((InputTotalizer) element, pageViewModality, className, style);
            case SEMAPHORE:
                return writeSemaforo((Semaphore) element, pageViewModality, className, style);
            case TEXT:
                return writeText((Text<?>) element, className, style);
            case TEXT_AREA:
                return writeTextArea((TextArea<?>) element, pageViewModality, className, style);
            case TEXT_TOTALIZER:
                return writeTextTotalizer((TextTotalizer) element, className, style);
            default:
                // Old -> Saranno da portare tutti a nuovo
                if (element instanceof RadioGroup<?>) {
                    return writeRadioGroup((RadioGroup<?>) element, pageViewModality, className, style);
                } else if (element instanceof MultipleAutoComplete<?, ?>) {
                    return writeMultipleAutoComplete((MultipleAutoComplete<?, ?>) element, parentElement, pageViewModality, className, style);
                } else {
                    return "";
                }
        }

    }

    public static boolean hasLabel(SimpleField element) {
        if (element instanceof Button) {
            return false;
        } else {
            return true;
        }
    }

    public static String writeLabel(SimpleField simpleField, ViewModality pageViewModality, String className, String style) {
        if (!hasLabel(simpleField)) {
            return "";
        }

        String classHtml = " class=\"" + BootStrapClass.LABEL_CLASS + "\"";
        if (!BaseFunction.isBlank(className)) {
            classHtml = " class=\"" + className + "\"";
        }

        String strStyle = BaseFunction.isBlank(style) ? "" : " style=\"" + style + "\" ";
        String strTooltip = BaseFunction.isBlank(simpleField.getTooltip()) ? "" : " title=\"" + simpleField.getHtmlTooltip() + "\" ";
        String strRequired = simpleField instanceof InputField && ((InputField<?>) simpleField).isRequired() ? "*" : "";

        String result = "";
        result += "<label for=\"" + simpleField.getName() + "\" " + classHtml + strStyle + strTooltip + ">" + simpleField.getHtmlDescription() + strRequired + ":&nbsp;</label>";

        return result;
    }

    /**
     * Scrive un campo: Text
     *
     * @param text
     * @param className
     * @param style
     * @return
     */

    public static String writeText(Text<?> text, String className, String style) {
        StringBuilder result = new StringBuilder()
                .append("<input")
                .append(getAttribute("id", text.getName()))
                .append(getAttribute("name", text.getName()))
                .append(getAttribute("type", "text"))
                .append(getAttribute("value", text.escapeHtmlText()))
                .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style))
                .append(getAttribute("disabled", ""))
                .append(getAttribute("data-toggle=\"tooltip\" data-placement=\"bottom\" title", !BaseFunction.isBlank(text.getTooltip()), text.getTooltip()))
                .append("/>");

        return result.toString();
    }

    /**
     * Scrive un campo: TextTotalizer
     *
     * @param textTotalizer
     * @param className
     * @param style
     * @return
     */

    public static String writeTextTotalizer(TextTotalizer textTotalizer, String className, String style) {
        return writeText(textTotalizer, className, style);
    }

    /**
     * Scrive un campo: DecodedText
     *
     * @param decodedText
     * @param className
     * @param style
     * @return
     */
    public static String writeDecodedText(DecodedText<?> decodedText, String className, String style) {
        StringBuilder input = new StringBuilder()
                .append("<input")
                .append(getAttribute("id", decodedText.getName()))
                .append(getAttribute("name", decodedText.getName()))
                .append(getAttribute("value", decodedText.getHtmlDecodedText()))
                .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style))
                .append(getAttribute("disabled", ""))
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
                .append("<input")
                .append(getAttribute("id", input.getName()))
                .append(getAttribute("name", input.getName()))
                .append(getAttribute("type", "hidden"))
                .append(getAttribute("value", input.escapeHtmlText()))
                .append("/>")
                .toString();
    }


    /**
     * Scrive un campo: Input
     *
     * @param input
     * @param pageViewModality
     * @param className
     * @param style
     * @return
     */

    public static String writeInput(Input<?> input, ViewModality pageViewModality, String className, String style) {
        if (input.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = input.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : input.getViewModality();

        StringBuilder field = new StringBuilder()
                .append("<input")
                .append(getAttribute("id", input.getName()))
                .append(getAttribute("name", input.getName()));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            field
                    .append(getAttribute("type", "text"))
                    .append(getAttribute("value", input.escapeHtmlText()))
                    .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
                    .append(getAttribute("style", !BaseFunction.isBlank(style), style))
                    .append(getAttribute("disabled", viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            field
                    .append(getAttribute("type", input.getDataType().getHtmlType()))
                    .append(getAttribute("value", input.escapeHtmlValue()))
                    .append(getAttribute("step", DataTypes.DATETIME == input.getDataType() || DataTypes.TIME == input.getDataType(), "1"))
                    .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
                    .append(getAttribute("style", !BaseFunction.isBlank(style), style))
                    .append(getAttribute("readonly", input.isReadOnly(), ""))
                    .append(getAttribute("maxlength", input.getMaxLength() > 0, "" + input.getMaxLength()));
        }

        field
                .append(getAttribute("data-toggle=\"tooltip\" data-placement=\"bottom\" title", !BaseFunction.isBlank(input.getTooltip()), input.getTooltip()))
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
     * @param className
     * @param style
     * @return
     */
    public static String writeInputTotalizer(InputTotalizer inputTotalizer, ViewModality pageViewModality, String className, String style) {
        return writeInput(inputTotalizer, pageViewModality, className, style);
    }

    /**
     * Scrive un campo: Input
     *
     * @param autocomplete
     * @return
     */
    public static String writeAutoComplete(AutoComplete<?> autocomplete, Element parentElement, ViewModality pageViewModality, String className, String style) {
        if (autocomplete.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = autocomplete.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : autocomplete.getViewModality();

        StringBuilder input = new StringBuilder()
                .append("<input")
                .append(getAttribute("id", autocomplete.getName()))
                .append(getAttribute("name", autocomplete.getName()))
                .append(getAttribute("value", autocomplete.getHtmlDecodedText()))
                .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS + " autoComplete"))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            input.append(getAttribute("disabled", viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            input
                    .append(getAttribute("fields", parentElement.getName()))
                    .append(getAttribute("readonly", autocomplete.isReadOnly(), ""));
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
     * Scrive un campo: MultipleAutoComplete
     *
     * @param multipleAutoComplete
     * @param pageViewModality
     * @param className
     * @param style
     * @return
     */
    public static String writeMultipleAutoComplete(MultipleAutoComplete<?, ?> multipleAutoComplete, Element parentElement, ViewModality pageViewModality, String className, String style) {
        if (multipleAutoComplete.isHidden())
            return "";

        String result;
        ViewModality viewModality = multipleAutoComplete.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : multipleAutoComplete.getViewModality();
        if (viewModality == ViewModality.VIEW_MODIFICA) {
            result = "<input ";

            result += " id=\"" + multipleAutoComplete.getName() + "\"";
            result += " name=\"" + multipleAutoComplete.getName() + "\"";
            result += " value=\"" + multipleAutoComplete.getHtmlDecodedText(false, false) + "\"";
            result += " type=\"text\"";

            result += " fields=\"" + parentElement.getName() + "\"";

            if (multipleAutoComplete.isReadOnly()) {
                result += " disabled=\"disabled\"";
            }

            result += " class=\"" + (BaseFunction.isBlank(className) ? "multipleAutoComplete" : className + " multipleAutoComplete") + "\"";

            if (!BaseFunction.isBlank(style))
                result += " style=\"" + style + "\"";

            result += " />";

        } else if (!BaseFunction.isBlank(multipleAutoComplete.getBaseLink())) {
            result = "<span><a href=\"" + multipleAutoComplete.getBaseLink() + multipleAutoComplete.escapeHtmlValue() + "\" >" + multipleAutoComplete.getHtmlDecodedText() + "</a></span>";
        } else {
            result = "<span>" + BaseFunction.nvl(multipleAutoComplete.getHtmlDecodedText(), "&nbsp;") + "</span>";
        }

        return result;
    }

    /**
     * Scrive un campo: TextArea
     *
     * @param textArea
     * @param pageViewModality
     * @param className
     * @param style
     * @return
     */
    public static String writeTextArea(TextArea<?> textArea, ViewModality pageViewModality, String className, String style) {
        if (textArea.isHidden()) {
            return StringUtil.EMPTY;
        }

        // String result;
        ViewModality viewModality = textArea.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : textArea.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<textarea")
                .append(getAttribute("id", textArea.getName()))
                .append(getAttribute("name", textArea.getName()))
                .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result.append(getAttribute("disabled", viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            result
                    .append(getAttribute("readonly", textArea.isReadOnly(), ""))
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
     * @param className
     * @param style
     * @return
     */
    public static String writeComboBox(ComboBox<?> comboBox, ViewModality pageViewModality, String className, String style) throws FrameworkException {
        if (comboBox.isHidden())
            return StringUtil.EMPTY;

        ViewModality viewModality = comboBox.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : comboBox.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<select")
                .append(getAttribute("id", comboBox.getName()))
                .append(getAttribute("name", comboBox.getName()))
                .append(getAttribute("value", comboBox.escapeHtmlValue()))
                .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result.append(getAttribute("disabled", viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
        } else {
            result
                    .append(getAttribute("readonly", comboBox.isReadOnly(), ""));
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
                if (value.getCode().equals(comboBox.getValue()))
                    result.append("<option value=\"" + valueHtml + "\" selected=\"selected\">" + descriptionHtml + "</option>");
                else
                    result.append("<option value=\"" + valueHtml + "\">" + descriptionHtml + "</option>");
            }
        }

        result.append("</select>");

        return result.toString();
    }

    /**
     * Scrive un campo: CheckBox
     *
     * @param checkBox
     * @return
     */
    public static String writeCheckBox(CheckBox<?> checkBox, ViewModality pageViewModality, String className, String style) {
        if (checkBox.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = checkBox.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : checkBox.getViewModality();
        StringBuilder result = new StringBuilder()
                .append("<div class=\"custom-control custom-checkbox\"><input")
                .append(getAttribute("id", checkBox.getName()))
                .append(getAttribute("name", checkBox.getName()))
                .append(getAttribute("type", "checkbox"))
                .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CHECK_CLASS))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style))
                .append(getAttribute("value", checkBox.getValChecked().toString()))
                .append(getAttribute("checked", checkBox.getValChecked().toString().equalsIgnoreCase(checkBox.getData()), ""));

        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
            result.append(getAttribute("disabled", ""))
                    .append("/><span")
                    .append(getAttribute("class", "custom-control-label"))
                    .append("></span></div>");
        } else {
            result
                    .append(getAttribute("readonly", checkBox.isReadOnly(), ""))
                    .append("/><label")
                    .append(getAttribute("class", "custom-control-label"))
                    .append(getAttribute("for", checkBox.getName()))
                    .append("></label></div>");
        }

        return result.toString();
    }

    /**
     * Scrive un campo: RadioGroup
     *
     * @param radioGroup
     * @param pageViewModality
     * @param className
     * @param style
     * @return
     */
    public static String writeRadioGroup(RadioGroup<?> radioGroup, ViewModality pageViewModality, String className, String style) throws FrameworkException {
        if (radioGroup.isHidden())
            return "";

        String result = "";
        ViewModality viewModality = radioGroup.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : radioGroup.getViewModality();
        if (viewModality == ViewModality.VIEW_MODIFICA) {

            int i = 0;
            DecodeMap<?, ?> decodeMap = radioGroup.getDecodeMap();
            if (decodeMap != null) {
                for (DecodeValue<?> value : decodeMap) {
                    result += "<input type=\"radio\" ";
                    result += " id=\"" + radioGroup.getName() + i + "\"";
                    result += " name=\"" + radioGroup.getName() + "\"";
                    result += " value=\"" + radioGroup.escapeHtmlValue() + "\"";

                    if (className != null && !className.equals(""))
                        result += " style=\"" + className + "\"";
                    if (style != null && !style.equals(""))
                        result += " style=\"" + style + "\"";
                    else
                        result += " style=\"width:20px\"";

                    if (value.getCode() != null && value.getCode().equals(radioGroup.getValue()))
                        result += " checked=\"checked\"";

                    result += " />&nbsp;";
                    result += "<label for=\"" + radioGroup.getName() + i + "\">" + Casting.getHtml(value.getDescription()) + "</label>";
                    i++;
                }
            }
        } else {
            result = "<span>" + BaseFunction.nvl(radioGroup.getHtmlDecodedText(), "&nbsp;") + "</span>";
        }

        return result;

    }

    /**
     * Scrive un campo: Semaforo
     *
     * @param semaforo
     * @param pageViewModality
     * @param className
     * @param style
     * @return
     */
    public static String writeSemaforo(Semaphore semaforo, ViewModality pageViewModality, String className, String style) {
        if (semaforo.isHidden())
            return "";

        ViewModality viewModality = semaforo.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : semaforo.getViewModality();
        if (viewModality == ViewModality.VIEW_MODIFICA) {
            // FIXME: Implementare
            return "";
        } else {
            return semaforo.getHtmlDecodedText();
        }
    }

    /**
     * Stcrive un campo di input di tipo File
     *
     * @param file
     * @param pageViewModality
     * @param className
     * @param style
     * @return
     */
    public static String writeFile(File file, ViewModality pageViewModality, String className, String style) {
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
                    .append(getAttribute("id", file.getName()))
                    .append(getAttribute("name", file.getName()))
                    .append(getAttribute("type", "file"))
                    .append(getAttribute("data-toggle=\"tooltip\" data-placement=\"bottom\" title", !BaseFunction.isBlank(file.getTooltip()), file.getTooltip()))
                    .append(" class=\"custom-file-input\"><label class=\"custom-file-label\"")
                    .append(getAttribute("for", file.getName()))
                    .append(">Choose file</label></div>");

//                    .append(getAttribute("value", input.escapeHtmlValue()))
//                    .append(getAttribute("step", DataTypes.DATETIME == input.getDataType() || DataTypes.TIME == input.getDataType(), "1"))
//                    .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
//                    .append(getAttribute("style", !BaseFunction.isBlank(style), style))
//                    .append(getAttribute("readonly", input.isReadOnly(), ""))
//                    .append(getAttribute("maxlength", input.getMaxLength() > 0, "" + input.getMaxLength()))
//                    .append(getAttribute("onclick", !BaseFunction.isBlank(onclick), onclick))
//                    .append(getAttribute("onchange", !BaseFunction.isBlank(onchange), onchange));
        }

        return result.toString();

//  <div class="custom-file small"><input type="file" id="inputGroupFile02" class="custom-file-input"><label class="custom-file-label" for="inputGroupFile02">Choose file</label>
//          </div>


//
//        StringBuilder result = new StringBuilder()
//                .append("<input")
//                .append(getAttribute("id", input.getName()))
//                .append(getAttribute("name", input.getName()));
//
//        if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
//            result
//                    .append(getAttribute("type", "text"))
//                    .append(getAttribute("value", input.escapeHtmlText()))
//                    .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
//                    .append(getAttribute("style", !BaseFunction.isBlank(style), style))
//                    .append(getAttribute("disabled", viewModality == ViewModality.VIEW_VISUALIZZAZIONE, ""));
//        } else {
//            result
//                    .append(getAttribute("type", input.getDataType().getHtmlType()))
//                    .append(getAttribute("value", input.escapeHtmlValue()))
//                    .append(getAttribute("step", DataTypes.DATETIME == input.getDataType() || DataTypes.TIME == input.getDataType(), "1"))
//                    .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.CONTROL_CLASS))
//                    .append(getAttribute("style", !BaseFunction.isBlank(style), style))
//                    .append(getAttribute("readonly", input.isReadOnly(), ""))
//                    .append(getAttribute("maxlength", input.getMaxLength() > 0, "" + input.getMaxLength()))
//                    .append(getAttribute("onclick", !BaseFunction.isBlank(onclick), onclick))
//                    .append(getAttribute("onchange", !BaseFunction.isBlank(onchange), onchange));
//        }
//
//        result.append("/>");
//
//        return result.toString();

//
//        log.info("writeFile: IN");
//        String result = "ccccccccccccccccc";
//
//
//        ViewModality viewModality = file.getViewModality() == ViewModality.VIEW_AUTO ? pageViewModality : file.getViewModality();
//        if (viewModality == ViewModality.VIEW_MODIFICA) {
//            result = "<input ";
//
//            result += " id=\"" + file.getName() + "\"";
//            result += " name=\"" + file.getName() + "\"";
//            result += " value=\"\"";
//            result += " type=\"file\"";
//
//            if (file.isReadOnly())
//                result += " disabled=\"disabled\"";
//
//            if (className != null && !className.equals(""))
//                result += " style=\"" + className + "\"";
//            if (style != null && !style.equals(""))
//                result += " style=\"" + style + "\"";
//            if (onclick != null && !onclick.equals(""))
//                result += " onclick=\"" + onclick + "\"";
//            if (onchange != null && !onchange.equals(""))
//                result += " onchange=\"" + onchange + "\"";
//
//            result += " />";
//
//        } else {
//            result = "<span>&nbsp;</span>";
//        }
//
//        log.info("writeFile: OUT");
//
//        return result;
    }

    /**
     * Scrive un campo: LinkButton
     *
     * @param button
     * @param lastController
     * @param className
     * @param style
     * @return
     */
    public static String writeLinkButton(Link button, String lastController, String className, String style) {
        if (button.isHidden()) {
            return "";
        }

        // String htmlDisabled = button.isDisabled() ? "" : " disabled=\"disabled\"";
        // String htmlDescription = Casting.getHtml(button.getDescription());
        // String htmlConfirmMessage = BaseFunction.isBlank(button.getConfirmMessage()) ? "" : " confirmMessage=\"" + Casting.getHtml(button.getConfirmMessage()) + "\"";
        //
        // if (!BaseFunction.isBlank(className)) {
        // // NOP
        // } else if (!BaseFunction.isBlank(button.getClassName())) {
        // className = button.getClassName();
        // } else {
        // className = "work";
        // }
        //
        // if (!BaseFunction.isBlank(button.getConfirmMessage())) {
        // className += " confirm";
        // }
        //
        // return "<a href=\"" + lastController + "?" + button.getHtlmName() + "=true\" " + " class=\"" + className + "\"" + htmlDisabled + htmlConfirmMessage + ">" + htmlDescription + "</a>";

        return null;
    }

    /**
     * Scrive un campo: Button
     *
     * @param button
     * @param className
     * @param style
     * @return
     */
    public static String writeButton(Button button, String className, String style) {
        if (button.isHidden()) {
            return StringUtil.EMPTY;
        }

        //button.getButtonType()

        StringBuilder result = new StringBuilder()
                .append("<button")
                .append(getAttribute("id", button.getHtlmName()))
                .append(getAttribute("name", button.getHtlmName()))
                .append(getAttribute("class", !BaseFunction.isBlank(className), className, BootStrapClass.BUTTON_CLASS))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style))
                .append(getAttribute("disabled", button.isDisabled(), ""))
                .append(getAttribute("data-toggle", !BaseFunction.isBlank(button.getTooltip()), "tooltip"))
                .append(getAttribute("data-placement", !BaseFunction.isBlank(button.getTooltip()), "bottom"))
                .append(getAttribute("title", !BaseFunction.isBlank(button.getTooltip()), button.getTooltip()))
                .append("/>" + Casting.getHtml(button.getDescription()) + BaseFunction.nvl(button.getImgHtml(), "") + "</button>");

        return result.toString();
    }
}

package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.base.Elements;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

public class LabelControlWriter extends HtmlWriter {

    private static boolean hasLabel(SimpleField element) {
        return !(element instanceof Button);
    }

    public static String writeLabel(SimpleField simpleField, boolean simple) {
        if (!hasLabel(simpleField)) {
            return StringUtil.EMPTY;
        }

        String enrich = StringUtil.EMPTY;
        if (!simple) {
            if (simpleField instanceof InputField && ((InputField<?>) simpleField).isRequired() || simpleField instanceof File && ((File) simpleField).isRequired()) {
                enrich = "*";
            }

            enrich += ":";
        }

        return new StringBuilder()
                .append("<label")
                .append(getAttribute("for", simpleField.getName()))
                .append(getAttribute(ATTR_CLASS, BootStrapClass.LABEL_CLASS))
                .append(getTooltipAttributes(simpleField.getTooltip()))
                .append(">" + simpleField.getHtmlDescription() + enrich + "</label>")
                .toString();
    }

    public static String writeLblControl(SimpleField simpleField, Elements<?> parentElement, ViewModality viewModality, String labelWidth, String controlWidth) throws FrameworkException {
        return writeLblControl(simpleField, parentElement, viewModality, false, labelWidth, controlWidth, labelWidth, controlWidth, false, null, null);
    }

    public static String writeLblControl(SimpleField simpleField, Elements<?> parentElement, ViewModality viewModality, boolean overflow, String labelWidth, String controlWidth, String mLabelWidth, String mControlWidth, boolean revert, String labelCellClassName, String controlCellClassName) throws FrameworkException {
        StringBuilder result = new StringBuilder();

        if (revert) {
            result
                    .append(GroupWriter.openCell(controlWidth, mControlWidth, BaseFunction.isBlank(controlCellClassName) ? BootStrapClass.TEXT_RIGHT : controlCellClassName))
                    .append(FormControlWriter.writeControl(simpleField, parentElement, viewModality, overflow))
                    .append(GroupWriter.closeCell());

            if (hasLabel(simpleField)) {
                result
                        .append(GroupWriter.openCell(labelWidth, mLabelWidth, labelCellClassName))
                        .append(writeLabel(simpleField, true))
                        .append(GroupWriter.closeCell());
            }

        } else {
            if (hasLabel(simpleField)) {
                result
                        .append(GroupWriter.openCell(labelWidth, mLabelWidth, BaseFunction.isBlank(labelCellClassName) ? BootStrapClass.TEXT_RIGHT : labelCellClassName))
                        .append(writeLabel(simpleField, false))
                        .append(GroupWriter.closeCell());
            }

            result
                    .append(GroupWriter.openCell(controlWidth, mControlWidth, controlCellClassName))
                    .append(FormControlWriter.writeControl(simpleField, parentElement, viewModality, overflow))
                    .append(GroupWriter.closeCell());

        }

        return result.toString();

    }


}

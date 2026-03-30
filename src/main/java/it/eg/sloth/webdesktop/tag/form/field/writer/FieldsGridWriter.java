package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.Hidden;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class FieldsGridWriter extends HtmlWriter {

    public static final String GRID_CLASS = "table{0}{1}";

    private static final String TABLE_OPEN = "\n" +
            "<!-- Table: {0} -->\n" +
            "{1}" +
            "<table class=\"{2}\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\n";

    private static final String TABLE_CLOSE = "</table>\n" +
            "{0}";

    private static final String HEAD_OPEN = " <thead>\n";
    private static final String HEAD_CLOSE = " </thead>\n";
    private static final String ROW_OPEN = "  <tr>\n";
    private static final String ROW_CLOSE = "  </tr>\n";

    public static String openTable(Fields<?> fields, boolean responsive, boolean border, boolean small) {
        String classHtml = MessageFormat.format(GRID_CLASS, border ? " table-bordered" : "", small ? " table-sm" : "");

        return MessageFormat.format(TABLE_OPEN,
                Casting.getHtml(fields.getTitle()),
                responsive ? "<div class=\"table-responsive\">\n" : StringUtil.EMPTY,
                classHtml
        );
    }

    public static String closeTable(boolean responsive) {
        return MessageFormat.format(TABLE_CLOSE, responsive ? "</div>\n" : StringUtil.EMPTY);
    }

    public static String openHeader() {
        return HEAD_OPEN;
    }

    public static String closeHeader() {
        return HEAD_CLOSE;
    }

    public static String headerRow(Fields<?> fields) {
        StringBuilder result = new StringBuilder()
                .append(ROW_OPEN);

        String rowNumber = "0";
        for (SimpleField field : fields) {
            if (!rowNumber.equals(StringUtil.split(field.getName(), "|")[1])) {
                break;
            }

            String descriptionHtml = field.getHtmlDescription();

            if (field instanceof TextField && ((TextField<?>) field).isHidden() || field instanceof Hidden<?>) {
                continue;
            }

            if (field instanceof InputField) {
                InputField<?> inputField = (InputField<?>) field;
                descriptionHtml = inputField.getHtmlDescription() + (inputField.isRequired() ? " *" : "");
            }

            result.append(openCell(field));
            if (field instanceof Button) {
                result.append(NBSP);
            } else {
                result.append(descriptionHtml);
            }

            result.append(closeCell(true));

        }

        return result
                .append(ROW_CLOSE)
                .toString();

    }

    public static String header(Fields<?> fields) {
        return openHeader() +
                headerRow(fields) +
                closeHeader();
    }

    public static String openCell(SimpleField field) {
        // Stile cella
        String htmlClass = "text-left";
        if (FieldType.SEMAPHORE == field.getFieldType() || FieldType.CHECK_BOX == field.getFieldType() || FieldType.BUTTON == field.getFieldType()) {
            htmlClass = "text-center";
        } else if (((TextField<?>) field).getDataType().isNumber() && !(field instanceof DecodedDataField) && !(field instanceof MultipleAutoComplete)) {
            htmlClass = "text-right";
        }

        if (field instanceof Button) {
            htmlClass += " p-1";
        }

        return MessageFormat.format("   <td{0}>", getAttribute(ATTR_CLASS, htmlClass));
    }

    public static String closeCell(boolean headder) {
        if (headder) {
            return "</th>\n";
        } else {
            return "</td>\n";
        }
    }

    public static String cell(Grid<?> grid, SimpleField field, ViewModality viewModality) throws FrameworkException {
        StringBuilder result = new StringBuilder()
                .append(openCell(field));

        if (ViewModality.VIEW == viewModality) {
            result.append(TextControlWriter.writeControl(field, grid));
        } else {
            result.append(FormControlWriter.writeControl(field, grid, viewModality, false));
        }

        return result
                .append(closeCell(false))
                .toString();
    }


    public static String rows(Fields<?> fields, ViewModality viewModality) throws FrameworkException {
        StringBuilder result = new StringBuilder();
        result.append(" <tbody>\n");

        String currentRow = "0";
        result.append(ROW_OPEN);
        for (SimpleField field : fields) {
            String rowNumber = StringUtil.split(field.getName(), "|")[1];

            if (!currentRow.equals(rowNumber)) {
                currentRow = rowNumber;
                result.append(ROW_CLOSE);
                result.append(ROW_OPEN);
            }

            result.append(openCell(field));
            if (ViewModality.VIEW == viewModality) {
                result.append(TextControlWriter.writeControl(field, fields));
            } else {
                result.append(FormControlWriter.writeControl(field, fields, viewModality, false));
            }
            result.append(closeCell(false));

        }
        result.append(ROW_CLOSE);

        result.append(" </tbody>\n");

        return result.toString();
    }


}

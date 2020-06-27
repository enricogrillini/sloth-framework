package it.eg.sloth.webdesktop.tag.form.grid.writer;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;

import java.text.MessageFormat;

public class GridWriter extends HtmlWriter {

    public static String openTable(Grid<?> grid, boolean border, boolean hover, boolean small) {
        String classHtml = MessageFormat.format(BootStrapClass.GRID_CLASS, border ? " table-bordered" : "", hover ? " table-hover" : "", small ? " table-sm" : "");

        return new StringBuilder()
                .append("\n")
                .append("<!-- Table: " + Casting.getHtml(grid.getTitle()) + " -->\n")
                .append("<table class=\"" + classHtml + "\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\n")
                .toString();

    }

    public static String closeTable() {
        return new StringBuilder()
                .append("</table>\n")
                .toString();
    }

    public static String openHeader() {
        return new StringBuilder()
                .append(" <thead>\n")
                .toString();
    }

    public static String closeHeader() {
        return new StringBuilder()
                .append(" </thead>\n")
                .toString();
    }

    public static String headerRow(Grid<?> grid, boolean sortable) {
        StringBuilder result = new StringBuilder()
                .append("  <tr>\n");

        for (SimpleField field : grid) {
            String descriptionHtml = field.getHtmlDescription();

            if (field instanceof InputField) {
                InputField<?> inputField = (InputField<?>) field;
                if (inputField.isHidden())
                    continue;

                descriptionHtml = inputField.getHtmlDescription() + (inputField.isRequired() ? " *" : "");
            }

            result.append(openCell(field, true, sortable));
            if (field instanceof Button) {
                result.append(NBSP);
            } else if (sortable && field instanceof TextField) {
                TextField<?> textField = (TextField<?>) field;

                String iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort float-right m-1");
                String buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_ASC, grid.getName(), textField.getAlias()));

                SortingRule sortingRule = grid.getDataSource().getSortingRules().getFirstRule();
                if (sortingRule != null && sortingRule.getFieldName().equalsIgnoreCase(textField.getAlias())) {
                    if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST) {
                        iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort-up float-right m-1");
                        buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_DESC, grid.getName(), textField.getAlias()));
                    } else {
                        iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort-down float-right m-1");
                        buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_ASC, grid.getName(), textField.getAlias()));
                    }
                }

                result.append("<button type=\"submit\"" + buttonNameHtml + " class=\"btn btn-Link btn-block btn-sm\"><b>" + descriptionHtml + "</b><i" + iconHtml + "></i></button>");

            } else {
                result.append(descriptionHtml);
            }

            result.append(closeCell(true));

        }

        return result
                .append("  </tr>\n")
                .toString();

    }

    public static String header(Grid<?> grid, boolean sortable) {
        return new StringBuilder()
                .append(openHeader())
                .append(headerRow(grid, sortable))
                .append(closeHeader())
                .toString();
    }

    public static String openCell(SimpleField field, boolean header, boolean sortable) {
        StringBuilder result = new StringBuilder();

        if (header) {
            result.append("   <th")
                    .append(getAttributeTooltip(field.getTooltip()));
        } else {
            result.append("   <td");
        }

        if (sortable) {
            result.append(getAttribute(ATTR_CLASS, "text-center p-1"));
        } else if (FieldType.SEMAPHORE == field.getFieldType() ||
                FieldType.CHECK_BOX == field.getFieldType() ||
                FieldType.BUTTON == field.getFieldType()) {
            result.append(getAttribute(ATTR_CLASS, "text-center"));
        } else if (((TextField<?>) field).getDataType().isNumber() && !(field instanceof DecodedDataField)) {
            result.append(getAttribute(ATTR_CLASS, "text-right"));
        } else {
            result.append(getAttribute(ATTR_CLASS, "text-left"));
        }

        return result.append(">")
                .toString();
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
                .append(openCell(field, false, false));

        if (ViewModality.VIEW_VISUALIZZAZIONE == viewModality) {
            result.append(TextControlWriter.writeControl(field));
        } else {
            result.append(FormControlWriter.writeControl(field, grid, viewModality));
        }

        return result
                .append(closeCell(false))
                .toString();
    }

    public static String rows(Grid<?> grid, ViewModality viewModality) throws FrameworkException {
        int rowNumber = 0;

        StringBuilder result = new StringBuilder();

        result.append(" <tbody>\n");

        DataTable<?> dataTable = grid.getDataSource();
        for (DataRow dataRow : dataTable) {
            if (dataTable.getPageSize() <= 0 || (dataTable.getPageStart() <= rowNumber && dataTable.getPageEnd() >= rowNumber)) {

                String classHtml = "";
                if (rowNumber == dataTable.getCurrentRow()) {
                    classHtml += " class=\"table-primary\"";
                }

                // Riga corrente in edit mode
                if (rowNumber == dataTable.getCurrentRow() && ViewModality.VIEW_MODIFICA == viewModality) {
                    result.append("  <tr" + classHtml + ">\n");
                    for (SimpleField field : grid.getElements()) {
                        if (field instanceof InputField && ((InputField<?>) field).isHidden())
                            continue;

                        if (field instanceof Button) {
                            Button button = (Button) field;
                            button.setIndex(rowNumber);
                        }

                        result.append(GridWriter.cell(grid, field, viewModality));
                    }

                    result.append("  </tr>\n");

                } else {
                    String idHtml = " id=\"" + NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber) + "\"";

                    result.append("  <tr" + idHtml + classHtml + ">\n");
                    for (SimpleField field : grid.getElements()) {
                        SimpleField appField = field.newInstance();

                        if (appField instanceof Button) {
                            Button button = (Button) appField;
                            button.setIndex(rowNumber);
                        } else if (appField instanceof DataField) {
                            DataField<?> dataField = (DataField<?>) appField;
                            dataField.copyFromDataSource(dataRow);
                        }

                        result.append(GridWriter.cell(grid, appField, ViewModality.VIEW_VISUALIZZAZIONE));
                    }
                    result.append("  </tr>\n");
                }

                // if (hasDetail()) {
                // for (SimpleField field : getElement().getElements()) {
                // SimpleField fieldClone = field.newInstance();
                //
                // if (fieldClone instanceof Button) {
                // Button button = (Button) fieldClone;
                // button.setIndex(rowNumber);
                // } else if (fieldClone instanceof DataField) {
                // DataField<?> dataField = (DataField<?>) fieldClone;
                // dataField.copyFromDataSource(dataRow);
                // }
                //
                // writeln(" <tr class=\"frDetail\">");
                // writeln(" <td>&nbsp;</td>");
                // writeln(" <td colspan=\"" + getElement().getElements().size() + "\">");
                // writeln(" <b style=\"color:#660000\">" + fieldClone.getHtmlDescription() + ": </b><span>" + FormControlWriter.writeControl(fieldClone, getElement(), ViewModality.VIEW_VISUALIZZAZIONE) + "</span>");
                // writeln(" </td>");
                // writeln(" </tr>");
                // }
                // }
            }
            rowNumber++;
        }
        result.append(" </tbody>\n");

        return result.toString();
    }

}

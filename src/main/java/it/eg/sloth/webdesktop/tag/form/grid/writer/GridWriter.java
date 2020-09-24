package it.eg.sloth.webdesktop.tag.form.grid.writer;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class GridWriter extends HtmlWriter {

    private static final String CELL_DETAIL = "   <td class=\"text-center tableDetail\"><i class=\"tableDetail text-info fa fa-chevron-down collapsed\" href=\"#{0}\" data-toggle=\"collapse\" aria-expanded=\"true\" aria-controls=\"collapse-collapsed\"></i></td>\n";

    private static final String ROW_CLOSE = "  </tr>\n";


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

    public static String headerRow(Grid<?> grid, Fields<?> detailFields, boolean sortable) {
        StringBuilder result = new StringBuilder()
                .append("  <tr>\n");

        if (detailFields != null) {
            result.append("   <th width=\"3%\"></th>\n");
        }

        for (SimpleField field : grid) {
            String descriptionHtml = field.getHtmlDescription();

            if (field instanceof TextField && ((TextField<?>) field).isHidden()) {
                continue;
            }

            if (field instanceof InputField) {
                InputField<?> inputField = (InputField<?>) field;
                descriptionHtml = inputField.getHtmlDescription() + (inputField.isRequired() ? " *" : "");
            }

            result.append(openCell(field, true, sortable));
            if (field instanceof Button) {
                result.append(NBSP);
            } else if (sortable && field instanceof TextField) {
                TextField<?> textField = (TextField<?>) field;

                String iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort m-1");
                String buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_ASC, grid.getName(), textField.getName()));

                SortingRule sortingRule = grid.getDataSource().getSortingRules().getFirstRule();
                if (sortingRule != null && sortingRule.getFieldName().equalsIgnoreCase(textField.getOrderByAlias())) {
                    if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST) {
                        iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort-up m-1");
                        buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_DESC, grid.getName(), textField.getName()));
                    } else {
                        iconHtml = getAttribute(ATTR_CLASS, "fas fa-sort-down m-1");
                        buttonNameHtml = getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.SORT_ASC, grid.getName(), textField.getName()));
                    }
                }

                result.append("<button type=\"submit\"" + buttonNameHtml + " class=\"btn btn-Link btn-block btn-sm d-flex justify-content-between\"><b class=\"w-100\">" + descriptionHtml + "</b><i" + iconHtml + "></i></button>");

            } else {
                result.append(descriptionHtml);
            }

            result.append(closeCell(true));

        }

        return result
                .append(ROW_CLOSE)
                .toString();

    }

    public static String header(Grid<?> grid, Fields<?> detailFields, boolean sortable) {
        return new StringBuilder()
                .append(openHeader())
                .append(headerRow(grid, detailFields, sortable))
                .append(closeHeader())
                .toString();
    }

    public static String openCell(SimpleField field, boolean header, boolean sortable) {

        // Stile cella
        String htmlClass = "text-left";
        if (sortable) {
            htmlClass = "text-center p-1";
        } else if (FieldType.SEMAPHORE == field.getFieldType() || FieldType.CHECK_BOX == field.getFieldType() || FieldType.BUTTON == field.getFieldType()) {
            htmlClass = "text-center";
        } else if (((TextField<?>) field).getDataType().isNumber() && !(field instanceof DecodedDataField)) {
            htmlClass = "text-right";
        }


        if (header) {
            return new StringBuilder()
                    .append("   <th")
                    .append(getAttributeTooltip(field.getTooltip()))
                    .append(getAttribute(ATTR_CLASS, htmlClass + " text-nowrap"))
                    .append(">")
                    .toString();
        } else {
            return new StringBuilder()
                    .append("   <td")
                    .append(getAttributeTooltip(field.getTooltip()))
                    .append(getAttribute(ATTR_CLASS, htmlClass))
                    .append(">")
                    .toString();

        }

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

    public static String rows(Grid<?> grid, Fields<?> detailFields, ViewModality viewModality, boolean manageCurrentRow) throws FrameworkException {
        int rowNumber = 0;

        StringBuilder result = new StringBuilder();

        result.append(" <tbody>\n");

        DataTable<?> dataTable = grid.getDataSource();
        for (DataRow dataRow : dataTable) {
            String idRow = NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber);
            String idRowDetail = NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber, "detail");


            if (dataTable.getPageSize() <= 0 || (dataTable.getPageStart() <= rowNumber && dataTable.getPageEnd() >= rowNumber)) {
                String classHtml = getAttribute("class", manageCurrentRow && rowNumber == dataTable.getCurrentRow(), "table-primary");

                // Riga corrente in edit mode
                if (rowNumber == dataTable.getCurrentRow() && ViewModality.VIEW_MODIFICA == viewModality) {
                    result.append(MessageFormat.format("  <tr{0}>\n", classHtml));
                    if (detailFields != null) {
                        result.append("   <td>&nbsp;</td>\n");
                    }

                    for (SimpleField field : grid.getElements()) {
                        if (field instanceof TextField && ((TextField<?>) field).isHidden())
                            continue;

                        if (field instanceof Button) {
                            Button button = (Button) field;
                            button.setIndex(rowNumber);
                        }

                        result.append(GridWriter.cell(grid, field, viewModality));
                    }
                } else {
                    result.append(MessageFormat.format("  <tr{0}{1}>\n", getAttribute("id", idRow), classHtml));
                    if (detailFields != null) {
                        result.append(MessageFormat.format(CELL_DETAIL, idRowDetail));
                    }
                    for (SimpleField field : grid.getElements()) {
                        SimpleField appField = field.newInstance();

                        if (field instanceof TextField && ((TextField<?>) field).isHidden())
                            continue;

                        if (appField instanceof Button) {
                            Button button = (Button) appField;
                            button.setIndex(rowNumber);
                        } else if (appField instanceof DataField) {
                            DataField<?> dataField = (DataField<?>) appField;
                            dataField.copyFromDataSource(dataRow);
                        }

                        result.append(GridWriter.cell(grid, appField, ViewModality.VIEW_VISUALIZZAZIONE));
                    }
                }
                result.append(ROW_CLOSE);

                if (detailFields != null) {
                    result
                            .append(MessageFormat.format("  <tr {0} class=\"frDetail collapse\">\n", getAttribute("id", idRowDetail)))
                            .append("   <td>&nbsp;</td>\n");

                    for (SimpleField field : detailFields) {
                        SimpleField fieldClone = field.newInstance();

                        if (fieldClone instanceof Button) {
                            Button button = (Button) fieldClone;
                            button.setIndex(rowNumber);
                        } else if (fieldClone instanceof DataField) {
                            DataField<?> dataField = (DataField<?>) fieldClone;
                            dataField.copyFromDataSource(dataRow);
                        }

                        result.append("   <td colspan=\"" + grid.getElements().size() + "\">\n");
                        result.append("    <b style=\"color:#660000\">" + fieldClone.getHtmlDescription() + ": </b><span>" + TextControlWriter.writeControl(fieldClone) + "</span>\n");
                        result.append("   </td>\n");
                    }
                    result.append(ROW_CLOSE);
                }
            }
            rowNumber++;
        }
        result.append(" </tbody>\n");

        return result.toString();
    }

    public static String total(Grid<?> grid, boolean hasDetail) throws FrameworkException {
        StringBuilder result = new StringBuilder();
        result.append(" <tr>\n");

        if (hasDetail) {
            result.append("  <td>&nbsp;</td>\n");
        }

        for (SimpleField field : grid.getElements()) {
            if (field instanceof TextField && ((TextField<?>) field).isHidden())
                continue;

            if (field instanceof TextTotalizer || field instanceof InputTotalizer) {
                TextField<BigDecimal> textField = (TextField<BigDecimal>) field.newInstance();

                BigDecimal totale = new BigDecimal(0);
                for (DataRow dataRow : grid.getDataSource()) {
                    totale = totale.add(BaseFunction.nvl(dataRow.getBigDecimal(textField.getAlias()), new BigDecimal(0)));
                }

                textField.setValue(totale);

                result.append("  <td class=\"sum\">" + textField.escapeHtmlText() + "</td>\n");
            } else {
                result.append("  <td class=\"sum\">&nbsp;</td>\n");
            }
        }

        return result
                .append(" </tr>\n")
                .toString();

    }

}

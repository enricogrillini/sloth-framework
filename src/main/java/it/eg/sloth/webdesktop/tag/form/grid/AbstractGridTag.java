package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.math.BigDecimal;

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
@Getter
@Setter
public abstract class AbstractGridTag<T extends Grid<?>> extends BaseElementTag<T> {

    static final long serialVersionUID = 1L;

    String detailName;

    public boolean hasDetail() {
        return getDetailName() != null && !getDetailName().equals("");
    }

    protected Fields<?> getDetail() {
        return (Fields<?>) getForm().getElement(getDetailName());
    }

    protected void writeCell(SimpleField field, DataRow dataRow, int rowNumber, ViewModality viewModality) throws FrameworkException, IOException {
        String classHtml = "";
        switch (field.getFieldType()) {
            case SEMAPHORE:
            case CHECK_BOX:
                classHtml = " class=\"text-center\"";
                break;

            case DECODED_TEXT:
            case AUTO_COMPLETE:
            case COMBO_BOX:
                break;

            default:
                if (((TextField<?>) field).getDataType().isNumber()) {
                    classHtml = " class=\"text-right\"";
                }
        }

        write("   <td" + classHtml + ">");
        if (ViewModality.VIEW_VISUALIZZAZIONE == viewModality) {
            write(TextControlWriter.writeControl(field));
        } else {
            write(FormControlWriter.writeControl(field, getElement(), viewModality));
        }
        writeln("</td>");
    }

    protected void writeHeader() throws IOException {
        Grid<?> grid = getElement();
        writeln(" <thead>");
        writeln("  <tr>");

        for (SimpleField field : grid) {
            String descriptionHtml = field.getHtmlDescription();

            if (field instanceof InputField) {
                InputField<?> inputField = (InputField<?>) field;

                if (inputField.isHidden())
                    continue;

                descriptionHtml = inputField.getHtmlDescription() + (inputField.isRequired() ? " *" : "");
            }

            if (field instanceof TextField) {
                TextField<?> textField = (TextField<?>) field;

                SortingRule sortingRule = grid.getDataSource().getSortingRules().getFirstRule();

                String iconHtml = " class=\"fas fa-sort float-right m-1\"";
                String buttonNameHtml = " name=\"" + NavigationConst.navStr(NavigationConst.SORT_ASC, getName(), textField.getAlias()) + "\"";
                if (sortingRule != null && sortingRule.getFieldName().equalsIgnoreCase(textField.getAlias())) {
                    if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST) {
                        iconHtml = " class=\"fas fa-sort-up float-right m-1\"";
                        buttonNameHtml = " name=\"" + NavigationConst.navStr(NavigationConst.SORT_DESC, getName(), textField.getAlias()) + "\"";
                    } else {
                        iconHtml = " class=\"fas fa-sort-down float-right m-1\"";
                        buttonNameHtml = " name=\"" + NavigationConst.navStr(NavigationConst.SORT_ASC, getName(), textField.getAlias()) + "\"";
                    }
                }

                String toolTipHtml = StringUtil.EMPTY;
                if (!BaseFunction.isBlank(field.getTooltip())) {
                    toolTipHtml = " data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + field.getHtmlTooltip() + "\"";
                }

                writeln("   <th class=\"p-1\"" + toolTipHtml + "><button type=\"submit\"" + buttonNameHtml + " class=\"btn btn-Link btn-block btn-sm\"><b>" + descriptionHtml + "</b><i" + iconHtml + "></i></button></th>");
                // writeln(" <th class=\"text-center\" title=\"" + field.getHtmlTooltip() + "\">" + descriptionHtml + "</th>");
                // writeln(" <th><button class=\"ui-button ui-widget ui-state-default ui-button-text-icon-secondary\" value=\" \" name=\"" + navigation + "\" role=\"button\" aria-disabled=\"false\" title=\"" + field.getHtmlTooltip() + "\"><span class=\"ui-button-text\"><b>" + descriptionHtml + "</b></span><span class=\"ui-button-icon-secondary ui-icon " + sortIcon + "\"></span></button></th>");

            } else if (field instanceof Button) {
                writeln("   <th>&nbsp;</th>");
            } else {
                writeln("   <th class=\"text-center\" title=\"" + field.getHtmlTooltip() + "\">" + descriptionHtml + "</th>");
                // writeln(" <th style=\"width:1%\"><button class=\"ui-button ui-widget ui-state-default ui-button-text-only\" disabled=\"disabled\"><span class=\"ui-button-text\" title=\"" + field.getHtmlTooltip() + "\">" + descriptionHtml + "</span></button></th>");
            }

        }

        writeln("  </tr>");
        writeln(" </thead>");

    }

    protected void writeTotal() throws  FrameworkException, IOException {

        if (getElement().hasTotalizer()) {
            writeln(" <tr>");

            for (SimpleField field : getElement().getElements()) {
                if (field instanceof TextTotalizer || field instanceof InputTotalizer) {
                    @SuppressWarnings("unchecked")
                    TextField<BigDecimal> textField = (TextField<BigDecimal>) field.newInstance();

                    BigDecimal totale = new BigDecimal(0);
                    for (DataRow dataRow : getElement().getDataSource()) {
                        totale = totale.add(BaseFunction.nvl(dataRow.getBigDecimal(textField.getAlias()), new BigDecimal(0)));
                    }

                    textField.setValue(totale);

                    writeln("  <td class=\"frSum\" style=\"text-align:right\">" + textField.escapeHtmlText() + "</td>");
                } else {
                    writeln("  <td class=\"frSum\">&nbsp;</td>");
                }
            }
            writeln(" </tr>");
        }
    }
}

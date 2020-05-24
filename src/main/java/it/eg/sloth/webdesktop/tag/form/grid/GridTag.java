package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;

import java.io.IOException;

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
public class GridTag extends AbstractGridTag<Grid<?>> {

  private static final long serialVersionUID = 1L;

  protected void writeRows() throws CloneNotSupportedException, FrameworkException, IOException {
    Grid<?> grid = getElement();
    int rowNumber = 0;

    writeln(" <tbody>");

    DataTable<?> dataTable = grid.getDataSource();
    for (DataRow dataRow : dataTable) {
      if (dataTable.getPageSize() <= 0 || (dataTable.getPageStart() <= rowNumber && dataTable.getPageEnd() >= rowNumber)) {

        String classHtml = "";
        if (rowNumber == dataTable.getCurrentRow()) {
          classHtml += " class=\"table-primary\"";
        }

        // Riga corrente in edit mode
        if (rowNumber == dataTable.getCurrentRow() && ViewModality.VIEW_MODIFICA == getForm().getPageInfo().getViewModality()) {
          writeln("  <tr" + classHtml + ">");
          for (SimpleField field : grid.getElements()) {
            if (field instanceof InputField && ((InputField<?>) field).isHidden())
              continue;

            if (field instanceof Button) {
              Button button = (Button) field;
              button.setIndex(rowNumber);
            }

            writeCell(field, dataRow, rowNumber, getForm().getPageInfo().getViewModality());
          }

          writeln("  </tr>");

        } else {
          String idHtml = " id=\"" + NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber) + "\"";

          writeln("  <tr" + idHtml + classHtml + ">");
          for (SimpleField field : grid.getElements()) {
            SimpleField appField = (SimpleField) field.clone();

            if (appField instanceof Button) {
              Button button = (Button) appField;
              button.setIndex(rowNumber);
            } else if (appField instanceof DataField) {
              DataField<?> dataField = (DataField<?>) appField;
              dataField.copyFromDataSource(dataRow);
            }

            writeCell(appField, dataRow, rowNumber, ViewModality.VIEW_VISUALIZZAZIONE);
          }
          writeln("  </tr>");
        }

        if (hasDetail()) {
          for (SimpleField field : getDetail().getElements()) {
            SimpleField fieldClone = (SimpleField) field.clone();

            if (fieldClone instanceof Button) {
              Button button = (Button) fieldClone;
              button.setIndex(rowNumber);
            } else if (fieldClone instanceof DataField) {
              DataField<?> dataField = (DataField<?>) fieldClone;
              dataField.copyFromDataSource(dataRow);
            }

            writeln("  <tr class=\"frDetail\">");
            writeln("   <td>&nbsp;</td>");
            writeln("   <td colspan=\"" + getElement().getElements().size() + "\">");
            writeln("    <b style=\"color:#660000\">" + fieldClone.getHtmlDescription() + ": </b><span>" + FormControlWriter.writeControl(fieldClone, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_VISUALIZZAZIONE, null, null) + "</span>");
            writeln("   </td>");
            writeln("  </tr>");
          }
        }
      }
      rowNumber++;
    }
    writeln(" </tbody>");

  }

  public int startTag() throws Throwable {
    if (getElement().getDataSource() != null) {
      writeln("");
      writeln("<!-- Table: " + Casting.getHtml(getElement().getTitle()) + " -->");
      writeln("<table class=\"" + BootStrapClass.GRID_CLASS + "\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
      writeHeader();
      writeRows();
      writeTotal();
      writeln("</table>");
    }

    return EVAL_BODY_INCLUDE;
  }

  protected void endTag() throws Throwable {
    // NOP
  }

}

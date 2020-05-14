package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;

import java.io.IOException;

public class SimpleGridTag extends AbstractGridTag<Grid<?>> {

  private static final long serialVersionUID = 1L;

  protected void writeRows() throws CloneNotSupportedException, BusinessException, IOException {
    Grid<?> grid = getElement();
    int rowNumber = 0;
    
    writeln(" <tbody>");

    DataTable<?> dataTable = grid.getDataSource();
    for (DataRow dataRow : dataTable) {
      if (dataTable.getPageSize() <= 0 || (dataTable.getPageStart() <= rowNumber && dataTable.getPageEnd() >= rowNumber)) {
        String rowNameHtml = Casting.getHtml(getElement().getName() + rowNumber);
        String rowNameJs = Casting.getJs(getElement().getName() + rowNumber);

        // Stampa campi principali
//        String className = (hasDetail() || rowNumber % 2 == 0) ? "frGray" : "frWhite";
        writeln(" <tr>");

        if (hasDetail()) {
          writeln(" <td><a id=\"" + rowNameHtml + "Expand\" class=\"collapsed\" onclick=\"expandDetail('" + rowNameJs + "')\">Apri</a><a id=\"" + rowNameHtml + "Collapse\" class=\"expanded\" style=\"display:none\" onclick=\"collapseDetail('" + rowNameJs + "')\">Chiudi</a></td>");
        }

        for (SimpleField field : grid) {
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

        writeln(" </tr>");

        // Stampa dettaglio
        if (hasDetail()) {
          String idHtml = " id=\"" + NavigationConst.navStr(NavigationConst.ROW, grid.getName(), "" + rowNumber) + "\"";
          
          writeln(" <tr" + idHtml +">");
          writeln("  <td colspan=\"" + (getElement().getElements().size() + 1) + "\">");
          for (SimpleField field : getDetail().getElements()) {
            SimpleField fieldClone = (SimpleField) field.clone();

            if (fieldClone instanceof Button) {
              Button button = (Button) fieldClone;
              button.setIndex(rowNumber);
            } else if (fieldClone instanceof DataField) {
              DataField<?> dataField = (DataField<?>) fieldClone;
              dataField.copyFromDataSource(dataRow);
            }

            writeln("   <b style=\"color:#660000\">" + fieldClone.getHtmlDescription() + ": </b><span>" + FormControlWriter.writeControl(fieldClone, getElement(), getWebDesktopDto().getLastController(), ViewModality.VIEW_VISUALIZZAZIONE, null, null) + "</span>");
          }
          writeln("  </td>");
          writeln(" </tr>");
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
  }

}

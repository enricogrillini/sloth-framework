package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.grid.RadioGrid;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
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
public class RadioGridTag extends AbstractGridTag<RadioGrid<?>> {

    private static final long serialVersionUID = 1L;

    protected void writeRow(DataRow dataRow, int rowNumber) throws FrameworkException, IOException {
        boolean selected = rowNumber == getElement().getRowSelected();
        boolean readOnly = (getForm().getPageInfo().getViewModality() == ViewModality.VIEW_VISUALIZZAZIONE);

        String className = (rowNumber % 2 == 0) ? "frGray" : "frWhite";

        // Prima Riga
        String riga = "  <td>\n";
        riga += "   <div class=\"" + className + "\" style=\"float:none; color:#000099; font-size:9pt\">";

        int i = 0;
        for (SimpleField field : getElement()) {
            SimpleField appField = field.newInstance();
            if (appField instanceof DataField) {
                DataField<?> dataField = (DataField<?>) appField;
                dataField.copyFromDataSource(dataRow);

                if (i == 1) {
                    riga += " - ";
                } else if (i > 1) {
                    riga += ", ";
                }
                riga += FormControlWriter.writeControl(appField, getElement(), ViewModality.VIEW_VISUALIZZAZIONE);
                i++;
            }
        }
        riga += "  </div>\n";

        // Dettaglio
        riga += " <div class=\"" + className + "\" style=\"float:none;\">";
        i = 0;
        for (SimpleField field : getElement()) {
            if (field instanceof DataField) {
                DataField<?> dataField = (DataField<?>) field.newInstance();
                dataField.copyFromDataSource(dataRow);

                riga += i >= 1 ? ", " : "";
                riga += "<b>" + dataField.getHtmlDescription() + "</b>: " + FormControlWriter.writeControl(dataField, getElement(), ViewModality.VIEW_VISUALIZZAZIONE);
            }

            i++;
        }
        riga += "</div>";
        riga += "  </td>";

        if (readOnly) {
            if (selected) {
                writeln(riga);
            }
        } else {
            writeln(" <tr class=\"" + className + "\">");
            writeln("  <td style=\"width:25px\"><input type=\"radio\" id=\"" + getElement().getName() + rowNumber + "\" name=\"" + getElement().getName() + "\" value=\"" + rowNumber + "\" " + (selected ? "checked=\"checked\"" : "") + "></td>");
            writeln(riga);
            writeln(" </tr>");
        }
    }

    protected void writeLastRow(int rowNumber) throws FrameworkException, IOException {
        boolean selected = getElement().isNewLine();
        boolean readOnly = (getForm().getPageInfo().getViewModality() == ViewModality.VIEW_VISUALIZZAZIONE);

        String className = (rowNumber % 2 == 0) ? "frGray" : "frWhite";

        if (readOnly && selected) {
            writeln(" <tr class=\"" + className + "\">");
            writeln("  <td>");
            int i = 0;
            for (SimpleField field : getElement()) {
                if (field instanceof DataField) {
                    if (i == 1) {
                        write(" - ");
                    } else if (i > 1) {
                        write(", ");
                    }
                    writeln(FormControlWriter.writeControl(field, getElement(), ViewModality.VIEW_VISUALIZZAZIONE));
                    i++;
                }
            }
            writeln("  </div>");

            // Dettaglio
            writeln(" <div class=\"" + className + "\" style=\"float:none;\">");
            i = 0;
            for (SimpleField field : getElement()) {
                if (field instanceof DataField) {
                    write(i >= 1 ? ", " : "");
                    writeln("<b>" + field.getHtmlDescription() + "</b>: " + FormControlWriter.writeControl(field, getElement(), ViewModality.VIEW_VISUALIZZAZIONE));
                }

                i++;
            }

            writeln("  </td>");
            writeln(" </tr>");

        } else if (!readOnly) {
            // Prima Radio per la selezione
            writeln(" <tr class=\"" + className + "\">");
            writeln("  <td style=\"width:25px\"><input type=\"radio\" id=\"" + getElement().getName() + rowNumber + "\" name=\"" + getElement().getName() + "\" value=\"" + RadioGrid.LAST + "\" " + (selected ? "checked=\"checked\"" : "") + "></td>\n");
            writeln("  <td>\n");
            writeln("   <div class=\"" + className + "\" style=\"color:#000099; font-size:9pt\">Altra destinazione</div>\n");
            writeln("   <div id=\"" + getElement().getName() + "Last\" style=\"" + (selected ? "" : "display:none") + "\">");

            // Grid
            for (SimpleField field : getElement()) {
                if (field instanceof DataField) {
                    String descrizione = field.getHtmlDescription();
                    if (field instanceof InputField && getViewModality() == ViewModality.VIEW_MODIFICA) {
                        InputField<?> inputField = (InputField<?>) field;
                        descrizione += inputField.isRequired() ? "* " : " ";
                    }

                    writeln("   <div class=\"" + className + "\" style=\"float:left; width:30%; height:22px; text-align:right; padding-top: 3px;\">" + descrizione + ": </div>");
                    writeln("   <div class=\"" + className + "\" style=\"float:left; width:50%; height:22px;\">" + FormControlWriter.writeControl(field, getElement(), ViewModality.VIEW_MODIFICA) + "</div>");
                }
            }

            // Dettaglio
            for (Element element : getElement()) {
                SimpleField field = (SimpleField) element;

                writeln("   <div class=\"" + className + "\" style=\"float:left; width:30%; height:22px; text-align:right; padding-top: 3px;\">" + field.getHtmlDescription() + ": </div>");
                writeln("   <div class=\"" + className + "\" style=\"float:left; width:50%; height:22px;\">" + FormControlWriter.writeControl(field, getElement(), ViewModality.VIEW_MODIFICA) + "</div>");
            }

            writeln("  </td>");
            writeln(" </tr>");
        }

    }

    public int startTag() throws IOException, FrameworkException {
        if (getElement().getDataSource() != null) {
            writeln("");
            writeln("<table class=\"frElenco\">");

            int rowNumber = 0;
            DataTable<?> dataTable = getElement().getDataSource();
            for (DataRow dataRow : dataTable) {
                writeRow(dataRow, rowNumber);
                rowNumber++;
            }
            writeLastRow(rowNumber);

            writeln("</table>");
        }
        return EVAL_BODY_INCLUDE;
    }

    protected void endTag() {
        // NOP
    }

}

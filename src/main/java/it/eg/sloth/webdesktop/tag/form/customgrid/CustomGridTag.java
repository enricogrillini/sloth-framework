package it.eg.sloth.webdesktop.tag.form.customgrid;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.customgrid.CustomCell;
import it.eg.sloth.form.customgrid.CustomGrid;
import it.eg.sloth.form.customgrid.CustomRow;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.form.skipper.Skipper;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.grid.writer.GridWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public class CustomGridTag extends BaseElementTag<CustomGrid> {

    static final long serialVersionUID = 1L;

    public int startTag() throws IOException, FrameworkException {
        CustomGrid customGrid = getElement();

        if (!customGrid.getRowList().isEmpty()) {

            writeln("<table class=\"table table-bordered table-sm\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");

            boolean headerOpen = false;
            boolean bodyOpen = false;

            for (CustomRow customRow : customGrid.getRowList()) {
                if (customRow.isHeader() && bodyOpen) {
                    writeln(" </body>");
                    bodyOpen = false;
                }

                if (!customRow.isHeader() && headerOpen) {
                    writeln(" </head>");
                    headerOpen = false;
                }

                if (!customRow.isHeader() && bodyOpen) {
                    writeln(" <body>");
                    bodyOpen = true;
                }

                if (customRow.isHeader() && !headerOpen) {
                    writeln(" <head>");
                    headerOpen = true;
                }


                writeln(" <tr>");

                for (CustomCell customCell : customRow.getCellList()) {
                    if (customRow.isHeader()) {
                        writeln(" <th class=\"%s\">%s</th>".formatted(customCell.getCss(), customCell.getHtml()));
                    } else {
                        writeln(" <td class=\"%s\">%s</td>".formatted(customCell.getCss(), customCell.getHtml()));
                    }
                }

                writeln(" </tr>");
            }

            if (bodyOpen) {
                writeln(" </body>");
            }

            if (headerOpen) {
                writeln(" </head>");
            }

            writeln("</table>");
        }

        return SKIP_BODY;
    }

    protected void endTag() {
        // NOP
    }

}

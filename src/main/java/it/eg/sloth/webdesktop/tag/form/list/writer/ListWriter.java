package it.eg.sloth.webdesktop.tag.form.list.writer;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
@Slf4j
public class ListWriter extends HtmlWriter {

    private static final String ITEM = " <span class=\"list-group-item p-1\">{0}</span>\n";

    public static String writeTitle(Grid<?> grid) {
        if (BaseFunction.isBlank(grid.getTitle())) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append(getElement("h2", grid.getTitle()))
                    .append(getElement("br"))
                    .toString();
        }
    }


    public static String writeList(Grid<?> grid) throws FrameworkException {
        StringBuilder result = new StringBuilder()
                .append("<div class=\"list-group list-group-flush\" style=\"font-size: 0.875rem\">\n");

        Grid<?> appGrid = grid.newInstance();
        DataTable<?> dataTable = grid.getDataSource();

        for (DataRow dataRow : dataTable) {
            appGrid.copyFromDataSource(dataRow);

            for (SimpleField field : appGrid.getElements()) {
                if (field instanceof DataField && !((DataField<?>) field).isHidden()) {
                    result.append(MessageFormat.format(ITEM, TextControlWriter.writeControl(field, appGrid)));

                    // NOTA - Stampo solo il primo campo
                    break;
                }
            }
        }

        return result
                .append("</div>\n")
                .toString();
    }

}

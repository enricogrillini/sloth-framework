package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.grid.writer.GridWriter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class GridWriterTest {

    private static final String CONTENT_TEMPLATE = " <tbody>\n" +
            "  <tr id=\"navigationprefix___row___prova___0\">\n" +
            "   <td class=\"text-left\">valore1</td>\n" +
            "  </tr>\n" +
            "  <tr id=\"navigationprefix___row___prova___1\" class=\"table-primary\">\n" +
            "   <td class=\"text-left\">valore2</td>\n" +
            "  </tr>\n" +
            " </tbody>\n";

    @Test
    public void fieldCardContentTest() throws FrameworkException {
        Table table = new Table();
        table.add().setString("campo1", "valore1");
        table.add().setString("campo1", "valore2");

        Grid<Table> grid = new Grid<>("prova");
        grid.addChild(new Text<String>("campo1", "campo1", DataTypes.STRING));
        grid.setDataSource(table);

        assertEquals(CONTENT_TEMPLATE, GridWriter.rows(grid, ViewModality.VIEW_VISUALIZZAZIONE));
    }

}

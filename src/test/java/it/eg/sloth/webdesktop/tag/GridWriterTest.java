package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.grid.writer.GridWriter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
            "  <tr id=\"navigationprefix___row___provagrid___0\">\n" +
            "   <td class=\"text-left\">valore1</td>\n" +
            "   <td class=\"text-left\">A</td>\n" +
            "  </tr>\n" +
            "  <tr id=\"navigationprefix___row___provagrid___1\" class=\"table-primary\">\n" +
            "   <td class=\"text-left\">valore2</td>\n" +
            "   <td class=\"text-left\">B</td>\n" +
            "  </tr>\n" +
            " </tbody>\n";

    private static final String CONTENT_TEMPLATE_DETAIL = " <tbody>\n" +
            "  <tr id=\"navigationprefix___row___provagrid___0\">\n" +
            "   <td class=\"text-center tableDetail\"><i class=\"tableDetail text-info fa fa-chevron-down collapsed\" href=\"#navigationprefix___row___provagrid___0___detail\" data-toggle=\"collapse\" aria-expanded=\"true\" aria-controls=\"collapse-collapsed\"></i></td>\n" +
            "   <td class=\"text-left\">valore1</td>\n" +
            "   <td class=\"text-left\">A</td>\n" +
            "  </tr>\n" +
            "  <tr  id=\"navigationprefix___row___provagrid___0___detail\" class=\"frDetail collapse\">\n" +
            "   <td>&nbsp;</td>\n" +
            "   <td colspan=\"2\">\n" +
            "    <b style=\"color:#660000\">campo3: </b><span>Lorem ipsum A</span>\n" +
            "   </td>\n" +
            "  </tr>\n" +
            "  <tr id=\"navigationprefix___row___provagrid___1\" class=\"table-primary\">\n" +
            "   <td class=\"text-center tableDetail\"><i class=\"tableDetail text-info fa fa-chevron-down collapsed\" href=\"#navigationprefix___row___provagrid___1___detail\" data-toggle=\"collapse\" aria-expanded=\"true\" aria-controls=\"collapse-collapsed\"></i></td>\n" +
            "   <td class=\"text-left\">valore2</td>\n" +
            "   <td class=\"text-left\">B</td>\n" +
            "  </tr>\n" +
            "  <tr  id=\"navigationprefix___row___provagrid___1___detail\" class=\"frDetail collapse\">\n" +
            "   <td>&nbsp;</td>\n" +
            "   <td colspan=\"2\">\n" +
            "    <b style=\"color:#660000\">campo3: </b><span>Lorem ipsum B</span>\n" +
            "   </td>\n" +
            "  </tr>\n" +
            " </tbody>\n";

    Table table;
    Grid<Table> grid;

    @Before
    public void init() {
        table = new Table();
        Row row = table.add();
        row.setString("campo1", "valore1");
        row.setString("campo2", "A");
        row.setString("campo3", "Lorem ipsum A");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setString("campo3", "Lorem ipsum B");

        grid = new Grid<>("provaGrid");
        grid.addChild(new Text<String>("campo1", "campo1", DataTypes.STRING));
        grid.addChild(new Text<String>("campo2", "campo2", DataTypes.STRING));
        grid.setDataSource(table);
    }

    @Test
    public void gridTest() throws FrameworkException {
        assertEquals(CONTENT_TEMPLATE, GridWriter.rows(grid, null, ViewModality.VIEW_VISUALIZZAZIONE));
    }

    @Test
    public void gridDetailTest() throws FrameworkException {
        Fields<Table> fields = new Fields<>("provaFields");
        fields.addChild(new Text<String>("campo3", "campo3", DataTypes.STRING));
        fields.setDataSource(table);

        assertEquals(CONTENT_TEMPLATE_DETAIL, GridWriter.rows(grid, fields, ViewModality.VIEW_VISUALIZZAZIONE));
    }

}

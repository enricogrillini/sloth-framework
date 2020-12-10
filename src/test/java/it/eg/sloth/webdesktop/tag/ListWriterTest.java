package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.list.writer.ListWriter;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;

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
public class ListWriterTest {

    static final String TITLE_TEMPLATE = "<h2>{0}</h2><br>";

    static final String LIST_TEMPLATE = "<div class=\"list-group list-group-flush\">\n" +
            " <span class=\"list-group-item\">02/04 13</span>\n" +
            " <span class=\"list-group-item\">02/04 14</span>\n" +
            " <span class=\"list-group-item\">02/04 15</span>\n" +
            "</div>\n";

    Grid<DataTable<?>> grid;

    @Before
    public void init() {
        grid = new Grid<>("Prova");
        grid.setTitle("Titolo");
        grid.addChild(new Text<String>("Ora", "Ora", DataTypes.STRING));

        DataTable<?> table = new Table();
        DataRow row = table.add();
        row.setString("Ora", "02/04 13");

        row = table.add();
        row.setString("Ora", "02/04 14");

        row = table.add();
        row.setString("Ora", "02/04 15");

        grid.setDataSource(table);
    }

    @Test
    public void writeTitleTest() {
        assertEquals(MessageFormat.format(TITLE_TEMPLATE, "Titolo"), ListWriter.writeTitle(grid));

        grid.setTitle(null);
        assertEquals(StringUtil.EMPTY, ListWriter.writeTitle(grid));
    }

    @Test
    public void writeListTest() throws FrameworkException {
        assertEquals(LIST_TEMPLATE, ListWriter.writeList(grid));
    }


}

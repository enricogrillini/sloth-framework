package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.tag.form.list.writer.ListWriter;


public class ListWriterTest {

    static final String TITLE_TEMPLATE = "<h1>{0}</h1><br>";

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
        grid.addChild(new Text<String>("Ora", "Ora", null, DataTypes.STRING));

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
    public void writeListTest() throws CloneNotSupportedException, ParseException, BusinessException {
        assertEquals(LIST_TEMPLATE, ListWriter.writeList(grid));
    }


}

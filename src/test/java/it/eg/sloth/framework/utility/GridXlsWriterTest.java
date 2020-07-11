package it.eg.sloth.framework.utility;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.xlsx.GridXlsxWriter;
import it.eg.sloth.webdesktop.tag.form.grid.writer.GridWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;

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
public class GridXlsWriterTest {

    Table table;
    Grid<Table> grid;

    @Before
    public void init() {
        table = new Table();
        Row row = table.add();
        row.setString("campo1", "valore1");
        row.setString("campo2", "A");
        row.setBigDecimal("campo3", BigDecimal.valueOf(3));

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setBigDecimal("campo3", BigDecimal.valueOf(3));

        grid = new Grid<>("provaGrid");
        grid.setTitle("Prova Grid");
        grid.setDescription("Prova sottotitolo");
        grid.addChild(new Text<String>("campo1", "campo 1", DataTypes.STRING));
        grid.addChild(new Text<String>("campo2", "campo 2", DataTypes.STRING));
        grid.addChild(new TextTotalizer("campo3", "campo 3", DataTypes.CURRENCY));
        grid.setDataSource(table);
    }

    @Test
    public void gridDetailTest() throws FrameworkException, IOException {
        GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
//        try (OutputStream outputStream = new FileOutputStream("C:/work/prova.xlsx")) {
            gridXlsxWriter.getWorkbook().write(outputStream);
        }
    }

}

package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.TestUtil;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

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
public class GridXlsWriterTest {

    private static final String GRID = TestUtil.OUTPUT_DIR + "/GridXlsWriter.xlsx";

    Table table;
    Grid<Table> grid;

    @Before
    public void init() throws IOException {
        TestUtil.createOutputDir();

        table = new Table();
        Row row = table.add();
        row.setString("campo1", "valore1");
        row.setString("campo2", "A");
        row.setBigDecimal("campo3", BigDecimal.valueOf(3.333));
        row.setString("campo4", "U");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setBigDecimal("campo3", BigDecimal.valueOf(-3.444));
        row.setString("campo4", "B");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setBigDecimal("campo3", BigDecimal.valueOf(-1));
        row.setString("campo4", "A");

        grid = new Grid<>("provaGrid");
        grid.setTitle("Prova Grid");
        grid.setDescription("Prova sottotitolo");
//        grid.addChild(new Text<String>("campo1", "campo 1", DataTypes.STRING));
//        grid.addChild(new Text<String>("campo2", "campo 2", DataTypes.STRING));
//        grid.addChild(new TextTotalizer("campo3", "campo 3", DataTypes.CURRENCY));
        grid.addChild(new ComboBox<String>("campo4", "campo 4", DataTypes.STRING));

        ComboBox<String> comboBox = (ComboBox<String>) grid.getElement("campo4");
        comboBox.setDecodeMap(new StringDecodeMap("A,Ancora;B,Basta;U,Un po'"));

        grid.setDataSource(table);
    }

    @Test
    public void gridWriterTest() throws FrameworkException, IOException {
        GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            gridXlsxWriter.getWorkbook().write(outputStream);

            assertTrue(outputStream.toByteArray().length > 0);
        }

        try (OutputStream outputStream = new FileOutputStream(GRID)) {
            gridXlsxWriter.getWorkbook().write(outputStream);
        }
    }

}

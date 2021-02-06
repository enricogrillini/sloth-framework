package it.eg.sloth.framework.utility.xlsx;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import it.eg.sloth.TestUtil;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.form.pivot.Pivot;
import it.eg.sloth.form.pivot.PivotRow;
import it.eg.sloth.form.pivot.PivotValue;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.jaxb.form.ConsolidateFunction;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotField;

import java.io.*;
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
public class GridXlsPivotWriterTest {

    private static final String GRID = TestUtil.OUTPUT_DIR + "/GridXlsPivotWriter.xlsx";

    Table table;
    Grid<Table> grid;

    @Before
    public void init() throws IOException {
        TestUtil.createOutputDir();

        table = new Table();
        Row row = table.add();
        row.setString("campo1", "Aaaa");
        row.setString("campo2", "1111");
        row.setBigDecimal("campo3", BigDecimal.valueOf(10));
        row.setBigDecimal("campo4", BigDecimal.valueOf(100));

        row = table.add();
        row.setString("campo1", "Aaaa");
        row.setString("campo2", "2222");
        row.setBigDecimal("campo3", BigDecimal.valueOf(20));
        row.setBigDecimal("campo4", BigDecimal.valueOf(200));

        row = table.add();
        row.setString("campo1", "Bbbb");
        row.setString("campo2", "1111");
        row.setBigDecimal("campo3", BigDecimal.valueOf(30));
        row.setBigDecimal("campo4", BigDecimal.valueOf(300));

        grid = new Grid<>("provaGrid");
        grid.setTitle("Prova Grid");
        grid.setDescription("Prova sottotitolo");

        grid.addChild(new Input<>("campo1", "Colonna1", DataTypes.STRING));
        grid.addChild(new Input<>("campo2", "Colonna2", DataTypes.STRING));
        grid.addChild(new Input<>("campo3", "valore1", DataTypes.DECIMAL));
        grid.addChild(new Input<>("campo4", "valore2", DataTypes.CURRENCY));

        Pivot pivot = new Pivot("Pivot");
        pivot.setTitle("Pivot di Prova");
        pivot.addChild(PivotRow.builder().name("campo1").description("piv campo 1").build());
        pivot.addChild(PivotRow.builder().name("campo2").description("piv campo 2").build());
        pivot.addChild(PivotValue.builder().name("campo3").description("piv val 1").consolidateFunction(ConsolidateFunction.SUM).build());
        pivot.addChild(PivotValue.builder().name("campo4").description("piv val 2").consolidateFunction(ConsolidateFunction.SUM).build());
        pivot.addChild(PivotValue.builder().name("campo5").fieldAlias("campo3").description("piv val 3").consolidateFunction(ConsolidateFunction.AVERAGE).build());
        pivot.addChild(PivotValue.builder().name("campo6").fieldAlias("campo4").description("piv val 4").consolidateFunction(ConsolidateFunction.AVERAGE).build());
        grid.addPivot(pivot);

        pivot.getElements().size();

        grid.setDataSource(table);
    }

    @Test
    public void gridPivotWriterTest() throws IOException, FrameworkException {
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



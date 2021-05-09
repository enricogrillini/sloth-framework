package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.form.pivot.Pivot;
import it.eg.sloth.form.pivot.PivotRow;
import it.eg.sloth.form.pivot.PivotValue;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.jaxb.form.ConsolidateFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
class GridXlsPivotWriterTest {

    private static final String GRID = TestFactory.OUTPUT_DIR + "/GridXlsPivotWriter.xlsx";

    Grid<Table> grid;

    @BeforeEach
    void init() throws IOException {
        TestFactory.createOutputDir();
        grid = TestFactory.getGrid();

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
    }

    @Test
    void gridPivotWriterTest() throws IOException, FrameworkException {
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



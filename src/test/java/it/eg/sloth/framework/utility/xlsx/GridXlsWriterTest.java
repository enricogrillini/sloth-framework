package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
class GridXlsWriterTest {

    private static final String GRID = TestFactory.OUTPUT_DIR + "/GridXlsWriter.xlsx";

    Grid<Table> grid;

    @BeforeEach
    void init() throws IOException, FrameworkException {
        TestFactory.createOutputDir();

        grid = TestFactory.getGrid();
    }

    @Test
    void gridWriterTest() throws FrameworkException, IOException {
        GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            gridXlsxWriter.getWorkbook().write(outputStream);

            Assertions.assertTrue(outputStream.toByteArray().length > 0);
        }

        try (OutputStream outputStream = new FileOutputStream(GRID)) {
            gridXlsxWriter.getWorkbook().write(outputStream);
        }
    }

}



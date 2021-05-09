package it.eg.sloth.framework.utility.csv;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
class GridCsvWriterTest {

    private static final String SIMPLE_CSV = TestFactory.OUTPUT_DIR + "/BaseCsvWriter.csv";

    Grid<Table> grid;

    @BeforeEach
    void init() throws IOException {
        TestFactory.createOutputDir();

        grid = TestFactory.getGrid();
    }

    @Test
    void writeTest() throws IOException, FrameworkException {
        GridCsvWiter gridCsvWiter = new GridCsvWiter(grid);
        try (OutputStream outputStream = new FileOutputStream(SIMPLE_CSV)) {
            gridCsvWiter.write(outputStream);
        }
    }

}

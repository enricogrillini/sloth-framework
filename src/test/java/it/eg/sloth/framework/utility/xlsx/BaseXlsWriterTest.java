package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.TestUtil;
import it.eg.sloth.framework.utility.report.SimpleReport;
import it.eg.sloth.framework.utility.xlsx.BaseXlsxWriter;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelContainer;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

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
public class BaseXlsWriterTest {


    private static final String SIMPLE_REPORT = TestUtil.OUTPUT_DIR + "/SimpleReport.xlsx";
    private static final String STYLE_REPORT = TestUtil.OUTPUT_DIR + "/StyleReport.xlsx";

    @BeforeClass
    public static void initClass() throws IOException {
        TestUtil.createOutputDir();
    }

    @Test
    public void styleReportTest() throws IOException {
        // Creo il report Excel
        try (OutputStream outputStream = new FileOutputStream(STYLE_REPORT); BaseXlsxWriter baseXlsxWriter = new SimpleReport()) {

            baseXlsxWriter.addSheet("Prova", true);

            int row = 0;
            baseXlsxWriter.setColumnsWidth(0, 0, 5000);

            // TITLE
            baseXlsxWriter.setCellStyle(row, 0, BaseExcelContainer.WHITE_BORDERED, BaseExcelFont.TITLE, null, HorizontalAlignment.LEFT);
            baseXlsxWriter.setCellValue(row, 0, "TITLE");
            row++;

            // SUB_TITLE
            baseXlsxWriter.setCellStyle(row, 0, BaseExcelContainer.WHITE_BORDERED, BaseExcelFont.SUB_TITLE, null, HorizontalAlignment.LEFT);
            baseXlsxWriter.setCellValue(row, 0, "SUB_TITLE");
            row++;

            // COMMENT
            baseXlsxWriter.setCellStyle(row, 0, BaseExcelContainer.WHITE_BORDERED, BaseExcelFont.COMMENT, null, HorizontalAlignment.LEFT);
            baseXlsxWriter.setCellValue(row, 0, "COMMENT");
            row++;

            // HEADER
            baseXlsxWriter.setCellStyle(row, 0, BaseExcelContainer.DARK_BLUE_BORDERED, BaseExcelFont.HEADER, null, HorizontalAlignment.LEFT);
            baseXlsxWriter.setCellValue(row, 0, "HEADER");
            row++;

            // HIGHIGHTED
            baseXlsxWriter.setCellStyle(row, 0, BaseExcelContainer.WHITE_BORDERED, BaseExcelFont.HIGHIGHTED, null, HorizontalAlignment.LEFT);
            baseXlsxWriter.setCellValue(row, 0, "HIGHIGHTED");
            row++;

            // NO FONT
            baseXlsxWriter.setCellStyle(row, 0, BaseExcelContainer.WHITE_BORDERED, null, null, HorizontalAlignment.LEFT);
            baseXlsxWriter.setCellValue(row, 0, "NO FONT");
            row++;

            baseXlsxWriter.setColumnsWidth(0,0,10000);
            baseXlsxWriter.autoSizeColumns(0,0);

            baseXlsxWriter.getWorkbook().write(outputStream);
        }

        // Verifico che  il report Excel sia corretto
        try (InputStream inp = new FileInputStream(STYLE_REPORT); Workbook wb = WorkbookFactory.create(inp);) {

            Sheet sheet = wb.getSheetAt(0);

            Row row = sheet.getRow(0);
            assertNotNull(row);

            Cell cell = row.getCell(0);
            assertEquals("TITLE", cell.getStringCellValue());
        }
    }

    @Test
    public void simpleReportTest() throws IOException {
        // Creo il report Excel
        try (OutputStream outputStream = new FileOutputStream(SIMPLE_REPORT);
             SimpleReport simpleReport = new SimpleReport()) {

            simpleReport.addData();
            simpleReport.getWorkbook().write(outputStream);
        }


        // Verifico che  il report Excel sia corretto
        try (InputStream inp = new FileInputStream(SIMPLE_REPORT);
             Workbook wb = WorkbookFactory.create(inp);) {

            Sheet sheet = wb.getSheetAt(0);

            Row row = sheet.getRow(0);
            assertNotNull(row);

            Cell cell = row.getCell(0);
            assertEquals("Titolo 1", cell.getStringCellValue());
        }
    }

}

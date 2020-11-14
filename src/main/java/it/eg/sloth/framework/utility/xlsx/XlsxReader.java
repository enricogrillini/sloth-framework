package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

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
 * <p>
 *
 * @author Enrico Grillini
 */
public class XlsxReader {
    private XlsxReader() {
        // NOP
    }

    private static String[] getNames(Row headerRow, int minCell, int maxCell) {
        String[] result = new String[maxCell - minCell];

        for (int i = 0; i < result.length; i++) {
            int j = (short) (minCell + i);

            if (headerRow.getCell(j) == null)
                result[i] = null;

            if (headerRow.getCell(j).getCellType() != CellType.STRING)
                result[i] = null;

            String nome = headerRow.getCell(j).getRichStringCellValue().getString();
            result[i] = "";
            for (int k = 0; k < nome.length(); k++) {
                char c = nome.charAt(k);
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                    result[i] += c;
                }
            }
        }

        return result;
    }

    public static final <R extends DataRow> DataTable<R> getDataTable(Sheet sheet, int rowHeader, int minRow, int maxRow, int minCell, int maxCell) {
        DataTable<R> table = (DataTable<R>) new Table();

        String[] names = getNames(sheet.getRow(rowHeader), minCell, maxCell);
        for (int i = minRow; i <= maxRow; i++) {

            Row row = sheet.getRow(i);
            if (row == null)
                continue;

            DataRow dataRow = table.add();
            for (int j = (short) minCell; j < (short) maxCell; j++) {
                if (names[j - minCell] == null)
                    continue;

                String nome = names[j - minCell];
                dataRow.setObject(nome, ExcelUtil.getCellValue(row.getCell(j)));
            }
        }

        return table;
    }

    public static final <R extends DataRow> DataTable<R> getDataTable(Sheet sheet) {
        return getDataTable(sheet, 0, 1, sheet.getLastRowNum(), 0, sheet.getRow(0).getLastCellNum());
    }

    public static final <R extends DataRow> DataTable<R> getDataTable(InputStream inputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            return getDataTable(workbook.getSheetAt(0));
        }
    }
}

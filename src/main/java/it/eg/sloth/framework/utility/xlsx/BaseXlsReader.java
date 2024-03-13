package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

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
 * <p>
 *
 * @author Enrico Grillini
 */
public class BaseXlsReader {

    private Workbook workbook;
    private Sheet sheet;

    private int rowHeader;
    private int minRow;
    private int maxRow;
    private int minCell;
    private int maxCell;

    public BaseXlsReader(InputStream inputStream) throws IOException {
        workbook = WorkbookFactory.create(inputStream);
        sheet = workbook.getSheetAt(0);

        initDataRange(0, 1, sheet.getLastRowNum(), 0, sheet.getRow(0).getLastCellNum());
    }


    public BaseXlsReader(InputStream inputStream, int rowHeader, int minRow) throws IOException {
        workbook = WorkbookFactory.create(inputStream);
        sheet = workbook.getSheetAt(0);

        initDataRange(rowHeader, minRow, sheet.getLastRowNum(), 0, sheet.getRow(rowHeader).getLastCellNum());
    }

    public BaseXlsReader(InputStream inputStream, int rowHeader, int minRow, int maxRow, int minCell, int maxCell) throws IOException {
        workbook = WorkbookFactory.create(inputStream);
        sheet = workbook.getSheetAt(0);

        initDataRange(rowHeader, minRow, maxRow, minCell, maxCell);
    }

    private void initDataRange(int rowHeader, int minRow, int maxRow, int minCell, int maxCell) {
        this.rowHeader = rowHeader;
        this.minRow = minRow;
        this.maxRow = maxRow;
        this.minCell = minCell;
        this.maxCell = maxCell;
    }

    public String[] getHeaders() {
        String[] result = new String[maxCell - minCell];
        for (int i = 0; i < result.length; i++) {
            int j = minCell + i;

            Cell cell = ExcelUtil.getCell(sheet, rowHeader, j);
            Object cellValue = ExcelUtil.getCellValue(cell);
            if (cellValue == null) {
                result[i] = "Header_" + j;
            } else {
                result[i] = cellValue.toString().trim();
            }
        }

        return result;
    }

    public MessageList checkHeaders(String... requiredColumnNames) {
        MessageList messageList = new MessageList();

        int i = 0;
        String[] excelColumnNames = getHeaders();
        for (String requiredColumnName : requiredColumnNames) {
            if (i >= excelColumnNames.length) {
                messageList.addBaseError(MessageFormat.format("Colonna {0} [{1}] non trovata", i + 1, requiredColumnName));
            } else if (!requiredColumnName.trim().equalsIgnoreCase(excelColumnNames[i].trim())) {
                messageList.addBaseError(MessageFormat.format("Nome colonna {0} errato: atteso [{1}] trovato [{2}]", i + 1, requiredColumnName, excelColumnNames[i]));
            }

            i++;
        }

        return messageList;
    }

    public <R extends DataRow> DataTable<R> getDataTable() throws FrameworkException {
        DataTable<R> table = (DataTable<R>) new Table();

        String[] names = getHeaders();
        for (int i = minRow; i <= maxRow; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            DataRow dataRow = table.add();
            for (int j = (short) minCell; j < (short) maxCell; j++) {
                if (names[j - minCell] == null) {
                    continue;
                }

                String nome = names[j - minCell];
                try {
                    dataRow.setObject(nome, ExcelUtil.getCellValue(row.getCell(j)));
                } catch (Exception e) {
                    throw new FrameworkException(ExceptionCode.GENERIC_BUSINESS_ERROR, MessageFormat.format("Errore lettura cella ({0}, {1}): {2}", i, j, e.getMessage()));
                }
            }
        }

        return table;
    }
}

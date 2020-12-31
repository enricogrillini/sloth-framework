package it.eg.sloth.framework.utility.report;

import it.eg.sloth.framework.utility.xlsx.BaseXlsxWriter;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelContainer;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelFont;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class SimpleReport extends BaseXlsxWriter {

    public void addData() {
        addSheet("Prova", true);

        int row = 0;

        setColumnsWidth(0, 0, 1000);
        setColumnsWidth(1, 1, 5000);
        setColumnsWidth(2, 2, 25000);


        // Titolo 1
        setCellValue(row, 0, "Titolo 1");
        setRangeStyle(row, 0, row, 2, getStyle(BaseExcelContainer.WHITE_BORDERED_BOTTOM, BaseExcelFont.TITLE, null, HorizontalAlignment.LEFT));
        addMergedRegion(row, 0, row, 2);
        row++;

        // Data - Livello 1
        setCellStyle(row, 1, BaseExcelContainer.WHITE_BORDERED, BaseExcelFont.HIGHIGHTED, null, HorizontalAlignment.LEFT);
        setCellValue(row, 1, "Data 1");

        setCellStyle(row, 2, BaseExcelContainer.WHITE_BORDERED, null, null, HorizontalAlignment.LEFT);
        setCellValue(row, 2, "Value 1");
        row++;

        row++;

        // Titolo 2
        setCellValue(row, 1, "Titolo 2");
        setRangeStyle(row, 1, row, 2, getStyle(BaseExcelContainer.WHITE_BORDERED_BOTTOM, BaseExcelFont.SUB_TITLE, null, HorizontalAlignment.LEFT));
        addMergedRegion(row, 1, row, 2);

        row++;

        // Data - Livello 2
        setCellStyle(row, 1, BaseExcelContainer.WHITE_BORDERED, BaseExcelFont.HIGHIGHTED, null, HorizontalAlignment.LEFT);
        setCellValue(row, 1, "Data A");
        setCellStyle(row, 2, BaseExcelContainer.WHITE_BORDERED, null, null, HorizontalAlignment.LEFT);
        setCellValue(row, 2, "Value A");
        row++;

        // Data - Livello 2
        setCellStyle(row, 1, BaseExcelContainer.WHITE_BORDERED, BaseExcelFont.HIGHIGHTED, null, HorizontalAlignment.LEFT);
        setCellValue(row, 1, "Data B");
        setCellStyle(row, 2, BaseExcelContainer.WHITE_BORDERED, null, null, HorizontalAlignment.LEFT);
        setCellValue(row, 2, "Value B");
        row++;

        row++;
    }

}

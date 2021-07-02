package it.eg.sloth.framework.utility.xlsx;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataFields;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CreatePivotTableSimple {

    private static void setFormatPivotField(XSSFPivotTable pivotTable,
                                            long fieldIndex,
                                            Integer numFmtId) {
        Optional.ofNullable(pivotTable
                .getCTPivotTableDefinition()
                .getPivotFields())
                .map(pivotFields -> pivotFields
                        .getPivotFieldArray((int) fieldIndex))
                .ifPresent(pivotField -> pivotField
                        .setNumFmtId(numFmtId));
    }

    private static void setFormatDataField(XSSFPivotTable pivotTable, long fieldIndex, long numFmtId) {
        Optional.ofNullable(pivotTable
                .getCTPivotTableDefinition()
                .getDataFields())
                .map(CTDataFields::getDataFieldList)
                .map(List::stream)
                .ifPresent(stream -> stream
                        .filter(dataField -> dataField.getFld() == fieldIndex)
                        .findFirst()
                        .ifPresent(dataField -> dataField.setNumFmtId(numFmtId)));
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        //Create some data to build the pivot table on
        setCellData(sheet);

        XSSFPivotTable pivotTable = sheet.createPivotTable(
                new AreaReference("A1:C6", SpreadsheetVersion.EXCEL2007),
                new CellReference("E3"));


        DataFormat df = wb.createDataFormat();


        pivotTable.addRowLabel(1); // set second column as 1-th level of rows
        setFormatPivotField(pivotTable, 1, (int) df.getFormat("#,##0.00")); //set format of row field numFmtId=9 0%
        pivotTable.addRowLabel(0); // set first column as 2-th level of rows
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 2); // Sum up the second column
        setFormatDataField(pivotTable, 2, 3); //set format of value field numFmtId=3 # ##0


        FileOutputStream fileOut = new FileOutputStream("stackoverflow-pivottable.xlsx");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }

    private static void setCellData(XSSFSheet sheet) {

        String[] names = {"Jane", "Tarzan", "Terk", "Kate", "Dmitry"};
        Double[] percents = {0.25, 0.5, 0.75, 0.25, 0.5};
        Integer[] balances = {107634, 554234, 10234, 22350, 15234};

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Percents");
        row.createCell(2).setCellValue("Balance");

        for (int i = 0; i < names.length; i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(names[i]);
            row.createCell(1).setCellValue(percents[i]);
            row.createCell(2).setCellValue(balances[i]);
        }
    }
}
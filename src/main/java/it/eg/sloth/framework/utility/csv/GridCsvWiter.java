package it.eg.sloth.framework.utility.csv;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GridCsvWiter {

    private Grid<?> grid;
    private Character delimiter;

    public GridCsvWiter(Grid<?> grid) {
        this(grid, null);
    }

    public GridCsvWiter(Grid<?> grid, Character delimiter) {
        this.grid = grid;
        this.delimiter = delimiter;
    }

    private CSVFormat getCSVFormat() {
        List<String> header = new ArrayList<>();
        for (SimpleField simpleField : grid) {
            if (simpleField instanceof DataField) {
                header.add(simpleField.getDescription());
            }
        }

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(header.toArray(new String[0]));

        if (delimiter != null) {
            csvFormat = csvFormat.withDelimiter(delimiter);
        }

        return csvFormat;
    }

    private void printData(Writer writer, CSVFormat csvFormat) throws IOException, FrameworkException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

        for (DataRow row : grid.getDataSource()) {
            List<String> list = new ArrayList<>();
            for (SimpleField simpleField : grid) {
                if (simpleField instanceof DataField) {
                    DataField<?> field = (DataField<?>) simpleField.newInstance();
                    field.copyFromDataSource(row);

                    list.add(field.getData());
                }
            }

            csvPrinter.printRecord(list.toArray());
        }

        csvPrinter.flush();
    }

    public void write(OutputStream outputStream) throws IOException, FrameworkException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            CSVFormat csvFormat = getCSVFormat();

            printData(writer, csvFormat);
        }
    }
}

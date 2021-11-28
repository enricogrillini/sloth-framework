package it.eg.sloth.framework.utility.csv;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.common.message.MessageList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;

public class BaseCsvReader {

    private CSVParser csvParser;

    public BaseCsvReader(InputStream inputStream) throws IOException {
        CSVFormat csvFormat = CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withDelimiter(';');
        csvParser = CSVParser.parse(inputStream, StandardCharsets.UTF_8, csvFormat);
    }

    public String[] getHeaders() {
        return csvParser.getHeaderNames().toArray(new String[0]);
    }

    public MessageList checkHeaders(String... requiredColumnNames) {
        MessageList messageList = new MessageList();

        int i = 0;
        String[] csvColumnNames = getHeaders();
        for (String requiredColumnName : requiredColumnNames) {
            if (i >= csvColumnNames.length) {
                messageList.addBaseError(MessageFormat.format("Colonna {0} [{1}] non trovata", i + 1, requiredColumnName));
            } else if (!requiredColumnName.equalsIgnoreCase(csvColumnNames[i].trim())) {
                messageList.addBaseError(MessageFormat.format("Nome colonna {0} errato: atteso [{1}] trovato [{2}]", i + 1, requiredColumnName, csvColumnNames[i]));
            }

            i++;
        }

        return messageList;
    }

    public <R extends DataRow> DataTable<R> getDataTable() throws IOException {
        DataTable<R> table = (DataTable<R>) new Table();

        String[] names = getHeaders();
        List<CSVRecord> records = csvParser.getRecords();
        for (int i = 0; i < records.size(); i++) {
            CSVRecord row = records.get(i);

            DataRow dataRow = table.add();
            for (String name : names) {
                dataRow.setString(name, row.get(name));
            }
        }

        return table;
    }

}

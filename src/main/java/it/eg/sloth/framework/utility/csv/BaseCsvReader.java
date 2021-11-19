package it.eg.sloth.framework.utility.csv;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;

public class BaseCsvReader {

  private CSVParser csvParser;

  public BaseCsvReader(InputStream inputStream) throws IOException {
    CSVFormat csvFormat = CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withDelimiter(';');
    csvParser = CSVParser.parse(inputStream, StandardCharsets.UTF_8, csvFormat);
  }

  public String[] getHeaders() throws IOException {
    List<String> rowHeaders = csvParser.getHeaderNames();

    String[] headerNames = new String[rowHeaders.size()];

    for (int i = 0; i < headerNames.length; i++) {
      headerNames[i] = rowHeaders.get(i);
    }

    return headerNames;
  }

  public MessageList checkHeaders(String... requiredColumnNames) throws IOException {
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

  public <R extends DataRow> DataTable<R> getDataTable() throws FrameworkException, IOException {
    DataTable<R> table = (DataTable<R>) new Table();

    String[] names = getHeaders();

    List<CSVRecord> records = csvParser.getRecords();

    for (int i = 0; i < records.size(); i++) {
      CSVRecord row = records.get(i);

      DataRow dataRow = table.add();
      for (int j = 0; j < row.size(); j++) {
        if (names[0] == null) {
          continue;
        }

        String nome = names[j];
        Object value = row.get(j);

        try {
          if (value instanceof String) {
            dataRow.setString(nome, (String) value);
          } else if (value instanceof BigDecimal) {
            dataRow.setBigDecimal(nome, (BigDecimal) value);
          } else if (value instanceof Timestamp) {
            dataRow.setTimestamp(nome, (Timestamp) value);
          }
        } catch (Exception e) {
          throw new FrameworkException(ExceptionCode.GENERIC_BUSINESS_ERROR, MessageFormat.format("Errore lettura cella ({0}, {1}): {2}", i, j, e.getMessage()));
        }
      }

    }

    return table;
  }

}

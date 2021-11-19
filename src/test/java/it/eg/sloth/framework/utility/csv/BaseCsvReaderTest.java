package it.eg.sloth.framework.utility.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;

public class BaseCsvReaderTest {

  private static final String CSV_FILE = "csv/TestReader.csv";

  @Test
  void csvReaderTest() throws IOException, FrameworkException {
    ClassPathResource classPathResource = new ClassPathResource(CSV_FILE);
    try (InputStream inputStream = classPathResource.getInputStream()) {
      BaseCsvReader baseCsvReader = new BaseCsvReader(inputStream);

      testReader(baseCsvReader);
    }
  }

  private void testReader(BaseCsvReader baseCsvReader) throws FrameworkException, IOException {
    MessageList messageList = baseCsvReader.checkHeaders("Codice", "Descrizione", "Data", "Currency");
    assertTrue(messageList.isEmpty());

    messageList = baseCsvReader.checkHeaders("Errore", "Descrizione", "Data", "Currency");
    assertFalse(messageList.isEmpty());
    assertEquals("Nome colonna 1 errato: atteso [Errore] trovato [Codice]", messageList.get(0).getDescription());

    messageList = baseCsvReader.checkHeaders("Codice", "Descrizione", "Data", "Currency", "Extra");
    assertFalse(messageList.isEmpty());
    assertEquals("Colonna 5 [Extra] non trovata", messageList.get(0).getDescription());
    
    DataTable<?> dataTable = baseCsvReader.getDataTable();
    
    assertEquals(3, dataTable.size());

    dataTable.setCurrentRow(0);
    assertEquals(1, dataTable.getRow().getBigDecimal("Codice").intValue());
    assertEquals("Prova 1", dataTable.getRow().getString("Descrizione"));
    assertEquals(BaseFunction.trunc(TimeStampUtil.parseTimestamp("01/01/2020", "dd/mm/yyyy")), dataTable.getRow().getTimestamp("Data"));
    assertEquals(BigDecimal.valueOf(1000.0), dataTable.getRow().getBigDecimal("Currency"));
  }

}

package it.eg.sloth.framework.utility.csv;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class BaseCsvReaderTest {

    private static final String CSV_FILE = "csv/TestReader.csv";
    private static final String CSV_FILE_TAB_SEPARATED = "csv/TestReaderTabSeparated.csv";

    @Test
    void csvReader() throws IOException, FrameworkException {
        ClassPathResource classPathResource = new ClassPathResource(CSV_FILE);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            BaseCsvReader baseCsvReader = new BaseCsvReader(inputStream);

            MessageList messageList = baseCsvReader.checkHeaders("Codice", "Descrizione", "Data", "Currency");
            assertTrue(messageList.isEmpty());

            DataTable<?> dataTable = baseCsvReader.getDataTable();
            assertEquals(3, dataTable.size());

            dataTable.setCurrentRow(0);
            DataRow row = dataTable.getRow();
            assertEquals("1", row.getString("Codice"));
            assertEquals("Prova 1", row.getString("Descrizione"));
            assertEquals("01/01/2020", row.getString("Data"));
            assertEquals(" 1.000,00 €", row.getString("Currency"));
        }
    }

    @Test
    void csvReader_checkHeaders_KO() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(CSV_FILE);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            BaseCsvReader baseCsvReader = new BaseCsvReader(inputStream);

            MessageList messageList = baseCsvReader.checkHeaders("Errore", "Descrizione", "Data", "Currency");
            assertFalse(messageList.isEmpty());
            assertEquals("Nome colonna 1 errato: atteso [Errore] trovato [Codice]", messageList.get(0).getDescription());

            messageList = baseCsvReader.checkHeaders("Codice", "Descrizione", "Data", "Currency", "Extra");
            assertFalse(messageList.isEmpty());
            assertEquals("Colonna 5 [Extra] non trovata", messageList.get(0).getDescription());
        }
    }

    @Test
    void csvReaderTabSeparated() throws IOException, FrameworkException {
        ClassPathResource classPathResource = new ClassPathResource(CSV_FILE_TAB_SEPARATED);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            BaseCsvReader baseCsvReader = new BaseCsvReader(inputStream, '\t');

            MessageList messageList = baseCsvReader.checkHeaders("Codice", "Ragione sociale", "Indirizzo", "Comune", "Partita IVA", "Codice fiscale", "Codice SdI", "Dt ult.fatt", "Cond. pag.");
            assertTrue(messageList.isEmpty());

            DataTable<?> dataTable = baseCsvReader.getDataTable();
            assertEquals(3, dataTable.size());

            dataTable.setCurrentRow(0);
            DataRow row = dataTable.getRow();
            assertEquals("111", row.getString("Codice"));
            assertEquals("AAA", row.getString("Ragione sociale"));
            assertEquals("CORSO REGINA MARGHERITA 76 TORINO", row.getString("Indirizzo"));
            assertEquals("TORINO", row.getString("Comune"));
            assertEquals("12345789012", row.getString("Partita IVA"));
            assertEquals(null, row.getString("Codice fiscale"));
            assertEquals(null, row.getString("Codice SdI"));
            assertEquals(null, row.getString("Dt ult.fatt"));
            assertEquals(null, row.getString("Cond. pag."));
        }
    }

}

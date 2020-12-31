package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.utility.xlsx.BaseXlsReader;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

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
public class BaseXlsReaderTest {

    private static final String XLS_FILE = "excel/TestReader.xls";
    private static final String XLSX_FILE = "excel/TestReader.xlsx";

    @Test
    public void xlsReaderTest() throws IOException, FrameworkException {
        ClassPathResource classPathResource = new ClassPathResource(XLS_FILE);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            BaseXlsReader baseXlsReader = new BaseXlsReader(inputStream);

            testReader(baseXlsReader);
        }
    }

    @Test
    public void xlsxReaderTest() throws IOException, FrameworkException {
        ClassPathResource classPathResource = new ClassPathResource(XLSX_FILE);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            BaseXlsReader baseXlsReader = new BaseXlsReader(inputStream);

            testReader(baseXlsReader);
        }
    }

    private void testReader(BaseXlsReader baseXlsReader) throws FrameworkException {
        MessageList messageList = baseXlsReader.checkHeaders("Codice", "Descrizione", "Data", "Currency");
        assertTrue(messageList.isEmpty());

        messageList = baseXlsReader.checkHeaders("Errore", "Descrizione", "Data", "Currency");
        assertFalse(messageList.isEmpty());
        assertEquals("Nome colonna 1 errato: atteso [Errore] trovato [Codice]", messageList.get(0).getDescription());

        messageList = baseXlsReader.checkHeaders("Codice", "Descrizione", "Data", "Currency", "Extra");
        assertFalse(messageList.isEmpty());
        assertEquals("Colonna 5 [Extra] non trovata", messageList.get(0).getDescription());

        DataTable<?> dataTable = baseXlsReader.getDataTable();
        assertEquals(3, dataTable.size());

        dataTable.setCurrentRow(0);
        assertEquals(1, dataTable.getRow().getBigDecimal("Codice").intValue());
        assertEquals("Prova 1", dataTable.getRow().getString("Descrizione"));
        assertEquals(BaseFunction.trunc(TimeStampUtil.parseTimestamp("01/01/2020", "dd/mm/yyyy")), dataTable.getRow().getTimestamp("Data"));
        assertEquals(BigDecimal.valueOf(1000.0), dataTable.getRow().getBigDecimal("Currency"));
    }
}

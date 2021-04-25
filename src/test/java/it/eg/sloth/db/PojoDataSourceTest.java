package it.eg.sloth.db;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.model.SamplePojoRow;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PojoDataSourceTest {

    @Test
    void pojoDataSourceTest1() throws FrameworkException {
        // Inizializzo - Pojo mode
        SamplePojoRow samplePojoRow = new SamplePojoRow();
        samplePojoRow.setTesto("Prova");
        samplePojoRow.setNumero(BigDecimal.valueOf(0));
        samplePojoRow.setData(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        samplePojoRow.setBlob("BlobData".getBytes());

        // Verifico
        assertEquals(7, samplePojoRow.keys().size());
        assertEquals(7, samplePojoRow.values().size());
        assertEquals(samplePojoRow.getTesto(), samplePojoRow.getString("TESTO"));
        assertEquals("Prova", samplePojoRow.getString("TESTO"));
        assertEquals(samplePojoRow.getNumero(), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(BigDecimal.valueOf(0), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(samplePojoRow.getData(), samplePojoRow.getTimestamp("Data"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), samplePojoRow.getTimestamp("Data"));
        assertEquals("BlobData", new String(samplePojoRow.getBlob()));
    }

    @Test
    void pojoDataSourceTest2() throws FrameworkException {
        // Inizializzo - Datasource Mode
        SamplePojoRow samplePojoRow = new SamplePojoRow();
        samplePojoRow.setString("Testo", "Prova");
        samplePojoRow.setBigDecimal("Numero", BigDecimal.valueOf(0));
        samplePojoRow.setTimestamp("Data", TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        samplePojoRow.setByte("Blob", "BlobData".getBytes());

        // Verifico
        assertEquals(7, samplePojoRow.keys().size());
        assertEquals(7, samplePojoRow.values().size());
        assertEquals(samplePojoRow.getTesto(), samplePojoRow.getString("TESTO"));
        assertEquals("Prova", samplePojoRow.getString("TESTO"));
        assertEquals(samplePojoRow.getNumero(), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(BigDecimal.valueOf(0), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(samplePojoRow.getData(), samplePojoRow.getTimestamp("Data"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), samplePojoRow.getTimestamp("Data"));
        assertEquals("BlobData", new String(samplePojoRow.getBlob()));

        // Clear
        samplePojoRow.clear();
        assertEquals(7, samplePojoRow.keys().size());
        assertEquals(7, samplePojoRow.values().size());
        assertEquals(null, samplePojoRow.getString("TESTO"));
        assertEquals(null, samplePojoRow.getBigDecimal("Numero"));
        assertEquals(null, samplePojoRow.getTimestamp("Data"));
        assertEquals(null, samplePojoRow.getByte("Blob"));
        assertEquals(null, samplePojoRow.getTesto());
        assertEquals(null, samplePojoRow.getNumero());
        assertEquals(null, samplePojoRow.getData());
        assertEquals(null, samplePojoRow.getBlob());


        // Campi extra Pojo
        samplePojoRow.setObject("aaaaName", "aaaaValue");
        assertEquals(null, samplePojoRow.getObject("aaaaName"));
    }

    @Test
    void pojoDataSourceCopyTest() throws FrameworkException {
        SamplePojoRow samplePojoRow = new SamplePojoRow();
        samplePojoRow.setTesto("Prova");
        samplePojoRow.setNumero(BigDecimal.valueOf(0));
        samplePojoRow.setData(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        samplePojoRow.setBlob("BlobData".getBytes());

        Row row = new Row();
        row.copyFromDataSource(samplePojoRow);
        samplePojoRow.loadFromDataSource(row);

        // Verifico
        assertEquals(7, samplePojoRow.keys().size());
        assertEquals(7, samplePojoRow.values().size());
        assertEquals(samplePojoRow.getTesto(), samplePojoRow.getString("TESTO"));
        assertEquals("Prova", samplePojoRow.getString("TESTO"));
        assertEquals(samplePojoRow.getNumero(), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(BigDecimal.valueOf(0), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(samplePojoRow.getData(), samplePojoRow.getTimestamp("Data"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), samplePojoRow.getTimestamp("Data"));
        assertEquals("BlobData", new String(samplePojoRow.getBlob()));
    }
}

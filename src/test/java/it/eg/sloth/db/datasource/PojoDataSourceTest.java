package it.eg.sloth.db.datasource;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PojoDataSourceTest {

    @Test
    public void pojoDataSourceTest() throws FrameworkException {
        // Inizializzo
        SamplePojoRow samplePojoRow = new SamplePojoRow();
        samplePojoRow.setTesto("Prova");
        samplePojoRow.setNumero(BigDecimal.valueOf(0));
        samplePojoRow.setData(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));

        // Verifico
        assertEquals(3, samplePojoRow.values().size());
        assertEquals(samplePojoRow.getTesto(), samplePojoRow.getString("TESTO"));
        assertEquals("Prova", samplePojoRow.getString("TESTO"));
        assertEquals(samplePojoRow.getNumero(), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(BigDecimal.valueOf(0), samplePojoRow.getBigDecimal("Numero"));
        assertEquals(samplePojoRow.getData(), samplePojoRow.getTimestamp("Data"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), samplePojoRow.getTimestamp("Data"));

        // Clear
        samplePojoRow.clear();
        assertEquals(3, samplePojoRow.values().size());
        assertEquals(null, samplePojoRow.getString("TESTO"));
        assertEquals(null, samplePojoRow.getBigDecimal("Numero"));
        assertEquals(null, samplePojoRow.getTimestamp("Data"));
        assertEquals(null, samplePojoRow.getTesto());
        assertEquals(null, samplePojoRow.getNumero());
        assertEquals(null, samplePojoRow.getData());

    }
}

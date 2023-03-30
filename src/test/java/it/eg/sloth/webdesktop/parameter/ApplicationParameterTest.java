package it.eg.sloth.webdesktop.parameter;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.parameter.model.ApplicationParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationParameterTest {

    ApplicationParameter multivalueParameter;

    @BeforeEach
    void init() {
        multivalueParameter = ApplicationParameter.builder()
                .codParameter("PARAM_MULTIVALUE")
                .dataType(DataTypes.STRING)
                .multivalue(true)
                .value("aaa|bbb")
                .build();
    }

    @Test
    void multivalueParameterValue() throws FrameworkException {
        Set<Object> values = multivalueParameter.getParameterValues(Locale.ITALY);
        assertEquals(2, values.size());
        assertTrue(values.contains("aaa"));
        assertTrue(values.contains("bbb"));
    }

    @Test
    void multivalueParameterFormattedValue() throws FrameworkException {
        Set<String> stringValues = multivalueParameter.getParameterFormatedValues(Locale.ITALY);
        assertEquals(2, stringValues.size());
        assertTrue(stringValues.contains("aaa"));
        assertTrue(stringValues.contains("bbb"));
    }

    @Test
    void multivalueparameterFromRow() {
        Row row = new Row();
        row.setString("codParameter", "PARAM_MULTIVALUE");
        row.setString("dataType", "STRING");
        row.setString("flagMultivalue", "S");
        row.setString("value", "aaaa");

        ApplicationParameter applicationParameter = new ApplicationParameter();
        applicationParameter.fromRow(row);

        assertEquals("PARAM_MULTIVALUE", applicationParameter.getCodParameter());
        assertEquals(DataTypes.STRING, applicationParameter.getDataType());
        assertTrue(applicationParameter.isMultivalue());
        assertEquals("aaaa", applicationParameter.getValue());
    }
}

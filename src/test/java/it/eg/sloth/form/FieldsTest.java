package it.eg.sloth.form;

import it.eg.sloth.db.model.SamplePojoRow;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

public class FieldsTest {

    @Test
    public void fieldsInputTest() throws FrameworkException {
        // Fields
        Fields<SamplePojoRow> fields = new Fields<>("prova");
        Input<String> testo = new Input<String>("Testo", "Testo", DataTypes.STRING);
        Input<BigDecimal> numero = new Input<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        Input<Timestamp> data = new Input<Timestamp>("Data", "Data", DataTypes.DATE);

        fields.addChild(testo);
        fields.addChild(numero);
        fields.addChild(data);

        // DataSource
        SamplePojoRow samplePojoRow = new SamplePojoRow();
        samplePojoRow.setTesto("Prova testo");
        samplePojoRow.setNumero(BigDecimal.valueOf(10));
        samplePojoRow.setData(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));

        // DataSource - CopyFrom
        fields.copyFromDataSource(samplePojoRow);
        assertEquals(samplePojoRow.getTesto(), testo.getValue());
        assertEquals(samplePojoRow.getNumero(), numero.getValue());
        assertEquals(samplePojoRow.getData(), data.getValue());


        // DataSource - CopyTo
        samplePojoRow = new SamplePojoRow();
        fields.copyToDataSource(samplePojoRow);
        assertEquals(samplePojoRow.getTesto(), testo.getValue());
        assertEquals(samplePojoRow.getNumero(), numero.getValue());
        assertEquals(samplePojoRow.getData(), data.getValue());
    }

    @Test
    public void fieldsTextTest() throws FrameworkException {
        // Fields
        Fields<SamplePojoRow> fields = new Fields("prova");
        Text<String> testo = new Text<String>("Testo", "Testo", DataTypes.STRING);
        Text<BigDecimal> numero = new Text<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        Text<Timestamp> data = new Text<Timestamp>("Data", "Data", DataTypes.DATE);

        fields.addChild(testo);
        fields.addChild(numero);
        fields.addChild(data);

        // DataSource
        SamplePojoRow samplePojoRow = new SamplePojoRow();
        samplePojoRow.setTesto("Prova testo");
        samplePojoRow.setNumero(BigDecimal.valueOf(10));
        samplePojoRow.setData(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));

        // DataSource - CopyFrom
        fields.copyFromDataSource(samplePojoRow);
        assertEquals(samplePojoRow.getTesto(), testo.getValue());
        assertEquals(samplePojoRow.getNumero(), numero.getValue());
        assertEquals(samplePojoRow.getData(), data.getValue());

        // DataSource - CopyTo
        samplePojoRow = new SamplePojoRow();
        fields.copyToDataSource(samplePojoRow);
        assertEquals(samplePojoRow.getTesto(), testo.getValue());
        assertEquals(samplePojoRow.getNumero(), numero.getValue());
        assertEquals(samplePojoRow.getData(), data.getValue());
    }
}

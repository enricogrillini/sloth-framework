package it.eg.sloth.form;

import it.eg.sloth.db.model.SamplePojoRow;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
class FieldsTextTest {

    private static final String VALUE_DESCRIPTION = "Testo: Prova testo | Numero: 10 | Data: 01/01/2020";

    Fields<SamplePojoRow> fields;
    Text<String> testo;
    Text<BigDecimal> numero;
    Text<Timestamp> data;

    SamplePojoRow samplePojoRow;

    @BeforeEach
    void init() throws FrameworkException {
        // Fields
        fields = new Fields("prova");
        testo = new Text<String>("Testo", "Testo", DataTypes.STRING);
        numero = new Text<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        data = new Text<Timestamp>("Data", "Data", DataTypes.DATE);

        fields.addChild(testo);
        fields.addChild(numero);
        fields.addChild(data);
        fields.setLocale(Locale.ITALY);

        // DataSource
        samplePojoRow = new SamplePojoRow();
        samplePojoRow.setTesto("Prova testo");
        samplePojoRow.setNumero(BigDecimal.valueOf(10));
        samplePojoRow.setData(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
    }

    @Test
    void fieldsTextTest() throws FrameworkException {
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
    void valuesDescriptionTest() throws FrameworkException {
        assertEquals(StringUtil.EMPTY, fields.getValuesDescription());

        fields.copyFromDataSource(samplePojoRow);
        assertEquals(VALUE_DESCRIPTION, fields.getValuesDescription());
    }

}

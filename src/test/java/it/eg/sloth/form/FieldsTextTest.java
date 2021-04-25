package it.eg.sloth.form;

import it.eg.sloth.db.model.SamplePojoRow;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class FieldsTextTest {

    @Test
    void fieldsTextTest() throws FrameworkException {
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

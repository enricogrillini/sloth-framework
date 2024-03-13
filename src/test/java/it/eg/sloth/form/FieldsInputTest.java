package it.eg.sloth.form;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.db.model.SamplePojoRow;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.AutoComplete;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.api.model.BffFieldsProva;
import it.eg.sloth.webdesktop.api.response.BffFieldsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
class FieldsInputTest {

    Fields<SamplePojoRow> inputFields;

    AutoComplete<String> autoComplete;
    Input<String> testo;
    Input<BigDecimal> numero;
    Input<BigDecimal> decimal;
    Input<BigDecimal> currency;
    Input<Timestamp> data;

    @BeforeEach
    void init() {
        // Fields
        inputFields = new Fields<>("prova");
        autoComplete = new AutoComplete<>("AutoComplete", "AutoComplete", DataTypes.STRING);
        autoComplete.setDecodeMap(new StringDecodeMap("A,Ancora;B,Basta;T,Tutti"));

        testo = new Input<String>("Testo", "Testo", DataTypes.STRING);
        numero = new Input<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        decimal = new Input<BigDecimal>("Decimal", "Decimal", DataTypes.DECIMAL);
        currency = new Input<BigDecimal>("Currency", "Currency", DataTypes.CURRENCY);
        data = new Input<Timestamp>("Data", "Data", DataTypes.DATE);

        inputFields.addChild(autoComplete);
        inputFields.addChild(testo);
        inputFields.addChild(numero);
        inputFields.addChild(decimal);
        inputFields.addChild(currency);
        inputFields.addChild(data);

        inputFields.setLocale(Locale.ITALY);
    }

    @Test
    void fieldsInputTest() throws FrameworkException {
        // DataSource
        SamplePojoRow samplePojoRow = new SamplePojoRow();
        samplePojoRow.setAutocomplete("A");
        samplePojoRow.setTesto("Prova testo");
        samplePojoRow.setNumero(BigDecimal.valueOf(10));
        samplePojoRow.setDecimal(BigDecimal.valueOf(10));
        samplePojoRow.setCurrency(BigDecimal.valueOf(10));
        samplePojoRow.setData(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));

        // DataSource - CopyFrom
        inputFields.copyFromDataSource(samplePojoRow);
        assertEquals(samplePojoRow.getAutocomplete(), autoComplete.getValue());
        assertEquals(samplePojoRow.getTesto(), testo.getValue());
        assertEquals(samplePojoRow.getNumero(), numero.getValue());
        assertEquals(samplePojoRow.getDecimal(), decimal.getValue());
        assertEquals(samplePojoRow.getCurrency(), currency.getValue());
        assertEquals(samplePojoRow.getData(), data.getValue());

        // DataSource - CopyTo
        samplePojoRow = new SamplePojoRow();
        inputFields.copyToDataSource(samplePojoRow);
        assertEquals(samplePojoRow.getAutocomplete(), autoComplete.getValue());
        assertEquals(samplePojoRow.getTesto(), testo.getValue());
        assertEquals(samplePojoRow.getNumero(), numero.getValue());
        assertEquals(samplePojoRow.getDecimal(), decimal.getValue());
        assertEquals(samplePojoRow.getCurrency(), currency.getValue());
        assertEquals(samplePojoRow.getNumero(), numero.getValue());
        assertEquals(samplePojoRow.getData(), data.getValue());
    }

    @Test
    void bffPostAndValidateTest() throws FrameworkException {
        BffFieldsProva fieldsProva = new BffFieldsProva();
        fieldsProva.setAutocomplete("Ancora");
        fieldsProva.setTesto("description");
        fieldsProva.setNumero("10");
        fieldsProva.setDecimal("10");
        fieldsProva.setCurrency("10");
        fieldsProva.setData("2021-01-01");


        BffFieldsResponse<BffFieldsProva> response = new BffFieldsResponse();

        // Ok
        boolean result = inputFields.postAndValidate(fieldsProva, response.getMessageList());
        assertTrue(result);
        assertTrue(response.getMessageList().isEmpty());

        // Ko
        fieldsProva.setAutocomplete("Xy");
        fieldsProva.setData("2021-01-99");

        result = inputFields.postAndValidate(fieldsProva, response.getMessageList());
        assertFalse(result);
        assertFalse(response.getMessageList().isEmpty());
        assertEquals(2, response.getMessageList().getList().size());
    }

    @Test
    void bffCopyFromDataSourceTest() throws FrameworkException {
        Row row = new Row();
        row.setString("Autocomplete", "A");
        row.setString("Testo", "description");
        row.setBigDecimal("Numero", BigDecimal.valueOf(10));
        row.setBigDecimal("Decimal", BigDecimal.valueOf(10));
        row.setBigDecimal("Currency", BigDecimal.valueOf(10));
        row.setTimestamp("Data", TimeStampUtil.parseTimestamp("2021-01-01", "yyyy-MM-dd"));

        BffFieldsProva bffFields = new BffFieldsProva();

        inputFields.copyFromDataSourceToBffFields(row, bffFields);

        assertEquals("Ancora", bffFields.getAutocomplete());
        assertEquals("description", bffFields.getTesto());
        assertEquals("10", bffFields.getNumero());
        assertEquals("10,00", bffFields.getDecimal());
        assertEquals("10,00 €", bffFields.getCurrency());
        assertEquals("2021-01-01", bffFields.getData());
    }


    @Test
    void removeTest() {
        assertEquals(6, inputFields.getElements().size());

        inputFields.removeChilds("*rr*");
        assertEquals(5, inputFields.getElements().size());

        inputFields.removeChilds("d*");
        assertEquals(3, inputFields.getElements().size());

        inputFields.removeChilds("d*");
        assertEquals(3, inputFields.getElements().size());
    }
}

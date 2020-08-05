package it.eg.sloth.framework.common.casting;

import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.base.TimestampUtilTest;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.jaxb.form.DataType;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
public class DataTypesTest {

    @Test
    public void timestampFormatTextTest() throws FrameworkException {
        Timestamp timestamp = TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy");

        assertEquals("01/06/2020", DataTypes.DATE.formatText(timestamp, Locale.ITALY));
        assertEquals("06/01/2020", DataTypes.DATE.formatText(timestamp, Locale.US));

        assertEquals("lun, 01/06", DataTypes.DATE.formatText(timestamp, Locale.ITALY, "EE, dd/MM"));
        assertEquals("Mon, 01/06", DataTypes.DATE.formatText(timestamp, Locale.US, "EE, dd/MM"));
    }


    @Test
    public void bigDecimalFormatValueTest() throws FrameworkException {
        assertEquals("1,000.00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00", DataTypes.DECIMAL.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1000", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1000", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0", DataTypes.INTEGER.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1,000.00 $", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00 €", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00 €", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 €", DataTypes.CURRENCY.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("10,00 %", DataTypes.PERC.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 %", DataTypes.PERC.formatValue(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1,000.0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("1,000.9870000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000.987), Locale.US));
        assertEquals("1.000,9870000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000.987), Locale.ITALY));
        assertEquals("10,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(0), Locale.ITALY));
    }


    @Test
    public void bigDecimalParseValueTest() throws FrameworkException {
        // Integer
        assertEquals(BigDecimal.valueOf(1000), DataTypes.DECIMAL.parseValue("1000.00", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.DECIMAL.parseValue("1000,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.DECIMAL.parseValue("10,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.DECIMAL.parseValue("0,00", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(1000), DataTypes.INTEGER.parseValue("1000", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.INTEGER.parseValue("1000", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.INTEGER.parseValue("10", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.INTEGER.parseValue("0", Locale.ITALY));

        // Currency
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1,000.00 $", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1.000,00 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1.000", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1,000.987 $", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1.000,987 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.CURRENCY.parseValue("10,00 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY.parseValue("0,00 €", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1000.00", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1000,00", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1000.987", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1000,987", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(1234), DataTypes.CURRENCY.parseValue("12.34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(12.34), DataTypes.CURRENCY.parseValue("12,34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY.parseValue("0.00", Locale.ITALY));

        // Percentuale
        assertEquals(BigDecimal.valueOf(1234), DataTypes.PERC.parseValue("12.34", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(12.34), DataTypes.PERC.parseValue("12,34", Locale.ITALY));

        // Number
        assertEquals(BigDecimal.valueOf(1000), DataTypes.NUMBER.parseValue("1000", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.NUMBER.parseValue("1000", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.NUMBER.parseValue("1000.987", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.NUMBER.parseValue("1000,987", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.NUMBER.parseValue("10,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.NUMBER.parseValue("0,00", Locale.ITALY));
    }

    @Test
    public void bigDecimalFormatTextTest() throws FrameworkException {
        assertEquals("1,000.00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00", DataTypes.DECIMAL.formatText(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1000", DataTypes.INTEGER.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1000", DataTypes.INTEGER.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10", DataTypes.INTEGER.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0", DataTypes.INTEGER.formatText(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1,000.00 $", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,00 €", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("10,00 €", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 €", DataTypes.CURRENCY.formatText(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("10,00 %", DataTypes.PERC.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,00 %", DataTypes.PERC.formatText(BigDecimal.valueOf(0), Locale.ITALY));

        assertEquals("1,000.0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1.000,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("1,000.9870000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000.987), Locale.US));
        assertEquals("1.000,9870000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000.987), Locale.ITALY));
        assertEquals("10,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(0), Locale.ITALY));
    }


    @Test
    public void stringParseTest() throws FrameworkException {
        FrameworkException frameworkException;

        // Mail non valida
        frameworkException = assertThrows(FrameworkException.class, () -> {
            DataTypes.MAIL.parseValue("@@", Locale.ITALY);
        });
        assertEquals("Impossibile validare il valore passato - Indirizzo email errato", frameworkException.getMessage());

        // Mail valida
        assertEquals("alice@gmail.com", DataTypes.MAIL.parseValue("alice@gmail.com", Locale.ITALY));


        // Codice fiscale non valido
        frameworkException = assertThrows(FrameworkException.class, () -> {
            DataTypes.CODICE_FISCALE.parseValue("AAAAA", Locale.ITALY);
        });
        assertEquals("Impossibile validare il valore passato - Lunghezza codice fiscale errata", frameworkException.getMessage());

        // Codice fiscale valido
        assertEquals("RSSMRA80A01A944I", DataTypes.CODICE_FISCALE.parseValue("RSSMRA80A01A944I", Locale.ITALY));

        // Partita IVA non valida
        frameworkException = assertThrows(FrameworkException.class, () -> {
            DataTypes.PARTITA_IVA.parseValue("AAAAA", Locale.ITALY);
        });
        assertEquals("Impossibile validare il valore passato - Lunghezza partita iva errata", frameworkException.getMessage());
        DataTypes.PARTITA_IVA.parseValue("00000000000", Locale.ITALY);

        // Partita IVA valida
        assertEquals("00000000000", DataTypes.PARTITA_IVA.parseValue("00000000000", Locale.ITALY));

    }


}

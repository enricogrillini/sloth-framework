package it.eg.sloth.framework.common.casting;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Test;

import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

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
 *
 */
public class DataTypesTest {

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

        assertEquals("1000.0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1000,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("1000.9870000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000.987), Locale.US));
        assertEquals("1000,9870000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(1000.987), Locale.ITALY));
        assertEquals("10,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,0000000", DataTypes.NUMBER.formatValue(BigDecimal.valueOf(0), Locale.ITALY));
    }


    @Test
    public void bigDecimalParseValueTest() throws FrameworkException {
        assertEquals(BigDecimal.valueOf(1000), DataTypes.DECIMAL.parseValue("1000.00", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.DECIMAL.parseValue("1000,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.DECIMAL.parseValue("10,00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.DECIMAL.parseValue("0,00", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(1000), DataTypes.INTEGER.parseValue("1000", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.INTEGER.parseValue("1000", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.INTEGER.parseValue("10", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.INTEGER.parseValue("0", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1,000.00 $", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1.000,00 €", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1,000.987 $", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1.000,987 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.CURRENCY.parseValue("10,00 €", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY.parseValue("0,00 €", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1000.00", Locale.US));
        assertEquals(BigDecimal.valueOf(1000), DataTypes.CURRENCY.parseValue("1000,00", Locale.ITALY));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1000.987", Locale.US));
        assertEquals(BigDecimalUtil.toBigDecimal(1000.987), DataTypes.CURRENCY.parseValue("1000,987", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(10), DataTypes.CURRENCY.parseValue("10.00", Locale.ITALY));
        assertEquals(BigDecimal.valueOf(0), DataTypes.CURRENCY.parseValue("0.00", Locale.ITALY));

        assertEquals(BigDecimal.valueOf(10), DataTypes.PERC.parseValue("10.00", Locale.ITALY));

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

        assertEquals("1000.0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000), Locale.US));
        assertEquals("1000,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000), Locale.ITALY));
        assertEquals("1000.9870000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000.987), Locale.US));
        assertEquals("1000,9870000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(1000.987), Locale.ITALY));
        assertEquals("10,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(10), Locale.ITALY));
        assertEquals("0,0000000", DataTypes.NUMBER.formatText(BigDecimal.valueOf(0), Locale.ITALY));
    }

}

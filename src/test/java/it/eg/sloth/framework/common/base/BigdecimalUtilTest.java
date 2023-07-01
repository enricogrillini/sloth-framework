package it.eg.sloth.framework.common.base;


import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
class BigdecimalUtilTest {

    Locale locale;

    @BeforeEach
    void beforeEach() {
        locale = Locale.getDefault();
        Locale.setDefault(Locale.ITALY);
    }

    @AfterEach
    void afterEach() {
        Locale.setDefault(locale);
    }

    @Test
    void sumTest() {
        assertEquals(null, BigDecimalUtil.sum(null, null));
        assertEquals(BigDecimal.valueOf(1), BigDecimalUtil.sum(BigDecimal.valueOf(1), null));
        assertEquals(BigDecimal.valueOf(1), BigDecimalUtil.sum(null, BigDecimal.valueOf(1)));
        assertEquals(BigDecimal.valueOf(2), BigDecimalUtil.sum(BigDecimal.valueOf(1), BigDecimal.valueOf(1)));
    }

    @Test
    void greatestTest() {
        assertEquals(null, BigDecimalUtil.greatest(null, null));
        assertEquals(null, BigDecimalUtil.greatest(BigDecimal.valueOf(1), null));
        assertEquals(null, BigDecimalUtil.greatest(null, BigDecimal.valueOf(1)));
        assertEquals(BigDecimal.valueOf(20), BigDecimalUtil.greatest(BigDecimal.valueOf(10), BigDecimal.valueOf(20)));
        assertEquals(BigDecimal.valueOf(10), BigDecimalUtil.greatest(BigDecimal.valueOf(10), BigDecimal.valueOf(2)));
    }

    @Test
    void parseBigDecimalTest() throws FrameworkException {
        assertNull(BigDecimalUtil.parseBigDecimal(""));
        assertEquals(10.6, BigDecimalUtil.parseBigDecimal("10,6").doubleValue());
    }


    @Test
    void intValueTest() {
        assertEquals(0, BigDecimalUtil.intValue(null));
        assertEquals(10, BigDecimalUtil.intValue(BigDecimal.valueOf(10)));
    }

    @Test
    void intObjectTest() {
        assertNull(BigDecimalUtil.intObject(null));
        assertEquals(10.0, BigDecimalUtil.intObject(BigDecimal.valueOf(10)), 0.0);
    }

    @Test
    void doubleValueTest() {
        assertNull(BigDecimalUtil.toBigDecimal(null));
        assertEquals(BigDecimal.valueOf(10), BigDecimalUtil.toBigDecimal(10));

        assertEquals(0.0, BigDecimalUtil.doubleValue(null), 0.0);
        assertEquals(10.0, BigDecimalUtil.doubleValue(BigDecimal.valueOf(10)), 0.0);
    }

    @Test
    void doubleObjectTest() {
        assertNull(BigDecimalUtil.doubleObject(null));
        assertEquals(10.0, BigDecimalUtil.doubleObject(BigDecimal.valueOf(10)), 0.0);
    }


}

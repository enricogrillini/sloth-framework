package it.eg.sloth.framework.common.base;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
class BaseFunctionTest {

    @Test
    void equalsTest() {
        assertTrue(BaseFunction.equals(null, null));
        assertFalse(BaseFunction.equals(BigDecimal.valueOf(0), null));
        assertFalse(BaseFunction.equals(null, BigDecimal.valueOf(0)));
        assertFalse(BaseFunction.equals(BigDecimal.valueOf(0), BigDecimal.valueOf(1)));
        assertTrue(BaseFunction.equals(BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
    }

    @Test
    void isBlankTest() {
        assertTrue(BaseFunction.isBlank(null));
        assertTrue(BaseFunction.isBlank(""));
        assertTrue(BaseFunction.isBlank("   "));
        assertFalse(BaseFunction.isBlank("pippo"));
    }

    @Test
    void isNullTest() {
        assertTrue(BaseFunction.isNull(null));
        assertTrue(BaseFunction.isNull(""));
        assertFalse(BaseFunction.isNull(new BigDecimal(0)));
    }

    @Test
    void coalesceTest() {
        assertEquals("Prova", BaseFunction.coalesce("", null, "Prova", "Pippo"));
        assertEquals(null, BaseFunction.coalesce("", null));
    }

}

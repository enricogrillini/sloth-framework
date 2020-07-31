package it.eg.sloth.framework.common.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import it.eg.sloth.framework.common.base.BaseFunction;

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
public class BaseFunctionTest {

    @Test
    public void equalsTest() {
        assertTrue(BaseFunction.equals(null, null));
        assertFalse(BaseFunction.equals( BigDecimal.valueOf(0), null));
        assertFalse(BaseFunction.equals(null,BigDecimal.valueOf(0)));
        assertFalse(BaseFunction.equals(BigDecimal.valueOf(0), BigDecimal.valueOf(1)));
        assertTrue(BaseFunction.equals(BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
    }

    @Test
    public void isBlankTest() {
        assertTrue(BaseFunction.isBlank(null));
        assertTrue(BaseFunction.isBlank(""));
        assertTrue(BaseFunction.isBlank("   "));
        assertFalse(BaseFunction.isBlank("pippo"));
    }

    @Test
    public void isNullTest() {
        assertTrue(BaseFunction.isNull(null));
        assertTrue(BaseFunction.isNull(""));
        assertFalse(BaseFunction.isNull(new BigDecimal(0)));
    }


}

package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
public class BigdecimalUtilTest {

    @Test
    public void sumTest() {
        assertEquals(null, BigDecimalUtil.sum(null, null));
        assertEquals(BigDecimal.valueOf(1), BigDecimalUtil.sum(BigDecimal.valueOf(1), null));
        assertEquals(BigDecimal.valueOf(1), BigDecimalUtil.sum(null, BigDecimal.valueOf(1)));
        assertEquals(BigDecimal.valueOf(2), BigDecimalUtil.sum(BigDecimal.valueOf(1), BigDecimal.valueOf(1)));
    }

}

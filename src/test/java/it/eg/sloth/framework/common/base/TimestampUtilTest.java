package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Test;

import java.sql.Timestamp;
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
public class TimestampUtilTest {

    @Test
    public void genericTest() throws FrameworkException {
        Timestamp timestamp = TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy");

        // get Year
        assertEquals(Integer.valueOf(2020), TimeStampUtil.getYear(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy")));

        // get Day of Week
        assertEquals(Integer.valueOf(2), TimeStampUtil.getDayOfWeek(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isMonday(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(3), TimeStampUtil.getDayOfWeek(TimeStampUtil.parseTimestamp("02/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isTuesday(TimeStampUtil.parseTimestamp("02/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(4), TimeStampUtil.getDayOfWeek(TimeStampUtil.parseTimestamp("03/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isWednesday(TimeStampUtil.parseTimestamp("03/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(5), TimeStampUtil.getDayOfWeek(TimeStampUtil.parseTimestamp("04/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isThursday(TimeStampUtil.parseTimestamp("04/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(6), TimeStampUtil.getDayOfWeek(TimeStampUtil.parseTimestamp("05/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isFriday(TimeStampUtil.parseTimestamp("05/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(7), TimeStampUtil.getDayOfWeek(TimeStampUtil.parseTimestamp("06/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isSaturday(TimeStampUtil.parseTimestamp("06/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(1), TimeStampUtil.getDayOfWeek(TimeStampUtil.parseTimestamp("07/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isSunday(TimeStampUtil.parseTimestamp("07/06/2020", "dd/MM/yyyy")));
    }


}

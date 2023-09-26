package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

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
class TimestampUtilTest {


    @Test
    void timestampParse_DateTime() throws FrameworkException {
//        assertEquals("2023-04-03 15:26:08.0", TimeStampUtil.parseTimestamp("2023-04-03 15:26", "yyyy-MM-dd HH:mm:ss").toString());

        assertEquals("2023-04-03 15:26:08.0", TimeStampUtil.parseTimestamp("03/04/2023 15:26:08", "dd/MM/yyyy HH:mm:ss").toString());
        assertEquals("2023-04-03 15:26:08.0", TimeStampUtil.parseTimestamp("2023/04/03 15:26:08", "yyyy/MM/dd HH:mm:ss").toString());
        assertEquals("2023-04-03 15:26:08.0", TimeStampUtil.parseTimestamp("2023-04-03 15:26:08", "yyyy-MM-dd HH:mm:ss").toString());
    }

    @Test
    void timestampParse_Date() throws FrameworkException {
        assertEquals("2023-04-03 00:00:00.0", TimeStampUtil.parseTimestamp("03/04/2023", "dd/MM/yyyy").toString());
        assertEquals("2023-04-03 00:00:00.0", TimeStampUtil.parseTimestamp("2023/04/03", "yyyy/MM/dd").toString());
        assertEquals("2023-04-03 00:00:00.0", TimeStampUtil.parseTimestamp("2023-04-03", "yyyy-MM-dd").toString());
    }


    @Test
    void genericTestOk() throws FrameworkException {
        Timestamp timestamp = TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy");

        // get Year
        assertEquals(null, TimeStampUtil.getYear(null));
        assertEquals(Integer.valueOf(2020), TimeStampUtil.getYear(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy")));

        // get Day of Week
        assertEquals(null, TimeStampUtil.getWeekDay(null));
        assertEquals(Integer.valueOf(2), TimeStampUtil.getWeekDay(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isMonday(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isMonday(TimeStampUtil.parseTimestamp("02/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(3), TimeStampUtil.getWeekDay(TimeStampUtil.parseTimestamp("02/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isTuesday(TimeStampUtil.parseTimestamp("02/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isTuesday(TimeStampUtil.parseTimestamp("03/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(4), TimeStampUtil.getWeekDay(TimeStampUtil.parseTimestamp("03/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isWednesday(TimeStampUtil.parseTimestamp("03/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isWednesday(TimeStampUtil.parseTimestamp("04/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(5), TimeStampUtil.getWeekDay(TimeStampUtil.parseTimestamp("04/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isThursday(TimeStampUtil.parseTimestamp("04/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isThursday(TimeStampUtil.parseTimestamp("05/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(6), TimeStampUtil.getWeekDay(TimeStampUtil.parseTimestamp("05/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isFriday(TimeStampUtil.parseTimestamp("05/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isFriday(TimeStampUtil.parseTimestamp("06/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(7), TimeStampUtil.getWeekDay(TimeStampUtil.parseTimestamp("06/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isSaturday(TimeStampUtil.parseTimestamp("06/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isSaturday(TimeStampUtil.parseTimestamp("07/06/2020", "dd/MM/yyyy")));

        assertEquals(Integer.valueOf(1), TimeStampUtil.getWeekDay(TimeStampUtil.parseTimestamp("07/06/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isSunday(TimeStampUtil.parseTimestamp("07/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isSunday(TimeStampUtil.parseTimestamp("08/06/2020", "dd/MM/yyyy")));
    }

    @Test
    void genericTestHolliday() throws FrameworkException {
        assertTrue(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy")));

        // Pasqua
        assertTrue(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("12/04/2020", "dd/MM/yyyy")));
        assertTrue(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("13/04/2020", "dd/MM/yyyy")));

        // Domenica
        assertTrue(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("19/04/2020", "dd/MM/yyyy")));

        // Festività custom
        assertTrue(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("09/06/2020", "dd/MM/yyyy"), TimeStampUtil.parseTimestamp("09/06/2020", "dd/MM/yyyy")));

        // Lavorativi
        assertFalse(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("09/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("10/06/2020", "dd/MM/yyyy")));
        assertFalse(TimeStampUtil.isHoliday(TimeStampUtil.parseTimestamp("11/06/2020", "dd/MM/yyyy")));
    }


    @Test
    void genericTestKo() {
        // Formato data errato
        FrameworkException frameworkException = assertThrows(FrameworkException.class, () -> {
            TimeStampUtil.parseTimestamp("aaaa", "dd/MM/yyyy");
        });
        assertEquals("Impossibile validare il valore passato", frameworkException.getMessage());

        // Formato data errato
        frameworkException = assertThrows(FrameworkException.class, () -> {
            TimeStampUtil.parseTimestamp("32/01/2021", "dd/MM/yyyy");
        });
        assertEquals("Impossibile validare il valore passato", frameworkException.getMessage());

    }
}

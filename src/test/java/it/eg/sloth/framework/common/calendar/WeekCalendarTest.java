package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class WeekCalendarTest {

    private WeekCalendar weekCalendar;

    @BeforeEach
    void init() throws FrameworkException {
        weekCalendar = new WeekCalendar(TimeStampUtil.parseTimestamp("07/07/2020", "dd/MM/yyyy"));
    }


    @Test
    void weekTest() throws FrameworkException {
        assertEquals(TimeStampUtil.parseTimestamp("06/07/2020", "dd/MM/yyyy"), weekCalendar.firstWeekDay());
        assertEquals(TimeStampUtil.parseTimestamp("12/07/2020", "dd/MM/yyyy"), weekCalendar.lastWeekDay());
    }


    @Test
    void prevWeekTest() throws FrameworkException {
        weekCalendar.prev();
        assertEquals(TimeStampUtil.parseTimestamp("29/06/2020", "dd/MM/yyyy"), weekCalendar.firstWeekDay());
        assertEquals(TimeStampUtil.parseTimestamp("05/07/2020", "dd/MM/yyyy"), weekCalendar.lastWeekDay());
    }

    @Test
    void nextWeekTest() throws FrameworkException {
        weekCalendar.next();
        assertEquals(TimeStampUtil.parseTimestamp("13/07/2020", "dd/MM/yyyy"), weekCalendar.firstWeekDay());
        assertEquals(TimeStampUtil.parseTimestamp("19/07/2020", "dd/MM/yyyy"), weekCalendar.lastWeekDay());
    }

}

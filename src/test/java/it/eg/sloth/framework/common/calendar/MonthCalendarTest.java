package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
public class MonthCalendarTest {

    private MonthCalendar monthCalendar;

    @Before
    public void init() throws FrameworkException {
        monthCalendar = new MonthCalendar(TimeStampUtil.parseTimestamp("07/07/2020", "dd/MM/yyyy"));
    }


    @Test
    public void monthTest() throws FrameworkException {
        assertEquals(TimeStampUtil.parseTimestamp("01/07/2020", "dd/MM/yyyy"), monthCalendar.firstMonthDay());
        assertEquals(TimeStampUtil.parseTimestamp("31/07/2020", "dd/MM/yyyy"), monthCalendar.lastMonthDay());

        assertEquals(TimeStampUtil.parseTimestamp("29/06/2020", "dd/MM/yyyy"), monthCalendar.firstCalendarDay());
        assertEquals(TimeStampUtil.parseTimestamp("02/08/2020", "dd/MM/yyyy"), monthCalendar.lastCalendarDay());
    }


    @Test
    public void prevMonthTest() throws FrameworkException {
        monthCalendar.prev();
        assertEquals(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy"), monthCalendar.firstMonthDay());
        assertEquals(TimeStampUtil.parseTimestamp("30/06/2020", "dd/MM/yyyy"), monthCalendar.lastMonthDay());

        assertEquals(TimeStampUtil.parseTimestamp("01/06/2020", "dd/MM/yyyy"), monthCalendar.firstCalendarDay());
        assertEquals(TimeStampUtil.parseTimestamp("05/07/2020", "dd/MM/yyyy"), monthCalendar.lastCalendarDay());
    }

    @Test
    public void nextMonthTest() throws FrameworkException {
        monthCalendar.next();
        assertEquals(TimeStampUtil.parseTimestamp("01/08/2020", "dd/MM/yyyy"), monthCalendar.firstMonthDay());
        assertEquals(TimeStampUtil.parseTimestamp("31/08/2020", "dd/MM/yyyy"), monthCalendar.lastMonthDay());

        assertEquals(TimeStampUtil.parseTimestamp("27/07/2020", "dd/MM/yyyy"), monthCalendar.firstCalendarDay());
        assertEquals(TimeStampUtil.parseTimestamp("06/09/2020", "dd/MM/yyyy"), monthCalendar.lastCalendarDay());
    }

}

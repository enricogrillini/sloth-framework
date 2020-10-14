package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class RollingWeekCalendarTest {

    private RollingWeekCalendar rollingWeekCalendar;
    private RollingWeekCalendar rollingWeekCalendarFromToday;

    @Before
    public void init() throws FrameworkException {
        rollingWeekCalendar = new RollingWeekCalendar(TimeStampUtil.parseTimestamp("07/07/2020", "dd/MM/yyyy"));
        rollingWeekCalendarFromToday = new RollingWeekCalendar();
    }

    @Test
    public void weekTest() throws FrameworkException {
        assertEquals(TimeStampUtil.parseTimestamp("07/07/2020", "dd/MM/yyyy"), rollingWeekCalendar.getFirstDay());
        assertEquals(TimeStampUtil.parseTimestamp("13/07/2020", "dd/MM/yyyy"), rollingWeekCalendar.getLastDay());
        assertEquals(TimeStampUtil.parseTimestamp("08/07/2020", "dd/MM/yyyy"), rollingWeekCalendar.getDay(TimeStampUtil.parseTimestamp("08/07/2020", "dd/MM/yyyy")).getCurrentDay());

        //Non ci sono il 05-07 e il 20-07 in questa settimana
        assertNull(rollingWeekCalendar.getDay(TimeStampUtil.parseTimestamp("05/07/2020", "dd/MM/yyyy")));
        assertNull(rollingWeekCalendar.getDay(TimeStampUtil.parseTimestamp("20/07/2020", "dd/MM/yyyy")));

        // Next
        rollingWeekCalendar.next();
        assertEquals(TimeStampUtil.parseTimestamp("14/07/2020", "dd/MM/yyyy"), rollingWeekCalendar.getFirstDay());
        assertEquals(TimeStampUtil.parseTimestamp("20/07/2020", "dd/MM/yyyy"), rollingWeekCalendar.getLastDay());

        // Prev
        rollingWeekCalendar.prev();
        assertEquals(TimeStampUtil.parseTimestamp("07/07/2020", "dd/MM/yyyy"), rollingWeekCalendar.getFirstDay());
        assertEquals(TimeStampUtil.parseTimestamp("13/07/2020", "dd/MM/yyyy"), rollingWeekCalendar.getLastDay());


        assertEquals(TimeStampUtil.truncSysdate(), rollingWeekCalendarFromToday.getFirstDay());
    }
}

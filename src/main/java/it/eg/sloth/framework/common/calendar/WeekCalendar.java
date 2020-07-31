package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeekCalendar implements Iterable<Day<?>> {

    private Timestamp week;
    private  List<Day<?>> dayList;

    public WeekCalendar(Timestamp week) throws FrameworkException {
        dayList = new ArrayList<>();
        setWeek(week);
    }

    public void setWeek(Timestamp week) throws FrameworkException {
        this.week = TimeStampUtil.firstDayOfWeek(week);
        dayList.clear();

        for (int i = 0; i < 7; i++) {
            dayList.add(new Day(TimeStampUtil.add(week, i)));
        }
    }

    @Override
    public Iterator<Day<?>> iterator() {
        return dayList.iterator();
    }

    public Timestamp firstWeekDay() {
        return week;
    }

    public Timestamp lastWeekDay() throws FrameworkException {
        return TimeStampUtil.lastDayOfWeek(week);
    }

    public void prev() throws FrameworkException {
        setWeek(TimeStampUtil.add(week, -7));
    }

    public void next() throws FrameworkException {
        setWeek(TimeStampUtil.add(week, 7));
    }
}

package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MonthCalendar<E> implements Iterable<Day<E>> {

    private Timestamp month;
    private Map<Timestamp, Day<E>> dayMap;

    public MonthCalendar(Timestamp month) throws FrameworkException {
        dayMap = new LinkedHashMap<>();
        setMonth(month);
    }

    public void setMonth(Timestamp month) throws FrameworkException {
        this.month = TimeStampUtil.firstDayOfMonth(month);
        dayMap.clear();

        Timestamp currentDay = firstCalendarDay();
        do {
            Day<E> day = new Day<>(currentDay);
            dayMap.put(day.getCurrentDay(), day);

            currentDay = TimeStampUtil.add(currentDay, 1);
        } while (currentDay.compareTo(lastCalendarDay()) <= 0);
    }

    @Override
    public Iterator<Day<E>> iterator() {
        return dayMap.values().iterator();
    }

    public Day<E> getDay(Timestamp day) {
        return dayMap.get(day);
    }

    public Timestamp firstCalendarDay() throws FrameworkException {
        return TimeStampUtil.firstDayOfWeek(month);
    }

    public Timestamp lastCalendarDay() throws FrameworkException {
        return TimeStampUtil.lastDayOfWeek(lastMonthDay());
    }

    public Timestamp firstMonthDay() {
        return month;
    }

    public Timestamp lastMonthDay() throws FrameworkException {
        return TimeStampUtil.lastDayOfMonth(month);
    }

    public MonthCalendar<E> prev() throws FrameworkException {
        setMonth(TimeStampUtil.add(month, -1));
        return this;
    }

    public MonthCalendar<E> next() throws FrameworkException {
        setMonth(TimeStampUtil.add(month, 32));
        return this;
    }
}

package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MonthCalendar<E, I extends DayInfo> implements Iterable<Day<E, I>> {

    private Timestamp month;
    private Map<Timestamp, Day<E, I>> dayMap;

    public MonthCalendar(Timestamp month) throws FrameworkException {
        dayMap = new LinkedHashMap<>();
        setMonth(month);
    }

    public void clear() throws FrameworkException {
        dayMap.clear();

        dayMap.clear();

        Timestamp currentDay = firstCalendarDay();
        for (; !currentDay.after(lastCalendarDay()); currentDay = TimeStampUtil.add(currentDay, 1)) {
            Day<E, I> day = new Day<>(currentDay);
            dayMap.put(day.getCurrentDay(), day);
        }
    }

    public void setMonth(Timestamp month) throws FrameworkException {
        this.month = TimeStampUtil.firstDayOfMonth(month);
        clear();
    }

    @Override
    public Iterator<Day<E, I>> iterator() {
        return dayMap.values().iterator();
    }

    public Day<E, I> getDay(Timestamp day) {
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

    public MonthCalendar<E, I> prev() throws FrameworkException {
        setMonth(TimeStampUtil.add(month, -1));
        return this;
    }

    public MonthCalendar<E, I> next() throws FrameworkException {
        setMonth(TimeStampUtil.add(month, 32));
        return this;
    }
}

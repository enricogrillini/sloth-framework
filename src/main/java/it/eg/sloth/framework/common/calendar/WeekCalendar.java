package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeekCalendar<E, I extends DayInfo> implements Iterable<Day<E, I>> {

    @Getter
    private Timestamp week;
    private Map<Timestamp, Day<E, I>> dayMap;

    public WeekCalendar(Timestamp week) throws FrameworkException {
        dayMap = new LinkedHashMap<>();
        setWeek(week);
    }

    public void clear() {
        dayMap.clear();

        for (int i = 0; i < 7; i++) {
            Day<E, I> day = new Day<>(TimeStampUtil.add(week, i));
            dayMap.put(day.getCurrentDay(), day);
        }
    }

    public void setWeek(Timestamp week) throws FrameworkException {
        this.week = TimeStampUtil.firstDayOfWeek(week);
        clear();
    }

    @Override
    public Iterator<Day<E, I>> iterator() {
        return dayMap.values().iterator();
    }

    public Day<E, I> getDay(Timestamp day) {
        return dayMap.get(day);
    }

    public Timestamp firstWeekDay() {
        return week;
    }

    public Timestamp lastWeekDay() throws FrameworkException {
        return TimeStampUtil.lastDayOfWeek(week);
    }

    public WeekCalendar<E, I> prev() throws FrameworkException {
        setWeek(TimeStampUtil.add(week, -7));
        return this;
    }

    public WeekCalendar<E, I> next() throws FrameworkException {
        setWeek(TimeStampUtil.add(week, 7));
        return this;
    }
}

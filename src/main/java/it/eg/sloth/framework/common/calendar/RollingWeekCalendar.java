package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RollingWeekCalendar<E, I extends DayInfo> implements Iterable<Day<E, I>> {

    @Getter
    private Timestamp fromDay;
    private Map<Timestamp, Day<E, I>> dayMap;

    public RollingWeekCalendar() throws FrameworkException {
        this(TimeStampUtil.truncSysdate());
    }

    public RollingWeekCalendar(Timestamp fromDay) {
        dayMap = new LinkedHashMap<>();
        setFromDay(fromDay);
    }

    public void clear() {
        dayMap.clear();

        for (int i = 0; i < 7; i++) {
            Day<E, I> day = new Day<>(TimeStampUtil.add(fromDay, i));
            dayMap.put(day.getCurrentDay(), day);
        }
    }

    public void setFromDay(Timestamp fromDay) {
        this.fromDay = fromDay;
        clear();
    }

    @Override
    public Iterator<Day<E, I>> iterator() {
        return dayMap.values().iterator();
    }

    public Day<E, I> getDay(Timestamp day) {
        return dayMap.get(day);
    }

    public Timestamp getFirstDay() {
        return fromDay;
    }

    public Timestamp getLastDay() {
        return TimeStampUtil.add(fromDay, 6);
    }

    public RollingWeekCalendar<E, I> prev() throws FrameworkException {
        setFromDay(TimeStampUtil.add(getFromDay(), -7));
        return this;
    }

    public RollingWeekCalendar<E, I> next() throws FrameworkException {
        setFromDay(TimeStampUtil.add(getFromDay(), 7));
        return this;
    }
}

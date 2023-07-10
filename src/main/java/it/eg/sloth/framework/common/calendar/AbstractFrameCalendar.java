package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractFrameCalendar<E, I extends DayInfo> implements FrameCalendar<E, I> {

    private Map<Timestamp, Day<E, I>> dayMap;
    private Timestamp[] otherHolidays;

    protected AbstractFrameCalendar(Timestamp timestamp, Timestamp... otherHolidays) throws FrameworkException {
        dayMap = new LinkedHashMap<>();
        set(timestamp);
        this.otherHolidays = otherHolidays;
    }

    @Override
    public void clear() throws FrameworkException {
        dayMap.clear();

        Timestamp currentDay = firstCalendarDay();
        for (; !currentDay.after(lastCalendarDay()); currentDay = TimeStampUtil.add(currentDay, 1)) {
            Day<E, I> day = new Day<>(currentDay, TimeStampUtil.isHoliday(currentDay, otherHolidays));
            dayMap.put(day.getCurrentDay(), day);
        }
    }

    @Override
    public Iterator<Day<E, I>> iterator() {
        return dayMap.values().iterator();
    }

    @Override
    public Day<E, I> getDay(Timestamp day) {
        return dayMap.get(day);
    }

    public int size() {
        return dayMap.size();
    }
}

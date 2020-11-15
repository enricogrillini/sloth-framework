package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractFrameCalendar<E, I extends DayInfo> implements FrameCalendar<E, I> {

    private Map<Timestamp, Day<E, I>> dayMap;

    public AbstractFrameCalendar(Timestamp timestamp) throws FrameworkException {
        dayMap = new LinkedHashMap<>();
        set(timestamp);
    }

    @Override
    public void clear() throws FrameworkException {
        dayMap.clear();

        Timestamp currentDay = firstCalendarDay();
        for (; !currentDay.after(lastCalendarDay()); currentDay = TimeStampUtil.add(currentDay, 1)) {
            Day<E, I> day = new Day<>(currentDay);
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


}

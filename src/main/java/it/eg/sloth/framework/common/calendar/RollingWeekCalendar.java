package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Map;

public class RollingWeekCalendar<E, I extends DayInfo> extends AbstractFrameCalendar<E, I> {

    @Getter
    private Timestamp fromDay;
    private Map<Timestamp, Day<E, I>> dayMap;

    public RollingWeekCalendar() throws FrameworkException {
        this(TimeStampUtil.truncSysdate());
    }

    public RollingWeekCalendar(Timestamp fromDay) throws FrameworkException {
        super(fromDay);
    }

    @Override
    public void set(Timestamp fromDay) throws FrameworkException {
        this.fromDay = fromDay;
        clear();
    }

    @Override
    public Timestamp get() {
        return fromDay;
    }

    @Override
    public Timestamp firstCalendarDay() throws FrameworkException {
        return fromDay;
    }

    @Override
    public Timestamp lastCalendarDay() throws FrameworkException {
        return TimeStampUtil.add(fromDay, 6);
    }

    @Override
    public RollingWeekCalendar<E, I> prev() throws FrameworkException {
        set(TimeStampUtil.add(getFromDay(), -7));
        return this;
    }

    @Override
    public RollingWeekCalendar<E, I> next() throws FrameworkException {
        set(TimeStampUtil.add(getFromDay(), 7));
        return this;
    }
}

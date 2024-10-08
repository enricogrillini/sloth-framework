package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;

public class StrictMonthCalendar<E, I> extends AbstractFrameCalendar<E, I> {

    private Timestamp month;

    public StrictMonthCalendar(Timestamp month, Timestamp... otherHolidays) throws FrameworkException {
        super(month, otherHolidays);
    }

    @Override
    public void set(Timestamp month) throws FrameworkException {
        this.month = TimeStampUtil.firstDayOfMonth(month);
        clear();
    }

    public Timestamp get() {
        return month;
    }

    @Override
    public Timestamp firstCalendarDay() throws FrameworkException {
        return TimeStampUtil.firstDayOfMonth(month);
    }

    @Override
    public Timestamp lastCalendarDay() throws FrameworkException {
        return TimeStampUtil.lastDayOfMonth(lastMonthDay());
    }

    @Override
    public StrictMonthCalendar<E, I> prev() throws FrameworkException {
        set(TimeStampUtil.add(month, -1));
        return this;
    }

    @Override
    public StrictMonthCalendar<E, I> next() throws FrameworkException {
        set(TimeStampUtil.add(month, 32));
        return this;
    }

    public Timestamp firstMonthDay() {
        return month;
    }

    public Timestamp lastMonthDay() throws FrameworkException {
        return TimeStampUtil.lastDayOfMonth(month);
    }
}

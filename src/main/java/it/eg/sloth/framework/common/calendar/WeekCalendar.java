package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;

import java.sql.Timestamp;

public class WeekCalendar<E, I> extends AbstractFrameCalendar<E, I> {

    @Getter
    private Timestamp week;

    public WeekCalendar(Timestamp week, Timestamp... otherHolidays) throws FrameworkException {
        super(week, otherHolidays);
    }

    @Override
    public void set(Timestamp week) throws FrameworkException {
        this.week = TimeStampUtil.firstDayOfWeek(week);
        clear();
    }

    @Override
    public Timestamp get() {
        return week;
    }

    @Override
    public Timestamp firstCalendarDay() throws FrameworkException {
        return firstWeekDay();
    }

    @Override
    public Timestamp lastCalendarDay() throws FrameworkException {
        return lastWeekDay();
    }

    @Override
    public WeekCalendar<E, I> prev() throws FrameworkException {
        set(TimeStampUtil.add(week, -7));
        return this;
    }

    @Override
    public WeekCalendar<E, I> next() throws FrameworkException {
        set(TimeStampUtil.add(week, 7));
        return this;
    }

    public Timestamp firstWeekDay() {
        return week;
    }

    public Timestamp lastWeekDay() throws FrameworkException {
        return TimeStampUtil.lastDayOfWeek(week);
    }
}

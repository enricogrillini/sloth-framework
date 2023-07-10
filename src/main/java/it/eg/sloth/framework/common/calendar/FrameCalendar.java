package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;

public interface FrameCalendar<E, I extends DayInfo> extends Iterable<Day<E, I>> {

    void clear() throws FrameworkException;

    /**
     * Imposta il giorno rappresentativo del calendario
     *
     * @return
     * @throws FrameworkException
     */
    void set(Timestamp timestamp) throws FrameworkException;

    /**
     * Ritorna il giorno rappresentativo del calendario
     *
     * @return
     * @throws FrameworkException
     */
    Timestamp get() throws FrameworkException;

    Day<E, I> getDay(Timestamp day);

    Timestamp firstCalendarDay() throws FrameworkException;

    Timestamp lastCalendarDay() throws FrameworkException;

    FrameCalendar<E, I> prev() throws FrameworkException;

    FrameCalendar<E, I> next() throws FrameworkException;

    int size();

}

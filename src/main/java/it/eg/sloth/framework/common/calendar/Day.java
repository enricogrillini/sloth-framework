package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@ToString
public class Day<E> implements Iterable<E> {

    @Getter
    private Timestamp currentDay;

    @Getter
    @Setter
    private boolean locked;

    @Getter
    @Setter
    private Level level;

    @Getter
    @Setter
    private String message;

    private List<E> eventList;

    public Day(Timestamp day) {
        this.currentDay = day;
        this.locked = false;
        this.level = null;
        this.message = null;
        this.eventList = new ArrayList<>();
    }

    public boolean isMonday() {
        return TimeStampUtil.getWeekDay(currentDay) == Calendar.MONDAY;
    }

    public boolean isTuesday() {
        return TimeStampUtil.getWeekDay(currentDay) == Calendar.TUESDAY;
    }

    public boolean isWednesday() {
        return TimeStampUtil.getWeekDay(currentDay) == Calendar.WEDNESDAY;
    }

    public boolean isThursday() {
        return TimeStampUtil.getWeekDay(currentDay) == Calendar.THURSDAY;
    }

    public boolean isFriday() {
        return TimeStampUtil.getWeekDay(currentDay) == Calendar.FRIDAY;
    }

    public boolean isSaturday() {
        return TimeStampUtil.getWeekDay(currentDay) == Calendar.SATURDAY;
    }

    public boolean isSunday() {
        return TimeStampUtil.getWeekDay(currentDay) == Calendar.SUNDAY;
    }

    public boolean isHolliday(Timestamp... otherHoliday) throws FrameworkException {
        return TimeStampUtil.isHoliday(currentDay, otherHoliday);
    }

    public boolean isToday() throws FrameworkException {
        return TimeStampUtil.truncSysdate().equals(getCurrentDay());
    }

    @Override
    public Iterator<E> iterator() {
        return eventList.iterator();
    }

    public void addEvent(E event) {
        eventList.add(event);
    }

    public void removeEvents(E event) {
        eventList.remove(event);
    }

    public void clearEvents() {
        eventList.clear();
    }

}

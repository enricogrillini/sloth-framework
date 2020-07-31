package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day<E> implements Iterable<E> {

    @Getter
    private Timestamp day;

    @Getter
    @Setter
    private boolean locked;

    private List<E> eventList;

    public Day(Timestamp day) {
        this.day = day;
        this.locked = false;
        this.eventList = new ArrayList<>();
    }

    public boolean isHolliday(Timestamp... otherHoliday) throws FrameworkException {
        return TimeStampUtil.isHoliday(day, otherHoliday);
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

    public void clearEvents(E event) {
        eventList.clear();
    }

}

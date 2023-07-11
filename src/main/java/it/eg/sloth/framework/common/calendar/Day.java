package it.eg.sloth.framework.common.calendar;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.*;

@ToString
@NoArgsConstructor
public class Day<E, I extends DayInfo> implements Iterable<E> {

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
    private String title;

    @Getter
    @Setter
    private String message;

    private List<E> eventList;
    private Map<String, I> infoMap;

    @Getter
    @Setter
    private boolean holiday;

    public Day(Timestamp day, boolean holiday) {
        this.currentDay = day;
        this.holiday = holiday;
        this.locked = false;
        this.level = null;
        this.title = null;
        this.message = null;
        this.eventList = new ArrayList<>();
        this.infoMap = new LinkedHashMap<>();
    }

    public void addInfo(I info) {
        infoMap.put(info.getId(), info);
    }

    public I getInfo(String id) {
        return infoMap.get(id);
    }

    public Collection<I> getInfoList() {
        return Collections.unmodifiableCollection(infoMap.values());
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

    public boolean isToday() throws FrameworkException {
        return TimeStampUtil.truncSysdate().equals(getCurrentDay());
    }

    @Override
    public Iterator<E> iterator() {
        return eventList.iterator();
    }

    public int eventCount() {
        return eventList.size();
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

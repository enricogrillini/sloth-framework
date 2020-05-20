package it.eg.sloth.framework.monitor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.security.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorSingleton {

    public static final String PAGE = "Page";
    public static final String JSON = "Json";
    public static final String JOB = "Job";
    public static final String INITIALIZER = "Initializer";

    private static MonitorSingleton instance = null;

    private boolean active = false;
    private Timestamp startupTime;
    private long eventId;
    private Map<Long, MonitorEvent> runningEvent;
    private Map<String, MonitorStatistics> statistics;
    private Map<Timestamp, Integer> trend;
    private EventWriter eventWriter;

    private MonitorSingleton() {
        active = false;
        startupTime = TimeStampUtil.sysdate();
        eventId = 1;
        runningEvent = new HashMap<>();
        statistics = new HashMap<>();
        trend = new LinkedHashMap<>();

        log.info("Monitor inizializzato");
    }

    public static synchronized MonitorSingleton getInstance() {
        if (instance == null) {
            instance = new MonitorSingleton();
        }

        return instance;
    }

    private void clear() {
        active = false;
        startupTime = null;
        eventId = 1;
        runningEvent = new HashMap<>();
        statistics = new HashMap<>();
        trend = new LinkedHashMap<>();
    }

    public synchronized void start() {
        clear();
        active = true;
        startupTime = TimeStampUtil.sysdate();
    }

    public synchronized void stop() {
        clear();
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized Timestamp getStartupTime() {
        return startupTime;
    }

    public synchronized void setEventWriter(EventWriter eventWriter) {
        this.eventWriter = eventWriter;
    }

    public synchronized long startEvent(String group, String name, User user) {
        if (active) {
            MonitorEvent event = new MonitorEvent(group, name, user);
            event.start();

            // Rendo persistente l'evento
            if (eventWriter != null) {
                eventWriter.writeEvent(event);
            }

            runningEvent.put(Long.valueOf(eventId), event);

            return eventId++;
        } else {
            return 0;
        }
    }

    public synchronized void endEvent(long eventId) {
        if (active && eventId > 0 && eventId < this.eventId) {
            MonitorEvent event = runningEvent.remove(Long.valueOf(eventId));
            event.end();

            // Rendo persistente l'evento
            if (eventWriter != null) {
                eventWriter.writeEvent(event);
            }

            MonitorStatistics monitorStatistics = statistics.get(event.getGroup() + "." + event.getName());
            if (monitorStatistics == null) {
                monitorStatistics = new MonitorStatistics(event.getGroup(), event.getName());
                statistics.put(event.getGroup() + "." + event.getName(), monitorStatistics);
            }

            Timestamp ora = null;
            try {
                ora = BaseFunction.trunc(TimeStampUtil.sysdate(), "dd/MM/yyyy HH");
            } catch (FrameworkException e) {
                log.error("endEvent", e);
            }

            if (!trend.containsKey(ora)) {
                trend.put(ora, 1);
            } else {
                trend.put(ora, trend.get(ora) + 1);
            }

            monitorStatistics.update(event);
        }
    }

    public synchronized DataTable getStatistics() {
        Table dataTable = new Table();

        for (MonitorStatistics monitorStatistics : statistics.values()) {
            monitorStatistics.populateRow(dataTable.add());
        }

        dataTable.first();
        return dataTable;
    }

    public synchronized DataTable getTrend() throws FrameworkException {
        return getTrend(null, null);
    }

    public synchronized DataTable getTrend(Timestamp dataDa, Timestamp dataA) throws FrameworkException {
        if (dataDa == null) {
            dataDa = BaseFunction.trunc(startupTime);
        } else {
            dataDa = BaseFunction.trunc(dataDa);
        }

        if (dataA == null) {
            dataA = TimeStampUtil.add(TimeStampUtil.truncSysdate(), 1);
        } else {
            dataA = TimeStampUtil.add(BaseFunction.trunc(dataA), 1);
        }

        Table dataTable = new Table();
        for (Entry<Timestamp, Integer> entry : trend.entrySet()) {
            if (entry.getKey().compareTo(dataDa) >= 0 && entry.getKey().compareTo(dataA) < 0) {
                Row row = dataTable.add();

                row.setTimestamp("hour", entry.getKey());
                row.setBigDecimal("executions", new BigDecimal(entry.getValue()));
            }
        }

        dataTable.first();
        return dataTable;
    }
}

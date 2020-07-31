package it.eg.sloth.framework.monitor;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.monitor.model.*;
import it.eg.sloth.framework.security.User;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Slf4j
public class MonitorSingleton {

    public static final String PAGE = "Page";
    public static final String JOB = "Job";
    public static final String INITIALIZER = "Initializer";

    private static MonitorSingleton instance = null;

    private boolean active;
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

            runningEvent.put(eventId, event);

            return eventId++;
        } else {
            return 0;
        }
    }

    public synchronized void endEvent(long eventId) {
        if (active && eventId > 0 && eventId < this.eventId) {
            MonitorEvent event = runningEvent.remove(eventId);
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

    public synchronized MonitorStatisticsTable getStatistics() {
        MonitorStatisticsTable dataTable = new MonitorStatisticsTable();

        for (MonitorStatistics monitorStatistics : statistics.values()) {
            MonitorStatisticsRow row = dataTable.add();
            row.copyFromDataSource(MonitorMapper.INSTANCE.monitorStatisticsToPojoRow(monitorStatistics));
        }

        dataTable.first();
        return dataTable;
    }

    public synchronized MonitorTrendTable getTrend() throws FrameworkException {
        return getTrend(null, null);
    }

    public synchronized MonitorTrendTable getTrend(Timestamp dataDa, Timestamp dataA) throws FrameworkException {
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

        MonitorTrendTable dataTable = new MonitorTrendTable();
        for (Entry<Timestamp, Integer> entry : trend.entrySet()) {
            if (entry.getKey().compareTo(dataDa) >= 0 && entry.getKey().compareTo(dataA) < 0) {
                MonitorTrendRow row = dataTable.add();

                row.setHour(entry.getKey());
                row.setExecutions(BigDecimal.valueOf(entry.getValue()));
            }
        }

        dataTable.first();
        return dataTable;
    }
}

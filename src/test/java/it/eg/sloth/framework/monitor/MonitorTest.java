package it.eg.sloth.framework.monitor;

import it.eg.sloth.framework.monitor.model.MonitorMapper;
import it.eg.sloth.framework.monitor.model.MonitorStatisticsRow;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
 *
 * @author Enrico Grillini
 */
public class MonitorTest {

    @Test
    public void monitorStatisticsTest() {
        MonitorStatistics monitorStatistics = new MonitorStatistics("Page", "prova.page");
        assertEquals("page", monitorStatistics.getShortName());
    }


    @Test
    public void copyToPojoRowTest() {
        MonitorEvent monitorEvent = new MonitorEvent("Page", "prova.page", null);
        monitorEvent.start();
        Awaitility.await().pollDelay(Duration.ONE_MILLISECOND).until(() -> true);
        monitorEvent.end();

        MonitorStatistics monitorStatistics = new MonitorStatistics("Page", "prova.page");
        monitorStatistics.update(monitorEvent);

        MonitorStatisticsRow row = MonitorMapper.INSTANCE.monitorStatisticsToPojoRow(monitorStatistics);
        assertEquals("Page", row.getGroup());
        assertEquals("prova.page", row.getName());
        assertEquals("page", row.getShortName());

        assertEquals(BigDecimal.valueOf(1), row.getExecutions());
        assertNotEquals(BigDecimal.valueOf(0), row.getDuration());
        assertNotEquals(BigDecimal.valueOf(0), row.getAverage());
        assertNotEquals(BigDecimal.valueOf(0), row.getMax());
    }

}

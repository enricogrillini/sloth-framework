package it.eg.sloth.framework.monitor.model;

import it.eg.sloth.db.datasource.table.TableAbstract;

public class MonitorStatisticsTable extends TableAbstract<MonitorStatisticsRow> {
    
    @Override
    protected MonitorStatisticsRow createRow() {
        return new MonitorStatisticsRow();
    }

    @Override
    protected TableAbstract<MonitorStatisticsRow> newTable() {
        return new MonitorStatisticsTable();
    }

}

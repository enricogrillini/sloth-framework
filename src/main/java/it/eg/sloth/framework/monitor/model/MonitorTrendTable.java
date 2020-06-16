package it.eg.sloth.framework.monitor.model;

import it.eg.sloth.db.datasource.table.TableAbstract;

public class MonitorTrendTable extends TableAbstract<MonitorTrendRow> {

    @Override
    protected MonitorTrendRow createRow() {
        return new MonitorTrendRow();
    }

    @Override
    protected TableAbstract<MonitorTrendRow> newTable() {
        return new MonitorTrendTable();
    }

}

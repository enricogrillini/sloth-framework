package it.eg.sloth.db.model;

import it.eg.sloth.db.datasource.table.TableAbstract;

public class SamplePojoTable extends TableAbstract<SamplePojoRow> {
    @Override
    protected SamplePojoRow createRow() {
        return new SamplePojoRow();
    }

    @Override
    protected TableAbstract<SamplePojoRow> newTable() {
        return new SamplePojoTable();
    }


}

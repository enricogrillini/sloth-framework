package it.eg.sloth.framework.monitor.model;

import it.eg.sloth.db.datasource.row.PojoRow;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class MonitorTrendRow extends PojoRow {

    Timestamp time;
    BigDecimal executions;

}

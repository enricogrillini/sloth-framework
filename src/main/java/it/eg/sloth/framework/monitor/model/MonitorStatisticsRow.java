package it.eg.sloth.framework.monitor.model;

import it.eg.sloth.db.datasource.row.PojoRow;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MonitorStatisticsRow extends PojoRow {

    String group;
    String name;
    BigDecimal executions;
    BigDecimal duration;
    BigDecimal average;
    BigDecimal max;

}

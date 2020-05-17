package it.eg.sloth.framework.monitor;

import java.math.BigDecimal;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.Getter;

@Getter
public class MonitorStatistics {

  String group;
  String name;
  long executions;
  long duration;
  long max;

  public MonitorStatistics(String group, String name) {
    this.group = group;
    this.name = name;
    this.executions = 0;
    this.duration = 0;
    this.max = 0;
  }

  public String getShortName() {
    if (BaseFunction.isBlank(getName())) {
      return StringUtil.EMPTY;
    }

    if (getName().contains(".")) {
      return getName().substring(getName().lastIndexOf(".") + 1);
    } else {
      return getName();
    }
  }

  public void update(MonitorEvent monitorEvent) {
    long eventDuration = monitorEvent.getEnd() - monitorEvent.getStart();

    this.duration += eventDuration;
    this.executions++;
    this.max = eventDuration > this.max ? eventDuration : this.max;
  }

  public void populateRow(DataSource dataSource) {
    dataSource.setString("name", getName());
    dataSource.setString("shortName", getShortName());
    dataSource.setString("group", getGroup());
    dataSource.setBigDecimal("duration", BigDecimal.valueOf((double) getDuration() / 1000));
    dataSource.setBigDecimal("executions", BigDecimal.valueOf(getExecutions()));
    dataSource.setBigDecimal("average", BigDecimal.valueOf((double) getDuration() / (getExecutions() == 0 ? 1 : getExecutions() ) / 1000));
    dataSource.setBigDecimal("max", BigDecimal.valueOf((double) getMax() / 1000));
  }

}

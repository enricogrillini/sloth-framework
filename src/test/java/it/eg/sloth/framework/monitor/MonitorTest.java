package it.eg.sloth.framework.monitor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.eg.sloth.db.datasource.row.Row;

public class MonitorTest {

  @Test
  public void populateRowTest() {
    MonitorStatistics monitorStatistics = new MonitorStatistics("Page", "prova.page");

    Row row = new Row();
    monitorStatistics.populateRow(row);

    assertEquals("page", row.getString("shortname"));
  }

}

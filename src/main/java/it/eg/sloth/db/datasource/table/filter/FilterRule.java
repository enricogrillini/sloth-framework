package it.eg.sloth.db.datasource.table.filter;

import it.eg.sloth.db.datasource.DataSource;

public interface FilterRule  {

  public boolean check(DataSource dataSource);

}

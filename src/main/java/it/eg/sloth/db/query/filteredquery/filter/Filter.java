package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Filter {

  public String getWhereCondition();

  public int addValues(PreparedStatement statement, int i) throws SQLException;

}

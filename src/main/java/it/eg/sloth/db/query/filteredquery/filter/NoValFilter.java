package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.eg.sloth.framework.FrameComponent;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
public class NoValFilter extends FrameComponent implements Filter {

  String sql;

  public NoValFilter(String sql) {
    this.sql = sql;
  }

  @Override
  public String getWhereCondition() {
    return getSql();
  }

  @Override
  public int addValues(PreparedStatement statement, int i) throws SQLException {
    return i;
  }

}

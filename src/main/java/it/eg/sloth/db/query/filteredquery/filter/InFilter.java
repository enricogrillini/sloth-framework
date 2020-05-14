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
public class InFilter extends FrameComponent implements Filter {

  int sqlTypes;
  Object[] values;
  String sql;

  public InFilter(String sql, int sqlTypes, Object... values) {
    this.sql = sql;
    this.sqlTypes = sqlTypes;
    this.values = values;
  }

  @Override
  public String getWhereCondition() {
    if (getValues().length > 0) {
      StringBuilder inStatement = new StringBuilder(getSql() + " In (");
      for (int i = 0; i < getValues().length; i++) {
        inStatement.append(i == 0 ? "?" : ", ?");
      }
      inStatement.append(")");

      return inStatement.toString();

    } else {
      return "";
    }
  }

  @Override
  public int addValues(PreparedStatement statement, int i) throws SQLException {
    for (Object object : getValues()) {
      statement.setObject(i++, object, getSqlTypes());
    }

    return i;
  }

}

package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.eg.sloth.framework.FrameComponent;

/**
 * 
 * @author Enrico Grillini
 *
 */
public class BaseFilter extends FrameComponent implements Filter {
  
  private String sql;
  private int sqlTypes;
  private Object value;

  public BaseFilter(String sql, int sqlTypes, Object value) {
    this.sql = sql;
    this.sqlTypes = sqlTypes;
    this.value = value;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public int getSqlTypes() {
    return sqlTypes;
  }

  public void setSqlTypes(int sqlTypes) {
    this.sqlTypes = sqlTypes;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getWhereCondition() {
    if (getValue() != null && !"".equals(getValue())) {
      return getSql();
    } else {
      return "";
    }
  }

  public int addValues(PreparedStatement statement, int i) throws SQLException {
    if (getValue() != null && !"".equals(getValue())) {
      statement.setObject(i++, getValue(), getSqlTypes());
    }

    return i;
  }

}

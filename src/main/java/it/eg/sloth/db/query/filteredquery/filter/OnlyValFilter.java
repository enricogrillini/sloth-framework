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
@Setter
@Getter
public class OnlyValFilter extends FrameComponent implements Filter {

  int sqlTypes;
  Object value;

  public OnlyValFilter(int sqlTypes, Object value) {
    this.sqlTypes = sqlTypes;
    this.value = value;
  }

  public String getWhereCondition() {
    return "";
  }

  public int addValues(PreparedStatement statement, int i) throws SQLException {
    statement.setObject(i++, getValue(), getSqlTypes());

    return i;
  }

}

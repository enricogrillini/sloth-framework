package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
@AllArgsConstructor
public class TextSearch extends FrameComponent implements Filter {

  String sql;
  String value;

  private String getSearchString() {
    if (value == null || value.equals(""))
      return "";

    String result = "";
    for (int i = 0; i < value.length(); i++) {
      char c = value.toUpperCase().charAt(i);

      if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90)) {
        result += c;
      } else {
        result += ' ';
      }
    }

    return result;
  }

  @Override
  public String getWhereCondition() {
    String result = "";
    List<String> list = StringUtil.words(getSearchString());

    for (int i = 0; i < list.size(); i++) {
      if ("".equals(result))
        result = "(";

      result += result.equals("(") ? "" : " And ";
      result += getSql();
    }

    if (!"".equals(result))
      result += ")";

    return result;
  }

  @Override
  public int addValues(PreparedStatement statement, int i) throws SQLException {
    List<String> list = StringUtil.words(getSearchString());

    for (String string : list) {
      statement.setObject(i++, string, Types.VARCHAR);
    }

    return i;
  }

  public int getSqlTypes() {
    return Types.VARCHAR;
  }

  public int getParameterCount() {
    return StringUtil.words(getSearchString()).size();
  }

  public Object getParameterValue(int i) {
    return StringUtil.words(getSearchString()).get(i);
  }

}

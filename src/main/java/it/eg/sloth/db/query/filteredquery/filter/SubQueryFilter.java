package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.eg.sloth.db.query.filteredquery.FilteredQuery;
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
public class SubQueryFilter extends FrameComponent implements Filter {

  FilteredQuery subQuery;

  public SubQueryFilter(FilteredQuery subQuery) {
    this.subQuery = subQuery;
  }

  @Override
  public String getWhereCondition() {
    return subQuery.getStatement();
  }

  @Override
  public int addValues(PreparedStatement statement, int i) throws SQLException {
    i = subQuery.addValues(statement, i);

    return i;
  }

}

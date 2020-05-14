package it.eg.sloth.db.datasource.table.filter;

import java.util.List;
import java.util.Stack;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.table.filter.impl.TextSearchRule;
import it.eg.sloth.db.datasource.table.filter.impl.ValueRule;
import it.eg.sloth.framework.FrameComponent;

/**
 * 
 * @author Enrico Grillini
 * 
 */
public class FilterRules extends FrameComponent {

  private List<FilterRule> list;

  public FilterRules() {
    list = new Stack<>();
  }

  public void clear() {
    list.clear();
  }

  public int size() {
    return list.size();
  }

  public void add(FilterRule filterRule) {
    list.add(filterRule);
  }

  public void addValueRule(String fieldName, Object fieldValue) {
    list.add(new ValueRule(fieldName, fieldValue));
  }

  public void addTextSearchRule(String textSearh) {
    list.add(new TextSearchRule(textSearh));
  }

  public boolean check(DataSource dataSource) {
    for (FilterRule filterRule : list) {
      if (!filterRule.check(dataSource)) {
        return false;
      }
    }

    return true;
  }

}

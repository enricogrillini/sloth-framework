package it.eg.sloth.db.datasource.table.sort;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.framework.FrameComponent;

public class SortingRules<T extends DataRow> extends FrameComponent implements Comparator<T> {

  private List<SortingRule> list;

  public SortingRules() {
    list = new ArrayList<SortingRule>();
  }

  public void clear() {
    list.clear();
  }

  public SortingRule getFirstRule() {
    for (SortingRule sortingRule : list) {
      return sortingRule;
    }

    return null;
  }
  
  public SortingRule add(String fieldName) {
    SortingRule sortingRule = new SortingRule(fieldName, SortingRule.SORT_ASC_NULLS_LAST);
    list.add(sortingRule);

    return sortingRule;
  }

  public SortingRule add(String fieldName, int sortType) {
    SortingRule sortingRule = new SortingRule(fieldName, sortType);
    list.add(sortingRule);

    return sortingRule;
  }

  @Override
  public int compare(T o1, T o2) {
    DataRow row1 = (DataRow) o1;
    DataRow row2 = (DataRow) o2;

    for (SortingRule sortingRule : list) {
      Object value1 = row1.getObject(sortingRule.getFieldName());
      Object value2 = row2.getObject(sortingRule.getFieldName());

      if (value1 == null && value2 == null)
        continue;

      if (value1 != null && value2 == null) {
        if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_DESC_NULLS_LAST)
          return -1;
        else
          return 1;
      }

      if (value1 == null && value2 != null) {
        if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_DESC_NULLS_LAST)
          return 1;
        else
          return -1;
      }

      if (value1 instanceof BigDecimal) {
        BigDecimal bigDecimal1 = (BigDecimal) value1;
        BigDecimal bigDecimal2 = (BigDecimal) value2;

        if (bigDecimal1.equals(bigDecimal2))
          continue;
        else if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_FIRST)
          return bigDecimal1.compareTo(bigDecimal2);
        else
          return -bigDecimal1.compareTo(bigDecimal2);
      }

      if (value1 instanceof Timestamp) {
        Timestamp timestamp1 = (Timestamp) value1;
        Timestamp timestamp2 = (Timestamp) value2;

        if (timestamp1.equals(timestamp2))
          continue;
        else if (sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_LAST || sortingRule.getSortType() == SortingRule.SORT_ASC_NULLS_FIRST)
          return timestamp1.compareTo(timestamp2);
        else
          return -timestamp1.compareTo(timestamp2);
      }

      if (value1 instanceof String) {
        String string1 = (String) value1;
        String string2 = (String) value2;

        if (string1.equals(string2))
          continue;
        else
          return string1.compareTo(string2) * sortingRule.getSortType();
      }
    }

    return 0;
  }
}

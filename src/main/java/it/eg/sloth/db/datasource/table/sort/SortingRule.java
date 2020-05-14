package it.eg.sloth.db.datasource.table.sort;

import it.eg.sloth.framework.FrameComponent;

public class SortingRule extends FrameComponent {

  public static final int SORT_ASC_NULLS_LAST = 1;
  public static final int SORT_DESC_NULLS_LAST = -1;

  public static final int SORT_ASC_NULLS_FIRST = 2;
  public static final int SORT_DESC_NULLS_FIRST = -2;

  private String fieldName;
  private int sortType;

  public SortingRule(String fieldName, int sortType) {
    super();
    this.fieldName = fieldName;
    this.sortType = sortType;
  }

  public SortingRule(String fieldName) {
    this(fieldName, SORT_ASC_NULLS_LAST);
  }

  /**
   * @return the columnName
   */
  public String getFieldName() {
    return fieldName;
  }

  /**
   * @param columnName
   */
  public void setFieldName(String columnName) {
    this.fieldName = columnName;
  }

  /**
   * @return the sortType
   */
  public int getSortType() {
    return sortType;
  }

  /**
   * @param sortType
   */
  public void setSortType(int sortType) {
    this.sortType = sortType;
  }

}

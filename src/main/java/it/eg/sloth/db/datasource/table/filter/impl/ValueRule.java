package it.eg.sloth.db.datasource.table.filter.impl;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.table.filter.FilterRule;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.BaseFunction;

public class ValueRule extends FrameComponent implements FilterRule {

  private String fieldName;
  private Object fieldValue;

  public ValueRule(String fieldName, Object fieldValue) {
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }

  public void setFieldValue(Object fieldValue) {
    this.fieldValue = fieldValue;
  }

  @Override
  public boolean check(DataSource dataSource) {
    return BaseFunction.equals(getFieldValue(), dataSource.getObject(getFieldName()));
  }

}

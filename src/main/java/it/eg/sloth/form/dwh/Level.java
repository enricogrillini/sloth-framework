package it.eg.sloth.form.dwh;

import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.DecodedTextField;
import it.eg.sloth.framework.common.casting.DataTypes;

public class Level<T> extends DecodedTextField<T> {

  

  private int sortType;

  public Level(String name, String alias, String description, String tootip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tootip, dataType, format, baseLink);
    this.sortType = SortingRule.SORT_ASC_NULLS_LAST;
  }

  public int getSortType() {
    return sortType;
  }

  public void setSortType(int sortType) {
    this.sortType = sortType;
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.LEVEL;
  }
  
}

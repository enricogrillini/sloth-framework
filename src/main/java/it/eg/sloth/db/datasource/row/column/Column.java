package it.eg.sloth.db.datasource.row.column;

public class Column {
  private String name;
  private boolean primaryKey;
  private boolean nullable;
  private int javaType;

  public Column(String name, boolean primaryKey, boolean nullable, int javaType) {
    super();
    this.name = name;
    this.nullable = nullable;
    this.primaryKey = primaryKey;
    this.javaType = javaType;
  }

  public String getName() {
    return name;
  }

  public boolean isNullable() {
    return nullable;
  }

  public boolean isPrimaryKey() {
    return primaryKey;
  }

  public int getJavaType() {
    return javaType;
  }

  public void setJavaType(int javaType) {
    this.javaType = javaType;
  }
}

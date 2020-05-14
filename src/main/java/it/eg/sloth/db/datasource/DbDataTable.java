package it.eg.sloth.db.datasource;

public interface DbDataTable<T extends DbDataRow> extends DbDataSource, TransactionalDataTable<T> {

  boolean isAutoloadLob();

  void setAutoloadLob(boolean autoloadLob);

}

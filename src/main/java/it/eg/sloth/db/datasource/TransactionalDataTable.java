package it.eg.sloth.db.datasource;


public interface TransactionalDataTable<T extends TransactionalDataRow> extends TransactionalDataSource, DataTable<T> {
  
}

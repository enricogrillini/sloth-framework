package it.eg.sloth.db.model;

import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.db.datasource.table.DbTable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TableBean per la tabella Prova
 *
 */
public class ProvaTableBean extends DbTable<ProvaRowBean> {

  

  public static final String SELECT = "Select * from Prova /*W*/";
  public static final String TABLE_NAME = "PROVA";

  @Override
  protected ProvaRowBean createRow () {
    ProvaRowBean rowBean = new ProvaRowBean();
    rowBean.setAutoloadLob(isAutoloadLob());
    return rowBean;
  }

  @Override
  protected ProvaTableBean newTable() {
    return new ProvaTableBean();
  }

  public void load(ProvaRowBean provarowbean) throws SQLException, IOException, FrameworkException {
    load(SELECT, ProvaRowBean.columns, provarowbean);
  }

  public void load(ProvaRowBean provarowbean, Connection connection) throws SQLException, IOException, FrameworkException {
    load(SELECT, ProvaRowBean.columns, provarowbean, connection);
  }

  public static class Factory {

    public static ProvaTableBean load(ProvaRowBean rowBean, int pageSize, Connection connection) throws SQLException, IOException, FrameworkException {
      ProvaTableBean tableBean = new ProvaTableBean();
      tableBean.load(rowBean, connection);
      tableBean.setPageSize(pageSize);
      return tableBean;
    }

    public static ProvaTableBean load(ProvaRowBean rowBean, int pageSize)throws SQLException, IOException, FrameworkException {
      return load(rowBean, pageSize, null);
    }

    public static ProvaTableBean load(ProvaRowBean rowBean) throws SQLException, IOException, FrameworkException {
      return load(rowBean, -1);
    }

    public static ProvaTableBean loadFromQuery(SelectQueryInterface query, int pageSize) throws SQLException, IOException, FrameworkException {
      ProvaTableBean tableBean = new ProvaTableBean();
      tableBean.loadFromQuery(query);
      tableBean.setPageSize(pageSize);
      return tableBean;
    }

    public static ProvaTableBean loadFromQuery(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
      return loadFromQuery (query, -1);
    }
  }

}
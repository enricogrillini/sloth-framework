package it.eg.sloth.db.query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.framework.FrameComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Enrico Grillini
 * 
 */
@Slf4j
public abstract class SelectAbstractQuery extends FrameComponent implements SelectQueryInterface {

  

  private String statement;

  public SelectAbstractQuery(String statement) {
    this.statement = statement;
  }

  /**
   * Ritorna lo statement
   * 
   * @param connection
   * @return
   * @throws SQLException
   */
  protected abstract PreparedStatement getPreparedStatement(Connection connection) throws SQLException;

  @Override
  public String getStatement() {
    return statement;
  }

  @Override
  public void setStatement(String statement) {
    this.statement = statement;
  }

  @Override
  public DataTable<? extends DataRow> selectTable() throws SQLException, IOException {
    Table table = new Table();
    populateDataTable(table);
    return table;
  }

  @Override
  public DataTable<?> selectTable(String connectionName) throws SQLException, IOException {
    Table table = new Table();
    populateDataTable(table, connectionName);
    return table;
  }

  @Override
  public DataTable<?> selectTable(Connection connection) throws SQLException, IOException {
    Table table = new Table();
    populateDataTable(table, connection);
    return table;
  }

  @Override
  public DataRow selectRow() throws SQLException, IOException {
    DataRow dataRow = new Row();
    return populateDataRow(dataRow) ? dataRow : null;
  }

  @Override
  public DataRow selectRow(String connectionName) throws SQLException, IOException {
    DataRow dataRow = new Row();
    return populateDataRow(dataRow, connectionName) ? dataRow : null;
  }

  @Override
  public DataRow selectRow(Connection connection) throws SQLException, IOException {
    DataRow dataRow = new Row();
    return populateDataRow(dataRow, connection) ? dataRow : null;
  }

  @Override
  public boolean populateDataRow(DataRow dataRow, String connectionName) throws SQLException, IOException {
    Connection connection = null;

    try {
      connection = DataConnectionManager.getInstance().getConnection(connectionName);
      return populateDataRow(dataRow, connection);

    } finally {
      DataConnectionManager.release(connection);
    }
  }

  @Override
  public boolean populateDataRow(DataRow dataRow) throws SQLException, IOException {
    return populateDataRow(dataRow, (Connection) null);
  }

  @Override
  public boolean populateDataRow(DataRow dataRow, Connection connection) throws SQLException, IOException {
    if (connection == null) {
      try {
        connection = DataConnectionManager.getInstance().getConnection();
        return populateDataRow(dataRow, connection);

      } finally {
        DataConnectionManager.release(connection);
      }
    } else {
      log.debug("Start populateDataRow");
      log.debug(toString());

      boolean result = false;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        log.debug("Start populateDataRow");
        log.debug(toString());

        preparedStatement = getPreparedStatement(connection);
        resultSet = preparedStatement.executeQuery();

        result = resultSet.next();
        if (result) {
          dataRow.loadFromResultSet(resultSet);
        } else {
          dataRow.clear();
        }
      } catch (SQLException | IOException e) {
        log.info("Errore sulla query: {}", toString());
        throw e;

      } finally {
        DataConnectionManager.release(resultSet);
        DataConnectionManager.release(preparedStatement);
      }

      log.debug("End populateDataRow ({})", result);
      return result;
    }
  }

  @Override
  public void populateDataTable(DataTable<?> dataTable, String connectionName) throws SQLException, IOException {
    Connection connection = null;

    try {
      connection = DataConnectionManager.getInstance().getConnection(connectionName);
      populateDataTable(dataTable, connection);

    } finally {
      DataConnectionManager.release(connection);
    }
  }

  @Override
  public void populateDataTable(DataTable<?> dataTable) throws SQLException, IOException {
    populateDataTable(dataTable, (Connection) null);
  }

  @Override
  public void populateDataTable(DataTable<?> dataTable, Connection connection) throws SQLException, IOException {
    if (connection == null) {
      try {
        connection = DataConnectionManager.getInstance().getConnection();
        populateDataTable(dataTable, connection);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      } finally {
        DataConnectionManager.release(connection);
      }
    } else {
      log.debug("Start populateDataTable");
      log.debug(toString());

      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        preparedStatement = getPreparedStatement(connection);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          dataTable.append().loadFromResultSet(resultSet);
        }
      } catch (SQLException | IOException e) {
        log.info("Errore sulla query: {}", toString());
        throw e;

      } finally {
        DataConnectionManager.release(resultSet);
        DataConnectionManager.release(preparedStatement);
      }

      log.debug("End populateDataTable");
    }
  }

}

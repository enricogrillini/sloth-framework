package it.eg.sloth.db.query.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.db.query.SelectAbstractQuery;
import it.eg.sloth.db.query.SelectQueryInterface;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Clase per governare le query Oracle
 * 
 * @author Enrico Grillini
 * 
 */
@Slf4j
public class Query extends SelectAbstractQuery implements SelectQueryInterface {

  private List<Parameter> parameterList;

  public Query(String statement) {
    super(statement);
    this.parameterList = new ArrayList<Parameter>();
  }

  @Override
  protected PreparedStatement getPreparedStatement(Connection connection) throws SQLException {
    if (connection == null) {
      try {
        connection = DataConnectionManager.getInstance().getConnection();
        return getPreparedStatement(connection);

      } finally {
        DataConnectionManager.release(connection);
      }
    } else {
      PreparedStatement preparedStatement = connection.prepareStatement(getStatement(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

      int i = 1;
      for (Parameter parameter : parameterList) {
        preparedStatement.setObject(i++, parameter.getValue(), parameter.getSqlType());
      }

      return preparedStatement;
    }
  }

  public void addParameter(int sqlTypes, Object value) {
    parameterList.add(new Parameter(sqlTypes, value));
  }

  public void execute() throws SQLException {
    execute((Connection) null);
  }

  public void execute(String connectionName) throws SQLException {
    Connection connection = null;

    try {
      connection = DataConnectionManager.getInstance().getConnection(connectionName);
      execute(connection);

    } finally {
      DataConnectionManager.release(connection);
    }
  }

  public void execute(Connection connection) throws SQLException {
    if (connection == null) {
      try {
        connection = DataConnectionManager.getInstance().getConnection();
        execute(connection);

      } finally {
        DataConnectionManager.release(connection);
      }
    } else {
      log.debug("Start execute");
      log.debug(toString());

      PreparedStatement preparedStatement = null;
      try {
        preparedStatement = getPreparedStatement(connection);
        preparedStatement.executeUpdate();
      } finally {
        DataConnectionManager.release(preparedStatement);
      }

      log.debug("End execute");
    }
  }

}

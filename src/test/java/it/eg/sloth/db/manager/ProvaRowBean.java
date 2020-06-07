package it.eg.sloth.db.manager;

import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.db.datasource.row.DbRow;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.RowStatus;
import it.eg.sloth.db.datasource.row.lob.BLobData;
import it.eg.sloth.db.datasource.row.lob.CLobData;
import it.eg.sloth.db.datasource.row.column.Column;
import org.apache.commons.io.IOUtils;
import it.eg.sloth.db.manager.DataConnectionManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * RowBean per la tabella Prova
 *
 */
public class ProvaRowBean extends DbRow {

  

  public static final String ID = "id";
  public static final String TESTO = "testo";
  public static final String DATA = "data";

  public static final Column[] columns = {
    new Column (ID, false, false, Types.DECIMAL),
    new Column (TESTO, false, false, Types.VARCHAR),
    new Column (DATA, false, false, Types.DATE)
  };

  private static String strSelect = "Select *\n" +
                                    "From Prova\n" +
                                    "Where Id = ?";

  private static String strInsert = "Insert into Prova\n" +
                                    "      (Id,\n" +
                                    "       Testo,\n" +
                                    "       Data)\n" +
                                    "Values (?,\n" +
                                    "        ?,\n" +
                                    "        ?)";

  private static String strDelete = "Delete Prova\n" +
                                    "Where Id = ?";

  private static String strUpdate = "Update Prova\n" +
                                    "Set Id = ?,\n" +
                                    "    Testo = ?,\n" +
                                    "    Data = ?\n" +
                                    "Where Id = ?";

  public ProvaRowBean () {
    super();
  }

  public ProvaRowBean (BigDecimal Id) throws SQLException, IOException, FrameworkException {
    this(null, Id);
  }

  public ProvaRowBean (Connection connection, BigDecimal Id) throws SQLException, IOException, FrameworkException {
    this();
    setId(Id);
    select(connection);
  }

  @Override   public Column[] getColumns() {    return columns;   }
  @Override  public String getSelect() {
     return strSelect;
  }

  @Override  public String getInsert() {
     return strInsert;
  }

  @Override  public String getDelete() {
     return strDelete;
  }

  @Override  public String getUpdate() {
     return strUpdate;
  }

  public BigDecimal getId() {
    return getBigDecimal(ID);
  }

  public BigDecimal getOldId() {
    return getOldBigDecimal(ID);
  }

  public void setId(BigDecimal id) {
    setObject(ID, id);
  }

  public String getTesto() {
    return getString(TESTO);
  }

  public String getOldTesto() {
    return getOldString(TESTO);
  }

  public void setTesto(String testo) {
    setObject(TESTO, testo);
  }

  public Timestamp getData() {
    return getTimestamp(DATA);
  }

  public Timestamp getOldData() {
    return getOldTimestamp(DATA);
  }

  public void setData(Timestamp data) {
    setObject(DATA, data);
  }

  private Query selectQuery() {
    Query query = new Query (getSelect());
    query.addParameter(Types.DECIMAL, getId());
    return query;
  }
  public boolean select(Connection connection) throws SQLException, IOException, FrameworkException {
    return loadFromQuery(selectQuery(), connection);
  }

  private void updateLob(Connection connection) {
  }

  public void update(Connection connection) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(getUpdate());
      preparedStatement.setObject (1, getId(), Types.DECIMAL);
      preparedStatement.setObject (2, getTesto(), Types.VARCHAR);
      preparedStatement.setObject (3, getData(), Types.DATE);
      preparedStatement.setObject (4, getOldId(), Types.DECIMAL);

      preparedStatement.executeUpdate();
      preparedStatement.close();

      updateLob (connection);
    } catch (Throwable e) {
      throw new RuntimeException (e);
    }
  }

  public void insert(Connection connection) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(getInsert());
      preparedStatement.setObject (1, getId(), Types.DECIMAL);
      preparedStatement.setObject (2, getTesto(), Types.VARCHAR);
      preparedStatement.setObject (3, getData(), Types.DATE);

      preparedStatement.executeUpdate();
      preparedStatement.close() ;

      updateLob (connection);
    } catch (Throwable e) {
      throw new RuntimeException (e);
    }
  }

  public void delete(Connection connection) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(getDelete());
      preparedStatement.setObject (1, getOldId(), Types.DECIMAL);

      preparedStatement.executeUpdate();
      preparedStatement.close() ;
    } catch (Throwable e) {
      throw new RuntimeException (e);
    }
  }
  public static class Factory {

    public static ProvaRowBean load(SelectQueryInterface selectQueryInterface) throws SQLException, IOException, FrameworkException {
      ProvaRowBean provarowbean = new ProvaRowBean();
      provarowbean.loadFromQuery(selectQueryInterface);
      return provarowbean;
    }

  }
}

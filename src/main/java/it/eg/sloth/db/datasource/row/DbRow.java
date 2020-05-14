package it.eg.sloth.db.datasource.row;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import it.eg.sloth.db.datasource.DbDataRow;
import it.eg.sloth.db.datasource.RowStatus;
import it.eg.sloth.db.datasource.row.lob.BLobData;
import it.eg.sloth.db.datasource.row.lob.CLobData;
import it.eg.sloth.db.manager.DataConnectionManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe astratta da utilizzare come base per i bean
 *
 * @author Enrico Grillini
 */
@Slf4j
public abstract class DbRow extends TransactionalRow implements DbDataRow {

    private boolean autoloadLob;

    public DbRow() {
        super();
        this.autoloadLob = false;
    }

    @Override
    protected void copyFromResultSet(ResultSet resultSet, int i) throws SQLException, IOException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        switch (resultSetMetaData.getColumnType(i)) {
            case Types.BLOB:
                super.setObject(resultSetMetaData.getColumnName(i), new BLobData(isAutoloadLob(), resultSet.getBlob(i)));
                break;

            case Types.CLOB:
                super.setObject(resultSetMetaData.getColumnName(i), new CLobData(isAutoloadLob(), resultSet.getClob(i)));
                break;

            default:
                super.copyFromResultSet(resultSet, i);
                break;
        }
    }

    @Override
    public void save() {
        // Controllo lo stato
        switch (getStatus()) {
            case CLEAN:
                return;

            case INSERTED:
            case DELETED:
            case UPDATED:
                break;

            default:
                throw new RuntimeException("save: " + getStatus());
        }

        // Effettuo il salvataggio
        Connection connection = null;
        try {
            connection = DataConnectionManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            post(connection);

            connection.commit();
            commit();

        } catch (Throwable e) {
            DataConnectionManager.rollback(connection);
            throw new RuntimeException(e);
        } finally {
            DataConnectionManager.release(connection);
        }
    }

    @Override
    public void post(Connection connection) {
        switch (getStatus()) {
            case CLEAN:
            case INCONSISTENT:
                break;

            case INSERTED:
                insert(connection);
                setStatus(RowStatus.POST_INSERT);
                break;

            case DELETED:
                delete(connection);
                setStatus(RowStatus.POST_DELETE);
                break;

            case UPDATED:
                update(connection);
                setStatus(RowStatus.POST_UPDATE);
                break;

            default:
                throw new RuntimeException("post: " + getStatus());
        }
    }

    @Override
    public void unPost() {
        switch (getStatus()) {
            case CLEAN:
            case INCONSISTENT:
            case INSERTED:
            case DELETED:
            case UPDATED:
                break;

            case POST_INSERT:
                setStatus(RowStatus.INSERTED);
                break;

            case POST_DELETE:
                setStatus(RowStatus.DELETED);
                break;

            case POST_UPDATE:
                setStatus(RowStatus.UPDATED);
                break;

            default:
                throw new RuntimeException("unPost: " + getStatus());
        }
    }

    @Override
    public void commit() {
        switch (getStatus()) {
            case CLEAN:
            case INCONSISTENT:
                break;

            case POST_INSERT:
                setStatus(RowStatus.CLEAN);
                break;

            case POST_DELETE:
                setStatus(RowStatus.INCONSISTENT);
                break;

            case POST_UPDATE:
                setStatus(RowStatus.CLEAN);
                break;

            default:
                throw new RuntimeException("commit: " + getStatus());
        }

        // salvo gli oldValues
        oldValues = new HashMap<String, Object>(values);
    }

    @Override
    public void rollback() {
        switch (getStatus()) {
            case CLEAN:
            case INSERTED:
            case DELETED:
            case UPDATED:
                break;

            case POST_INSERT:
                setStatus(RowStatus.INSERTED);
                break;

            case POST_DELETE:
                setStatus(RowStatus.DELETED);
                break;

            case POST_UPDATE:
                setStatus(RowStatus.UPDATED);
                break;

            default:
                throw new RuntimeException(" - rollback: " + getStatus());
        }
    }

    @Override
    public boolean select() {
        try {
            Connection connection = null;
            try {
                connection = DataConnectionManager.getInstance().getConnection();
                return select(connection);
            } finally {
                DataConnectionManager.release(connection);
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert() {
        Connection connection = null;
        try {
            connection = DataConnectionManager.getInstance().getConnection();
            insert(connection);

        } catch (Throwable e) {
            DataConnectionManager.rollback(connection);
        } finally {
            DataConnectionManager.release(connection);
        }
    }

    @Override
    public void delete() {
        Connection connection = null;
        try {
            connection = DataConnectionManager.getInstance().getConnection();
            delete(connection);

        } catch (Throwable e) {
            DataConnectionManager.rollback(connection);
        } finally {
            DataConnectionManager.release(connection);
        }
    }

    @Override
    public void update() {
        Connection connection = null;
        try {
            connection = DataConnectionManager.getInstance().getConnection();
            update(connection);
        } catch (Throwable e) {
            DataConnectionManager.rollback(connection);
        } finally {
            DataConnectionManager.release(connection);
        }
    }

    @Override
    public boolean isAutoloadLob() {
        return autoloadLob;
    }

    @Override
    public void setAutoloadLob(boolean autoloadLob) {
        this.autoloadLob = autoloadLob;
    }

}

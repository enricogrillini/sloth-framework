package it.eg.sloth.db.datasource.row;

import it.eg.sloth.db.datasource.DbDataRow;
import it.eg.sloth.db.datasource.RowStatus;
import it.eg.sloth.db.datasource.row.lob.BLobData;
import it.eg.sloth.db.datasource.row.lob.CLobData;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Slf4j
@Getter
@Setter
public abstract class DbRow extends TransactionalRow implements DbDataRow {

    private boolean autoloadLob;

    public DbRow() {
        super();
        this.autoloadLob = false;
    }

    @Override
    public void copyFromResultSet(ResultSet resultSet) throws SQLException, FrameworkException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            switch (resultSetMetaData.getColumnType(i)) {
                case Types.VARCHAR:
                case Types.CHAR:
                    setString(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                    break;

                case Types.BIT:
                case Types.SMALLINT:
                case Types.TINYINT:
                case Types.INTEGER:
                case Types.BIGINT:
                case Types.FLOAT:
                case Types.DECIMAL:
                case Types.REAL:
                case Types.DOUBLE:
                case Types.NUMERIC:
                    setBigDecimal(resultSetMetaData.getColumnName(i), resultSet.getBigDecimal(i));
                    break;

                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    setTimestamp(resultSetMetaData.getColumnName(i), resultSet.getTimestamp(i));
                    break;

                case Types.BLOB:
                    super.setObject(resultSetMetaData.getColumnName(i), new BLobData(isAutoloadLob(), resultSet.getBlob(i)));
                    break;

                case Types.CLOB:
                    super.setObject(resultSetMetaData.getColumnName(i), new CLobData(isAutoloadLob(), resultSet.getClob(i)));
                    break;

                default:
                    // Types {} non gestito. Utilizzato default.
                    setObject(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                    break;
            }
        }
    }

    @Override
    public void save() throws FrameworkException, SQLException {
        // Controllo lo stato
        switch (getStatus()) {
            case CLEAN:
                return;

            case INSERTED:
            case DELETED:
            case UPDATED:
                break;

            default:
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_SAVE, getStatus().name());
        }

        // Effettuo il salvataggio
        try (Connection connection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
            try {
                connection.setAutoCommit(false);
                post(connection);
                connection.commit();
                commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    @Override
    public void post(Connection connection) throws SQLException, FrameworkException {
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
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_POST, getStatus().name());
        }
    }

    @Override
    public void unPost() throws FrameworkException {
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
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_UNPOST, getStatus().name());
        }
    }

    @Override
    public void commit() throws FrameworkException {
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
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_COMMIT, getStatus().name());
        }

        // salvo gli oldValues
        oldValues = new HashMap<String, Object>(values);
    }

    @Override
    public void rollback() throws FrameworkException {
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
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_ROLLBACK, getStatus().name());
        }
    }

    @Override
    public boolean select() throws FrameworkException, SQLException, IOException {
        try (Connection connection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
            return select(connection);
        }
    }

}

package it.eg.sloth.db.datasource.row;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.RowStatus;
import it.eg.sloth.db.datasource.TransactionalDataRow;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
public class TransactionalRow extends Row implements TransactionalDataRow {

    protected Map<String, Object> oldValues;
    private RowStatus status;

    public TransactionalRow() {
        super();
        oldValues = new HashMap<>();
        status = RowStatus.INSERTED;
    }

    @Override
    public Object getOldObject(String name) {
        return oldValues.get(name.toLowerCase());
    }

    @Override
    public BigDecimal getOldBigDecimal(String name) {
        return (BigDecimal) oldValues.get(name.toLowerCase());
    }

    @Override
    public Timestamp getOldTimestamp(String name) {
        return (Timestamp) oldValues.get(name.toLowerCase());
    }

    @Override
    public String getOldString(String name) {
        return (String) oldValues.get(name.toLowerCase());
    }

    @Override
    public byte[] getOldByte(String name) {
        return (byte[]) oldValues.get(name.toLowerCase());
    }

    @Override
    public TransactionalRow setObject(String name, Object value) {
        super.setObject(name, value);
        if (status == RowStatus.CLEAN) {
            status = RowStatus.UPDATED;
        }

        return this;
    }

    @Override
    public void loadFromDataSource(DataSource dataSource) {
        super.loadFromDataSource(dataSource);
        oldValues = new HashMap<>(entries);
        status = RowStatus.CLEAN;
    }

    @Override
    public void loadFromResultSet(ResultSet resultSet) throws SQLException, FrameworkException {
        super.loadFromResultSet(resultSet);
        oldValues = new HashMap<>(entries);
        status = RowStatus.CLEAN;
    }

    @Override
    public void setFromQuery(SelectQueryInterface query) throws SQLException, FrameworkException {
        super.setFromQuery(query);
        if (status == RowStatus.CLEAN)
            status = RowStatus.UPDATED;
    }

    @Override
    public boolean loadFromQuery(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
        boolean result = super.loadFromQuery(query);
        oldValues = new HashMap<>(entries);
        status = RowStatus.CLEAN;

        return result;
    }

    @Override
    public boolean loadFromQuery(SelectQueryInterface query, Connection connection) throws SQLException, IOException, FrameworkException {
        boolean result = super.loadFromQuery(query, connection);
        oldValues = new HashMap<>(entries);
        status = RowStatus.CLEAN;

        return result;
    }

    @Override
    public RowStatus getStatus() {
        return status;
    }

    protected void setStatus(RowStatus status) {
        this.status = status;
    }

    @Override
    public void remove() throws FrameworkException {
        switch (getStatus()) {
            case INSERTED:
                setStatus(RowStatus.INCONSISTENT);
                break;

            case UPDATED:
                setStatus(RowStatus.DELETED);
                break;

            case CLEAN:
                setStatus(RowStatus.DELETED);
                break;

            default:
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_REMOVE, getStatus().name());
        }
    }

    @Override
    public void save() throws FrameworkException, SQLException {
        switch (getStatus()) {
            case INSERTED:
                setStatus(RowStatus.CLEAN);
                break;

            case DELETED:
                setStatus(RowStatus.INCONSISTENT);
                break;

            case UPDATED:
                setStatus(RowStatus.CLEAN);
                break;

            case CLEAN:
                setStatus(RowStatus.CLEAN);
                break;

            default:
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_SAVE, getStatus().name());
        }

        oldValues = new HashMap<>(entries);
    }

    @Override
    public void undo() throws FrameworkException {
        switch (getStatus()) {
            case CLEAN:
                break;

            case INSERTED:
                setStatus(RowStatus.INCONSISTENT);
                break;

            case DELETED:
                setStatus(RowStatus.CLEAN);
                break;

            case UPDATED:
                setStatus(RowStatus.CLEAN);
                break;

            default:
                throw new FrameworkException(ExceptionCode.TRANSACTION_EXCEPTION_UNDO, getStatus().name());
        }

        entries = new HashMap<String, Object>(oldValues);
    }

    @Override
    public void forceClean() {
        setStatus(RowStatus.CLEAN);
        oldValues = new HashMap<>(entries);
    }

}

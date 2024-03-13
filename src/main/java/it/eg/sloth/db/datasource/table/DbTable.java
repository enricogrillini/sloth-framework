package it.eg.sloth.db.datasource.table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import it.eg.sloth.db.datasource.DbDataRow;
import it.eg.sloth.db.datasource.DbDataTable;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.framework.common.exception.FrameworkException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public abstract class DbTable<T extends DbDataRow> extends TransactionalTableAbstract<T> implements DbDataTable<T> {

    private boolean autoloadLob;

    protected DbTable() {
        this.autoloadLob = false;
    }

    @Override
    public void post(Connection connection) throws SQLException, FrameworkException {
        for (T row : rowsDeleted) {
            row.post(connection);
        }

        for (T row : this) {
            row.post(connection);
        }
    }

    @Override
    public void unPost() throws FrameworkException {
        for (T row : rowsDeleted) {
            row.unPost();
        }

        for (T row : this) {
            row.unPost();
        }
    }

    @Override
    public void commit() throws FrameworkException {
        for (T row : rowsDeleted) {
            row.commit();
        }

        for (T row : this) {
            row.commit();
        }

        oldRows = new ArrayList<T>(rows);
        rowsDeleted = new HashSet<T>();
    }

    @Override
    public void rollback() throws FrameworkException {
        rows = new ArrayList<T>(oldRows);
        rowsDeleted = new HashSet<T>();

        for (T row : this) {
            row.rollback();
        }
    }

    @Override
    public void save() throws FrameworkException, SQLException {
        try (Connection connection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
            try {
                connection.setAutoCommit(false);
                post(connection);
                commit();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

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

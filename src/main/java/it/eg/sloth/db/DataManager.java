package it.eg.sloth.db;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.DbDataSource;
import it.eg.sloth.db.datasource.TransactionalDataSource;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
public class DataManager {
    private DataManager() {
        // NOP
    }

    private static void post(Connection connection, DataSource dataSource) throws SQLException, FrameworkException {
        if (dataSource instanceof DbDataSource) {
            DbDataSource dbDataRow = (DbDataSource) dataSource;
            dbDataRow.post(connection);
        }
    }

    private static void unPost(DataSource dataSource) throws FrameworkException {
        if (dataSource instanceof DbDataSource) {
            DbDataSource dbDataRow = (DbDataSource) dataSource;
            dbDataRow.unPost();
        }
    }

    private static void commit(DataSource dataSource) throws FrameworkException, SQLException {
        if (dataSource instanceof DbDataSource) {
            DbDataSource dbDataRow = (DbDataSource) dataSource;
            dbDataRow.commit();

        } else if (dataSource instanceof TransactionalDataSource) {
            TransactionalDataSource transactionalDataRow = (TransactionalDataSource) dataSource;
            transactionalDataRow.save();
        }
    }

    private static void undo(DataSource dataSources) throws FrameworkException {
        if (dataSources instanceof TransactionalDataSource) {
            TransactionalDataSource transactionalDataRow = (TransactionalDataSource) dataSources;
            transactionalDataRow.undo();
        }
    }

    public static void post(Connection connection, DataSource[] dataSources) throws SQLException, FrameworkException {
        for (DataSource dataSource : dataSources) post(connection, dataSource);
    }

    public static void unPost(DataSource[] dataSources) throws FrameworkException {
        for (DataSource dataSource : dataSources) unPost(dataSource);
    }

    public static void commit(DataSource[] dataSources) throws FrameworkException, SQLException {
        for (DataSource dataSource : dataSources) commit(dataSource);
    }

    public static void save(DataSource[] dataSources) throws SQLException, FrameworkException {
        try (Connection connection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
            try {
                connection.setAutoCommit(false);

                post(connection, dataSources);
                connection.commit();
                commit(dataSources);

            } catch (Exception e) {
                unPost(dataSources);
                connection.rollback();
                throw e;
            }
        }
    }

    public static void undo(DataSource[] dataSources) throws FrameworkException {
        for (DataSource dataSource : dataSources) undo(dataSource);
    }

    private static DataSource[] toDataSourceArray(List<DataSource> list) {
        DataSource[] dataSources = new DataSource[list.size()];

        int i = 0;
        for (DataSource dataSource : list) {
            dataSources[i++] = dataSource;
        }

        return dataSources;
    }

    private static DataSource[] toDataSourceArray(Form form, int inc) {
        Object[] object = form.getElements().toArray();
        int start = inc > 0 ? 0 : object.length - 1;
        int end = inc > 0 ? object.length : -1;

        List<DataSource> list = new ArrayList<>();
        for (int i = start; i != end; i += inc) {
            if (object[i] instanceof Fields<?>) {
                Fields<?> fields = (Fields<?>) object[i];

                if (fields.getDataSource() != null)
                    list.add(fields.getDataSource());
            }
        }
        return toDataSourceArray(list);
    }

    public static void saveFirstToLast(Form form) throws SQLException, FrameworkException {
        save(toDataSourceArray(form, 1));
    }

    public static void saveLastToFirst(Form form) throws SQLException, FrameworkException {
        save(toDataSourceArray(form, -1));
    }

    public static void undo(Form form) throws FrameworkException {
        undo(toDataSourceArray(form, 1));
    }

}

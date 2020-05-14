package it.eg.sloth.db;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.DbDataSource;
import it.eg.sloth.db.datasource.TransactionalDataSource;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.fields.Fields;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private DataManager() {
        // NOP
    }

    private static void post(Connection connection, DataSource dataSource) {
        if (dataSource instanceof DbDataSource) {
            DbDataSource dbDataRow = (DbDataSource) dataSource;
            dbDataRow.post(connection);
        }
    }

    private static void unPost(DataSource dataSource) {
        if (dataSource instanceof DbDataSource) {
            DbDataSource dbDataRow = (DbDataSource) dataSource;
            dbDataRow.unPost();
        }
    }

    private static void commit(DataSource dataSource) throws Exception {
        if (dataSource instanceof DbDataSource) {
            DbDataSource dbDataRow = (DbDataSource) dataSource;
            dbDataRow.commit();

        } else if (dataSource instanceof TransactionalDataSource) {
            TransactionalDataSource transactionalDataRow = (TransactionalDataSource) dataSource;
            transactionalDataRow.save();
        }
    }

    private static void undo(DataSource dataSources) {
        if (dataSources instanceof TransactionalDataSource) {
            TransactionalDataSource transactionalDataRow = (TransactionalDataSource) dataSources;
            transactionalDataRow.undo();
        }
    }

    public static void post(Connection connection, DataSource[] dataSources) {
        for (int i = 0; i < dataSources.length; i++)
            post(connection, dataSources[i]);
    }

    public static void unPost(DataSource[] dataSources) {
        for (int i = 0; i < dataSources.length; i++)
            unPost(dataSources[i]);
    }

    public static void commit(DataSource[] dataSources) throws Exception {
        for (int i = 0; i < dataSources.length; i++)
            commit(dataSources[i]);
    }

    public static void save(DataSource[] dataSources) throws Exception {
        Connection connection = null;
        try {
            connection = DataConnectionManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            post(connection, dataSources);
            connection.commit();
            commit(dataSources);

        } catch (Exception e) {
            unPost(dataSources);
            DataConnectionManager.rollback(connection);
            throw e;

        } finally {
            DataConnectionManager.release(connection);
        }
    }

    public static void undo(DataSource[] dataSources) {
        for (int i = 0; i < dataSources.length; i++)
            undo(dataSources[i]);
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

    public static void saveFirstToLast(Form form) throws Exception {
        save(toDataSourceArray(form, 1));
    }

    public static void saveLastToFirst(Form form) throws Exception {
        save(toDataSourceArray(form, -1));
    }

    public static void undo(Form form) {
        undo(toDataSourceArray(form, 1));
    }

}

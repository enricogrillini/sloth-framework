package it.eg.sloth.db.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class DataConnectionManager {

    private static DataConnectionManager instance = new DataConnectionManager();

    private static final String DEFAULT_CONNECTION_NAME = "defaultDB";

    private static final int MAX_ACTIVE = 100;
    private static final long MAX_WAIT = 10000;
    private static final int INITIAL_SIZE = 2;

    private Map<String, DataSource> dataSourceMap;

    private DataConnectionManager() {
        dataSourceMap = new HashMap<>();
    }

    public static DataConnectionManager getInstance() {
        return instance;
    }

    public synchronized void registerDefaultDataSource(String driverName, String url, String userName, String password) {
        registerDefaultDataSource(driverName, url, userName, password, MAX_ACTIVE, MAX_WAIT, INITIAL_SIZE);
    }

    public synchronized void registerDefaultDataSource(String driverName, String url, String userName, String password, int maxActive, long maxWait, int initialSize) {
        registerDataSource(DEFAULT_CONNECTION_NAME, driverName, url, userName, password, maxActive, maxWait, initialSize);
    }

    public synchronized void registerDataSource(String connectionName, String driverName, String url, String userName, String password) {
        registerDataSource(connectionName, driverName, url, userName, password, MAX_ACTIVE, MAX_WAIT, INITIAL_SIZE);
    }

    public synchronized void registerDataSource(String connectionName, String driverName, String url, String userName, String password, int maxActive, long maxWait, int initialSize) {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(driverName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setInitialSize(initialSize);

        dataSourceMap.put(connectionName, dataSource);
    }

    public synchronized void registerDataSource(DataSource dataSource) {
        dataSourceMap.put(DEFAULT_CONNECTION_NAME, dataSource);
    }

    public synchronized void registerDataSource(String connectionName, DataSource dataSource) {
        dataSourceMap.put(connectionName, dataSource);
    }

    public synchronized Connection getConnection(String connectionName) throws SQLException {
        Connection connection = null;

        if (dataSourceMap.containsKey(connectionName)) {
            DataSource dataSource = dataSourceMap.get(connectionName);

            connection = dataSource.getConnection();
            connection.setAutoCommit(true);

        } else {
            // Recupero la connection dal connection pool
            log.info("Recupero la connection {} dal connection pool", connectionName);
            try {
                Context context = new InitialContext();

                DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/" + connectionName);
                if (dataSource == null) {
                    throw new RuntimeException("DataSource: java:comp/env/jdbc/" + connectionName + " non trovato");
                } else {
                    dataSourceMap.put(connectionName, dataSource);
                }

                connection = dataSource.getConnection();
                connection.setAutoCommit(true);

            } catch (Exception e) {
                log.error("Error on get connection", e);
                throw new RuntimeException(e);
            }
        }

        return connection;

    }

    public synchronized Connection getConnection() throws SQLException {
        return getConnection(DEFAULT_CONNECTION_NAME);
    }

    /**
     * Rilascia la Connection gestendo le eccezioni
     *
     * @param connection
     * @throws SQLException
     */
    public static void release(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Rilascia il PreparedStatement gestendo le eccezioni
     *
     * @param preparedStatement
     */
    public static void release(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Rilascia il ResultSet gestendo le eccezioni
     *
     * @param resultSet
     */
    public static void release(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Effettua il rollback della transazione gestendo le eccezioni
     *
     * @param connection
     * @throws TransactionException
     */
    public static void rollback(Connection connection) {
        try {
            if (connection != null)
                connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

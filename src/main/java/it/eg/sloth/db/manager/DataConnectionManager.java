package it.eg.sloth.db.manager;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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

    public synchronized DataSource getDataSource() throws FrameworkException {
        return getDataSource(DEFAULT_CONNECTION_NAME);
    }

    public synchronized DataSource getDataSource(String dataSourcename) throws FrameworkException {
        if (dataSourceMap.containsKey(dataSourcename)) {
            return dataSourceMap.get(dataSourcename);
        } else {
            // Recupero la connection dal connection pool
            log.info("Recupero il DataSource {} dal connection pool", dataSourcename);
            try {
                Context context = new InitialContext();

                DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/" + dataSourcename);
                if (dataSource == null) {
                    throw new FrameworkException(ExceptionCode.DATA_SOURCE_NOT_FOUND, "DataSource: java:comp/env/jdbc/" + dataSourcename + " non trovato");
                } else {
                    dataSourceMap.put(dataSourcename, dataSource);
                }

                return dataSource;
            } catch (NamingException e) {
                throw new FrameworkException(ExceptionCode.DATA_SOURCE_NOT_FOUND, "DataSource: java:comp/env/jdbc/" + dataSourcename + " non trovato", e);
            }
        }
    }
}

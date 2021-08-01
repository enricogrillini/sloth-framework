package it.eg.sloth.db.datasource;

import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Collection;
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
public interface DataSource {

    Object getObject(String name);

    default BigDecimal getBigDecimal(String name) {
        return (BigDecimal) getObject(name);
    }

    default Timestamp getTimestamp(String name) {
        return (Timestamp) getObject(name);
    }

    default String getString(String name) {
        return (String) getObject(name);
    }

    default byte[] getByte(String name) {
        return (byte[]) getObject(name);
    }

    DataSource setObject(String name, Object value);

    default DataSource setBigDecimal(String name, BigDecimal value) {
       return setObject(name, value);
    }

    default DataSource setTimestamp(String name, Timestamp value) {
        return setObject(name, value);
    }

    default DataSource setString(String name, String value) {
        return setObject(name, value);
    }

    default DataSource setByte(String name, byte[] value) {
        return setObject(name, value);
    }

    void clear();

    Collection<String> keys();

    Collection<Object> values();

    Map<String, Object> entries();

    /**
     * Aggiorna il DataSource prelevando le informazioni dal DataSource passato
     *
     * @param dataSource
     */
    default void copyFromDataSource(DataSource dataSource) {
        for (String key : dataSource.keys()) {
            setObject(key, dataSource.getObject(key));
        }
    }

    /**
     * Aggiorna il DataSource prelevando le informazioni dal ResultSet passato
     *
     * @param resultSet
     * @throws SQLException
     */
    default void copyFromResultSet(ResultSet resultSet) throws SQLException, FrameworkException {
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

                default:
                    // Types {} non gestito. Utilizzato default.
                    setObject(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                    break;
            }
        }
    }

    /**
     * Carica il DataSource prelevando le informazioni dal ResultSet passato
     *
     * @param resultSet
     * @throws SQLException
     * @throws IOException
     */
    default void loadFromResultSet(ResultSet resultSet) throws SQLException, FrameworkException {
        clear();
        copyFromResultSet(resultSet);
    }

    /**
     * Carica il DataSource prelevando le informazioni dal DataSource passato
     *
     * @param dataSource
     */
    default void loadFromDataSource(DataSource dataSource) {
        clear();
        copyFromDataSource(dataSource);
    }


}

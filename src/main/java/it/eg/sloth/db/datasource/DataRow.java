package it.eg.sloth.db.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.eg.sloth.db.query.SelectQueryInterface;
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
public interface DataRow extends DataSource {


    /**
     * Inizializza la row dalla query
     *
     * @param query
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    default void setFromQuery(SelectQueryInterface query) throws SQLException, FrameworkException {
        query.populateDataRow(this);
    }

    /**
     * Inizializza la row dalla query
     *
     * @param query
     * @param connection
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    default void setFromQuery(SelectQueryInterface query, Connection connection) throws SQLException, IOException, FrameworkException {
        query.populateDataRow(this, connection);
    }

    /**
     * Carica la row dalla query
     *
     * @param query
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    default boolean loadFromQuery(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
        return query.populateDataRow(this);
    }

    /**
     * Carica la row dalla query
     *
     * @param query
     * @param connection
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    default boolean loadFromQuery(SelectQueryInterface query, Connection connection) throws SQLException, IOException, FrameworkException {
        return query.populateDataRow(this, connection);
    }

}

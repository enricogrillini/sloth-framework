package it.eg.sloth.db.query;

import java.sql.Connection;
import java.sql.SQLException;


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
public interface ExecuteQueryInterface {

    /**
     * Restituisce lo statement sql
     *
     * @return
     */
    String getStatement();

    /**
     * Imposta lo statement sql
     *
     * @param statement
     */
    void setStatement(String statement);

    /**
     * Esegue un DDL o una DML
     *
     * @throws SQLException
     */
    void execute() throws SQLException;

    /**
     * Esegue un DDL o una DML
     *
     * @param connectionName
     * @throws SQLException
     */
    void execute(String connectionName) throws SQLException;

    /**
     * Esegue un DDL o una DML
     *
     * @param connection
     * @throws SQLException
     */
    void execute(Connection connection) throws SQLException;


}

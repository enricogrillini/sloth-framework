package it.eg.sloth.db.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.row.column.Column;
import it.eg.sloth.framework.common.exception.FrameworkException;

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
public interface DbDataRow extends DbDataSource, TransactionalDataRow {

    /**
     * Restituisce le specifiche delle colonne della riga
     *
     * @return
     */
    Column[] getColumns();

    String getSelect();

    String getInsert();

    String getDelete();

    String getUpdate();

    boolean select() throws FrameworkException, SQLException, IOException;

    boolean select(Connection connection) throws SQLException, IOException, FrameworkException;

    void insert(Connection connection) throws SQLException;

    void delete(Connection connection) throws SQLException;

    void update(Connection connection) throws SQLException;

    boolean isAutoloadLob();

    void setAutoloadLob(boolean autoloadLob);

}

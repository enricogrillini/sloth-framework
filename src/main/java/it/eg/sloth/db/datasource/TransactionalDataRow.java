package it.eg.sloth.db.datasource;

import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.SQLException;

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
public interface TransactionalDataRow extends TransactionalDataSource, DataRow {

    /**
     * Ritorna lo stato della riga
     *
     * @return
     */
    RowStatus getStatus();

    /**
     * Segnala che la riga è rimossa
     */
    void remove() throws FrameworkException;

    /**
     * Salva le modifiche
     */
    void save() throws FrameworkException, SQLException;

    /**
     * Annulla le modifiche
     */
    void undo() throws FrameworkException;

    /**
     * Forza lo status clean
     */
    void forceClean();

}

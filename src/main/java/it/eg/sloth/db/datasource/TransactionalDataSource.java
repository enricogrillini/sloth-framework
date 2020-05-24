package it.eg.sloth.db.datasource;

import it.eg.sloth.framework.common.exception.FrameworkException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

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
public interface TransactionalDataSource extends DataSource {

    Object getOldObject(String name);

    BigDecimal getOldBigDecimal(String name);

    Timestamp getOldTimestamp(String name);

    String getOldString(String name);

    byte[] getOldByte(String name);

    /**
     * Salva le modifiche
     *
     * @throws Exception
     */
    void save() throws FrameworkException, SQLException;

    /**
     * Annulla le modifiche
     */
    void undo() throws FrameworkException;

    /**
     * Forza lo stato clean: dopo questa operazione il save e l'undo non hanno alcun effetto
     */
    void forceClean();
}

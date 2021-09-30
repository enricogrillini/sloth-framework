package it.eg.sloth.db.datasource.row.lob;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.sql.Blob;

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
public class BLobData extends LobData<byte[]> {

    public BLobData() {
        super();
    }

    public BLobData(boolean load, Blob blob)  throws FrameworkException {
        this();

        if (load && blob != null) {
            try (InputStream inputStream = blob.getBinaryStream()) {
                value = IOUtils.toByteArray(inputStream);
                setStatus(LobData.ON_LINE);
            } catch (Exception e) {
                throw new FrameworkException(ExceptionCode.GENERIC_SYSTEM_ERROR, e);
            }
        }
    }

}

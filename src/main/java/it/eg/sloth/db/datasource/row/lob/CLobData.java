package it.eg.sloth.db.datasource.row.lob;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;


import java.io.*;

import java.nio.charset.StandardCharsets;
import java.sql.Clob;
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
@Slf4j
public class CLobData extends LobData<String> {

    public CLobData() {
        super();
    }

    public CLobData(boolean load, Clob clob) throws FrameworkException {
        this();

        if (load && clob != null) {
            try (Reader reader = clob.getCharacterStream()) {
                value = new String(IOUtils.toString(reader).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                setStatus(LobData.ON_LINE);
            } catch (IOException | SQLException e) {
                throw new FrameworkException(ExceptionCode.GENERIC_SYSTEM_ERROR, e);
            }
        }
    }

}

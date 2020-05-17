package it.eg.sloth.db.datasource.row.lob;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.SQLException;

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
public class CLobData extends LobData<String> {

    public CLobData() {
        super();
    }

    public CLobData(boolean load, Clob clob) {
        this();

        if (load && clob != null) {
            try (InputStream inputStream = clob.getAsciiStream()) {
                value = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
                setStatus(LobData.ON_LINE);
            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(getValue());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setValue((String) in.readObject());
    }

}

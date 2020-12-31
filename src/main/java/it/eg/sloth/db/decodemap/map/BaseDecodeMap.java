package it.eg.sloth.db.decodemap.map;

import java.io.IOException;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.value.BaseDecodeValue;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class BaseDecodeMap<T> extends AbstractDecodeMap<T, BaseDecodeValue<T>> {

    public static final String DEFAULT_CODE_NAME = "codice";
    public static final String DEFAULT_DESCRIPTION_NAME = "descrizione";
    public static final String DEFAULT_VALID_NAME = "flagvalido";

    private String codeName;
    private String descriptionName;
    private String validName;

    public BaseDecodeMap(String codeName, String descriptionName, String validName) {
        super();

        this.codeName = codeName;
        this.descriptionName = descriptionName;
        this.validName = validName;
    }

    public BaseDecodeMap() {
        this(DEFAULT_CODE_NAME, DEFAULT_DESCRIPTION_NAME, DEFAULT_VALID_NAME);
    }

    /**
     * Ricarica la DecodeMap da un DataTable
     *
     * @param dataTable
     */
    @SuppressWarnings("unchecked")
    public void load(DataTable<?> dataTable) {
        clear();

        for (DataRow row : dataTable) {
            if (validName == null)
                put((T) row.getObject(codeName), row.getString(descriptionName));
            else
                put((T) row.getObject(codeName), row.getString(descriptionName), "S".equals(row.getString(validName)));
        }
    }

    /**
     * Ricarica la DecodeMap da una Query
     *
     * @param query
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    public void load(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
        load(query.selectTable());
    }

    public void put(T code, String description) {
        put(new BaseDecodeValue<>(code, description));
    }

    public void put(T code, String description, boolean valid) {
        put(new BaseDecodeValue<>(code, description, valid));
    }

}

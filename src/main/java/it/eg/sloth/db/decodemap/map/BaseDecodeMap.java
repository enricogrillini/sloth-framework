package it.eg.sloth.db.decodemap.map;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.value.BaseDecodeValue;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
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
public class BaseDecodeMap<T> extends AbstractDecodeMap<T, BaseDecodeValue<T>> {

  public static final String DEFAULT_CODE_NAME = "codice";
  public static final String DEFAULT_DESCRIPTION_NAME = "descrizione";
  public static final String DEFAULT_VALID_NAME = "flagvalido";

  public BaseDecodeMap() {
    super();
  }

  public BaseDecodeMap(DataTable<?> dataTable, String codeName, String descriptionName) {
    this(dataTable, codeName, descriptionName, null);
  }

  @SuppressWarnings("unchecked")
  public BaseDecodeMap(DataTable<?> dataTable, String codeName, String descriptionName, String validName) {
    super();

    for (DataRow row : dataTable) {
      if (validName == null)
        put((T) row.getObject(codeName), row.getString(descriptionName));
      else
        put((T) row.getObject(codeName), row.getString(descriptionName), "S".equals(row.getString(validName)));
    }
  }

  public BaseDecodeMap(SelectQueryInterface query) throws SQLException, IOException, FrameworkException {
    this(query, DEFAULT_CODE_NAME, DEFAULT_DESCRIPTION_NAME, null);
  }

  public BaseDecodeMap(SelectQueryInterface query, String codeName, String descriptionName, String validName) throws SQLException, IOException, FrameworkException {
    this(query.selectTable(), codeName, descriptionName, validName);
  }

  public void put(T code, String description) {
    put(new BaseDecodeValue<>(code, description));
  }

  public void put(T code, String description, boolean valid) {
    put(new BaseDecodeValue<>(code, description, valid));
  }


}

package it.eg.sloth.db.decodemap.value;

import it.eg.sloth.db.datasource.DataRow;

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
public class TableDecodeValue<T, R extends DataRow> extends AbstractDecodeValue<T> {

  private R dataRow;
  private String codeName;
  private String descriptionName;

  public TableDecodeValue(R dataRow, String codeName, String descriptionName) {
    this(dataRow, codeName, descriptionName, true);
  }

  public TableDecodeValue(R dataRow, String codeName, String descriptionName, boolean valid) {
    this.dataRow = dataRow;
    this.codeName = codeName;
    this.descriptionName = descriptionName;
    setValid(valid);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T getCode() {
    return (T) dataRow.getObject(codeName);
  }

  @Override
  public String getDescription() {
    return dataRow.getString(descriptionName);
  }
  
  public R getDataRow() {
    return dataRow;
  }


}

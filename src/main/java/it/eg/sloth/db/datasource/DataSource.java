package it.eg.sloth.db.datasource;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;

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
public interface DataSource {

   Object getObject(String name);

   BigDecimal getBigDecimal(String name);

   Timestamp getTimestamp(String name);

   String getString(String name);

   byte[] getByte(String name);

   void setObject(String name, Object value);

   void setBigDecimal(String name, BigDecimal value);

   void setTimestamp(String name, Timestamp value);

   void setString(String name, String value);

   void setByte(String name, byte[] value);

   void clear();

   Iterator<String> keyIterator();

   Iterator<Object> valueIterator();

  /**
   * Aggiorna il DataSource prelevando le informazioni dal DataSource passato
   * 
   * @param dataSource
   */
   void copyFromDataSource(DataSource dataSource);

  /**
   * Aggiorna il DataSource prelevando le informazioni dal ResultSet passato
   * 
   * @param resultSet
   * @throws SQLException
   */
   void copyFromResultSet(ResultSet resultSet) throws SQLException, IOException;

  /**
   * Carica il DataSource prelevando le informazioni dal DataSource passato
   * 
   * @param dataSource
   */
   void loadFromDataSource(DataSource dataSource);

  /**
   * Carica il DataSource prelevando le informazioni dal ResultSet passato
   * 
   * @param resultSet
   * @throws SQLException
   */
   void loadFromResultSet(ResultSet resultSet) throws SQLException, IOException;

}

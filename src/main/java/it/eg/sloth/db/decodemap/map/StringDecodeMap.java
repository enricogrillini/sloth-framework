package it.eg.sloth.db.decodemap.map;

import it.eg.sloth.db.decodemap.value.BaseDecodeValue;

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
public class StringDecodeMap extends BaseDecodeMap<String> {

  public StringDecodeMap(String... values) {
    for (String value : values) {
      put(new BaseDecodeValue<>(value, value));
    }
  }

  public StringDecodeMap(String string) {
    super();
    if (string == null || string.equals("")) {
      return;
    }

    String[] array = string.split(";");
    for (String value : array) {
      String[] array2 = value.split(",");

      put(new BaseDecodeValue<>(array2[0].trim(), array2[1].trim()));
    }

  }

  public static class Factory {

    private Factory() {
      // NOP
    }

    public static final String SI = "S";
    public static final String NO = "N";
    public static final String TUTTI = "T";

    public static final String DESCR_SI = "Sì";
    public static final String DESCR_NO = "No";
    public static final String DESCR_TUTTI = "Tutti";

    public static final StringDecodeMap DECODE_MAP_SN = new StringDecodeMap(SI + "," + DESCR_SI + ";" + NO + "," + DESCR_NO);
    public static final StringDecodeMap DECODE_MAP_SNT = new StringDecodeMap(SI + "," + DESCR_SI + ";" + NO + "," + DESCR_NO + ";" + TUTTI + "," + DESCR_TUTTI);
  }
}

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
public class StringDecodeMap extends BaseDecodeMap<String> {

    public static final StringDecodeMap SI_NO = new StringDecodeMap("S,Sì;N,No");
    public static final StringDecodeMap SI_NO_TUTTI = new StringDecodeMap("S,Sì;N,No;T,Tutti");

    public StringDecodeMap(String string) {
        super();
        if (string == null || string.equals("")) {
            return;
        }

        String[] array = string.split(";");
        for (String value : array) {
            String[] array2 = value.split(",");

            put(new BaseDecodeValue<String>(array2[0].trim(), array2[1].trim()));
        }

    }
}

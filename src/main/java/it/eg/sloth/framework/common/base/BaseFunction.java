package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;

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
public class BaseFunction {

    private static String separator = ",";

    private BaseFunction() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Verifica se l'oggetto passato è null ("" e' considerato null)
     *
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        return object == null || object.toString().equals("");
    }

    /**
     * Verifica se la stringa passata è valorizzata
     *
     * @param string
     * @return
     */
    public static boolean isBlank(String string) {
        return string == null || "".equals(string.trim());
    }

    /**
     * Ritorna il secondo oggetto se il primo è vuoto
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static Object nvl(Object obj1, Object obj2) {
        if (isNull(obj1))
            return obj2;
        else
            return obj1;
    }

    public static String nvl(String str1, String str2) {
        if (isBlank(str1))
            return str2;
        else
            return str1;
    }

    public static BigDecimal nvl(BigDecimal num1, BigDecimal num2) {
        if (isNull(num1))
            return num2;
        else
            return num1;
    }

    public static Timestamp nvl(Timestamp time1, Timestamp time2) {
        if (isNull(time1))
            return time2;
        else
            return time1;
    }

    /**
     * Verifica se gli oggetti passati sono uguali (gestisce anche il caso di null
     * value)
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null)
            return true;
        if (obj1 != null && obj2 == null)
            return false;
        if (obj1 == null)
            return false;
        return obj1.equals(obj2);
    }

    public static boolean in(Object obj1, Object... objects) {
        for (Object object : objects) {
            if (equals(obj1, object)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Arrotonda un bigDecimal
     *
     * @param bigDecimal
     * @param precision
     * @return
     */
    public static BigDecimal round(BigDecimal bigDecimal, int precision) {
        return bigDecimal.setScale(precision, RoundingMode.HALF_UP);
    }

    /**
     * Arrotonda un bigDecimal
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal round(BigDecimal bigDecimal) {
        return round(bigDecimal, 0);
    }

    /**
     * Tronca un bigDecimal
     *
     * @param bigDecimal
     * @param precision
     * @return
     */
    public static BigDecimal trunc(BigDecimal bigDecimal, int precision) {
        return bigDecimal.setScale(precision, RoundingMode.FLOOR);
    }

    /**
     * Arrotonda un bigDecimal
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal trunc(BigDecimal bigDecimal) {
        return trunc(bigDecimal, 0);
    }

    /**
     * Tronca la data passata
     *
     * @param timestamp
     * @param format
     * @return
     * @throws ParseException
     */
    public static Timestamp trunc(Timestamp timestamp, String format) throws FrameworkException {
        return TimeStampUtil.parseTimestamp(TimeStampUtil.formatTimestamp(timestamp, null, format), format);
    }

    /**
     * Tronca la data passata
     *
     * @param timestamp
     * @return
     * @throws ParseException
     */
    public static Timestamp trunc(Timestamp timestamp) throws FrameworkException {
        return trunc(timestamp, TimeStampUtil.DEFAULT_FORMAT);
    }

    /**
     * Tokenizza una stringa
     *
     * @param s
     * @param separator
     * @return
     */
    public static String[] split(String s, String separator) {
        String[] result = s.split(separator);
        for (int i = 0; i < result.length; i++)
            result[i] = result[i].trim();

        return result;
    }

    /**
     * Tokenizza una stringa
     *
     * @param s
     * @return
     */
    public static String[] split(String s) {
        return split(s, separator);
    }

    /**
     * Converte l'oggetto passato in stringa. Il null diventa stringa vuota
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (object instanceof String)
            return (String) object;

        if (object == null)
            return "";

        return object.toString();
    }

}

package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
public class BigDecimalUtil {
    public static final String DEFAULT_FORMAT = "#.0000000";

    public static final int SCALE = 10;

    private BigDecimalUtil() {

    }

    /**
     * Converte un double in BigDecimal
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(Double value) {
        if (BaseFunction.isNull(value)) {
            return null;
        } else {
            return BigDecimal.valueOf(value).setScale(SCALE, RoundingMode.HALF_EVEN).stripTrailingZeros();
        }
    }

    /**
     * Converte un Number in BigDecimal
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(Number value) {
        if (BaseFunction.isNull(value)) {
            return null;
        } else if (value.intValue() == value.doubleValue()) {
            return new BigDecimal(value.intValue());
        } else {
            return toBigDecimal(value.doubleValue());
        }
    }

    /**
     * Converte una stringa in un BigDecimal
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal parseBigDecimal(String bigDecimal) throws FrameworkException {
        return parseBigDecimal(bigDecimal, DEFAULT_FORMAT, null);
    }

    /**
     * Converte una stringa in un BigDecimal
     *
     * @param bigDecimal
     * @param format
     * @return
     */
    public static BigDecimal parseBigDecimal(String bigDecimal, String format, DecimalFormatSymbols decimalFormatSymbols) throws FrameworkException {
        if (BaseFunction.isBlank(bigDecimal)) {
            return null;
        }

        if (format == null) {
            format = DEFAULT_FORMAT;
        }

        if (decimalFormatSymbols == null) {
            decimalFormatSymbols = new DecimalFormatSymbols();
        }

        NumberFormat formatter = new DecimalFormat(format, decimalFormatSymbols);

        BigDecimal decimal;
        try {
            decimal = BigDecimalUtil.toBigDecimal(formatter.parse(bigDecimal.trim()));
        } catch (ParseException e) {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, e);
        }

        return decimal;
    }


    /**
     * Converte il BigDecimal in una stringa
     *
     * @param bigDecimal
     * @return
     */
    public static String formatBigDecimal(BigDecimal bigDecimal) {
        return formatBigDecimal(bigDecimal, DEFAULT_FORMAT, null);
    }

    /**
     * Converte il BigDecimal in una stringa
     *
     * @param bigDecimal
     * @param format
     * @return
     */
    public static String formatBigDecimal(BigDecimal bigDecimal, String format, DecimalFormatSymbols decimalFormatSymbols) {
        if (bigDecimal == null) {
            return StringUtil.EMPTY;
        }

        if (decimalFormatSymbols == null) {
            decimalFormatSymbols = new DecimalFormatSymbols();
        }

        NumberFormat formatter = new DecimalFormat(format, decimalFormatSymbols);
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        return formatter.format(bigDecimal);
    }

    /**
     * Moltiplica i due numeti passati gestendo i null
     *
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    public static final BigDecimal sum(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null) {
            return bigDecimal2;
        } else if (bigDecimal2 == null) {
            return bigDecimal1;
        } else {
            return bigDecimal1.add(bigDecimal2);
        }
    }

    /**
     * Moltiplica i due numeti passati gestendo i null
     *
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    public static final BigDecimal multiply(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null || bigDecimal2 == null) {
            return null;
        } else {
            return bigDecimal1.multiply(bigDecimal2);
        }
    }


    /**
     * Ritorna il numero maggiore
     *
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    public static BigDecimal greatest(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null || bigDecimal2 == null) {
            return null;
        } else if (bigDecimal1.doubleValue() > bigDecimal2.doubleValue()) {
            return bigDecimal1;
        } else {
            return bigDecimal2;
        }
    }


    public static int intValue(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0 : bigDecimal.intValue();
    }

    public static Integer intObject(BigDecimal bigDecimal) {
        return bigDecimal == null ? null : bigDecimal.intValue();
    }

    public static long longValue(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0 : bigDecimal.longValue();
    }

    public static Long longObject(BigDecimal bigDecimal) {
        return bigDecimal == null ? null : bigDecimal.longValue();
    }


    public static double doubleValue(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0 : bigDecimal.doubleValue();
    }

    public static Double doubleObject(BigDecimal bigDecimal) {
        return bigDecimal == null ? null : bigDecimal.doubleValue();
    }

}

package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.exception.ExceptionCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author Enrico Grillini
 */
public class BigDecimalUtil {
    public static final String DEFAULT_FORMAT = "#.0000000";

    public static final int ROUND = BigDecimal.ROUND_HALF_UP;
    public static final int SCALE = 10;

    private BigDecimalUtil() {

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
            return BigDecimal.valueOf(value.doubleValue()).setScale(SCALE, ROUND);
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

}

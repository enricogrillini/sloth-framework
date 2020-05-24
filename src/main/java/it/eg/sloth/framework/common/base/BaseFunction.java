package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;

/**
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

    /**
     * Moltiplica i due numeti passati gestendo i null
     *
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    public static final BigDecimal multiply(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null || bigDecimal2 == null)
            return null;
        else
            return bigDecimal1.multiply(bigDecimal2);
    }

    /**
     * Ritorna il numero maggiore
     *
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    public static BigDecimal greatest(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null || bigDecimal2 == null)
            return null;

        if (bigDecimal1.doubleValue() > bigDecimal2.doubleValue())
            return bigDecimal1;
        else
            return bigDecimal2;
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
        return TimeStampUtil.parseTimestamp(TimeStampUtil.formatTimestamp(timestamp, format), format);
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

    /**
     * Verifica se la stringa passata corrisponde ad un user id valido
     *
     * @param userid
     * @return
     */
    public static boolean isUserIdValid(String userid) {
        if (userid == null)
            return false;

        if (userid.length() < 8)
            return false;

        if (userid.length() > 16)
            return false;

        userid = userid.toLowerCase();
        for (int i = 0; i < userid.length(); i++) {
            char c = userid.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }

        return true;
    }

}

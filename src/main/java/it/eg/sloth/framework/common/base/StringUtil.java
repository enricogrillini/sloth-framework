package it.eg.sloth.framework.common.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Enrico Grillini
 * <p>
 * Collezione di funzioni Oracle orientented sulle stringhe
 */
public class StringUtil {

    public static final String EMPTY = "";
    public static final String TAB = String.valueOf((char) 9);

    public static String[] tokenize(String str, String separator) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, separator, true);

        List<String> list = new ArrayList<>();
        String lastToken = "";
        while (stringTokenizer.hasMoreTokens()) {
            String currentToken = stringTokenizer.nextToken();
            if (!currentToken.equals(separator)) {
                list.add(currentToken.trim());
            } else if (currentToken.equals(separator) && lastToken.equals(separator)) {
                list.add("");
            }
            lastToken = currentToken;
        }

        String[] strings = new String[list.size()];
        int i = 0;
        for (String string : list) {
            strings[i++] = string;
        }

        return strings;
    }

    /**
     * Ritorna la lista delle parole che compongono la stringa passata
     *
     * @param text
     * @return
     */
    public static List<String> words(String text) {
        List<String> result = new ArrayList<>();

        if (!BaseFunction.isBlank(text)) {
            Pattern p = Pattern.compile("\\w+");
            Matcher m = p.matcher(text);
            while (m.find()) {
                result.add(m.group());
            }
        }

        return result;
    }

    /**
     * Verfica se il testo passato contiene tutte le parole indicate
     *
     * @param text
     * @param words
     * @return
     */
    public static boolean containsAllWords(String text, List<String> words) {
        if (BaseFunction.isBlank(text)) {
            return false;
        }

        text = text.toLowerCase();
        for (String word : words) {
            if (!text.contains(word.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Ritorna il replace delle stringa passata (Oracle Like)
     *
     * @param string
     * @param searchString
     * @param replacement
     * @return
     */
    public static String replace(String string, String searchString, String replacement) {
        if (BaseFunction.isBlank(string)) {
            return EMPTY;
        } else {
            return StringUtils.replace(string, searchString, replacement);
        }
    }


    /**
     * Ritorna il substr delle stringa passata (Oracle Like)
     *
     * @param string
     * @param start
     * @return
     */
    public static String substr(String string, int start) {
        if (BaseFunction.isBlank(string)) {
            return EMPTY;
        } else {
            return string.substring(start);
        }
    }

    /**
     * Ritorna il substr delle stringa passata (Oracle Like)
     *
     * @param string
     * @param start
     * @param length
     * @return
     */
    public static String substr(String string, int start, int length) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else if (length > string.length() - start) {
            return string.substring(start);
        } else {
            return string.substring(start, start + length);
        }
    }

    /**
     * Ritorna il substr delle stringa passata arrotondata alla parola (delimitata
     * da spazi)
     *
     * @param string
     * @param start
     * @param length
     * @return
     */
    public static String substrWord(String string, int start, int length) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else if (length > string.length() - start) {
            return string.substring(start);
        } else {
            String result = substr(string, start, length);

            if (BaseFunction.isBlank(result)) {
                return "";
            } else {
                for (int i = result.length() - 1; i >= 0; i--) {
                    if (result.charAt(i) == ' ') {
                        return substr(result, 0, i).trim();
                    }
                }

                return "";
            }
        }
    }

    /**
     * Effettua il loweer case della stringa passata
     *
     * @param string
     * @return
     */
    public static String lower(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else {
            return string.toLowerCase();
        }
    }

    /**
     * Effettua l' upper case della stringa passata
     *
     * @param string
     * @return
     */
    public static String upper(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else {
            return string.toUpperCase();
        }
    }

    /**
     * Effettua il pad di spazi a sinistra
     *
     * @param string
     * @param size
     * @return
     */
    public static String lpad(String string, int size) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else {
            return StringUtils.leftPad(string, size);
        }
    }

    /**
     * Effettua il pad di spazi a destra
     *
     * @param string
     * @param size
     * @return
     */
    public static String rpad(String string, int size) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else {
            return StringUtils.rightPad(string, size);
        }
    }

    /**
     * Rimuove gli spazi a sinistra
     *
     * @param string
     * @return
     */
    public static String ltrim(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else {
            return string.replaceAll("^\\s+", "");
        }
    }

    /**
     * Rimuove gli spazi a destra
     *
     * @param string
     * @return
     */
    public static String rtrim(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else {
            return string.replaceAll("\\s+$", "");
        }
    }

    /**
     * Trasforma la prima lettera di ogni parola in maiuscolo
     *
     * @param string
     * @return
     */
    public static String initCap(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        } else {
            for (int i = 0; i < string.length(); i++) {
                if (i == 0) {
                    // Capitalize the first letter of the string.
                    string = String.format("%s%s", Character.toUpperCase(string.charAt(0)), string.substring(1));
                }

                if (!Character.isLetterOrDigit(string.charAt(i)) && i + 1 < string.length()) {
                    string = String.format("%s%s%s", string.subSequence(0, i + 1), Character.toUpperCase(string.charAt(i + 1)), string.substring(i + 2).toLowerCase());

                }
            }
            return string;
        }
    }

    public static String toJavaClassName(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        }

        return initCap(string).replace(" ", "").replace("_", "");
    }

    public static String toJavaObjectName(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        }

        string = toJavaClassName(string);
        return substr(string, 0, 1).toLowerCase() + substr(string, 1);
    }

    public static String toJavaConstantName(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        }

        String result = "";
        string = toJavaClassName(string);
        for (int i = 0; i < string.length(); i++) {
            if (i > 0 && string.charAt(i) >= 65 && string.charAt(i) <= 90 && !(string.charAt(i - 1) >= 65 && string.charAt(i - 1) <= 90))
                result += "_";

            result += string.charAt(i);
        }

        return result.toUpperCase();
    }

    public static String toFileName(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        }

        String result = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                result += "-";
            } else if (string.charAt(i) == '/') {
                result += "-";
            } else if (string.charAt(i) >= 48 && string.charAt(i) <= 57 ||
                    string.charAt(i) >= 65 && string.charAt(i) <= 90 ||
                    string.charAt(i) >= 97 && string.charAt(i) <= 122 || string.charAt(i) == '-' || string.charAt(i) == '_' || i > 0 && string.charAt(i) == '.') {
                result += string.charAt(i);
            }
        }

        while (result.contains("--")) {
            result = result.replaceAll("--", "-");
        }

        return result;
    }

    public static boolean contains(String string, String inStr) {
        if (BaseFunction.isBlank(string)) {
            return false;
        } else {
            return string.contains(inStr);
        }
    }

    /**
     * Replica il comportamente dell'unescape Js
     *
     * @param text
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String unescape(String text, String encoding) throws UnsupportedEncodingException {
        if (BaseFunction.isBlank(text)) {
            return "";
        }

        String result = text
                .replace("\\@", "%40") // @
                .replace("\\+", "%2B") // +
                .replace("\\/", "%2F") // /
                .replace("%u20AC", "%A4") // Euro
                .replace("%A0", "%20");

        return URLDecoder.decode(result, encoding);
    }

    /**
     * Replica il comportamente dell'escape Js
     *
     * @param text
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String escape(String text, String encoding) throws UnsupportedEncodingException {
        return URLEncoder.encode(text, encoding);
    }

    /**
     * Conta le occorrenze di una sottostringa all'interno della stringa passata
     *
     * @param text
     * @param regEx
     * @return
     */
    public static int countOccurences(String text, String regEx) {
        Matcher m = Pattern.compile(regEx).matcher(text);

        int count;
        for (count = 0; m.find(); count++)
            ;

        return count;
    }

}
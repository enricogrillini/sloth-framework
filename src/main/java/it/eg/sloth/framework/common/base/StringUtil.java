package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public class StringUtil {

    private static final String MAIL_ADDRESS = "^(.+)@(.+)$";

    private StringUtil() {
        // NOP
    }

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String TAB = String.valueOf((char) 9);
    public static final String SEPARATOR = ",";

    public static String join(Collection<String> strings) {
        return join(strings, SEPARATOR);
    }

    public static String join(Collection<String> strings, String separator) {
        if (BaseFunction.isNull(strings) || strings.isEmpty()) {
            return StringUtil.EMPTY;
        } else {
            return join(strings.toArray(new String[0]), separator);
        }
    }

    public static String join(String[] strings) {
        return join(strings, SEPARATOR);
    }

    public static String join(String[] strings, String separator) {
        if (BaseFunction.isNull(strings)) {
            return StringUtil.EMPTY;
        } else {
            return String.join(separator, strings);
        }
    }

    public static String[] split(String string) {
        return split(string, SEPARATOR);
    }

    public static String[] split(String string, String separator) {
        if (BaseFunction.isBlank(string)) {
            return new String[]{};
        } else {
            String[] tokens = StringUtils.split(string, separator);
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = StringUtils.trim(tokens[i]);
            }

            return tokens;
        }
    }


    /**
     * Ripulisce il testo passato. Effetua il trim del testo e rimuove gli spazi doppi tra le parole
     *
     * @param text
     * @return
     */
    public static String clean(String text) {
        if (BaseFunction.isBlank(text)) {
            return EMPTY;
        } else {
            return text.trim().replaceAll(" +", " ");
        }
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

    // Recursive function to check if the input string matches
    // with a given wildcard pattern
    private static boolean patternMatch(String str, int n, String pattern, int m) {
        // end of the pattern is reached
        if (m == pattern.length()) {
            // return true only if the end of the input string is also reached
            return n == str.length();
        }

        // if the input string reaches its end, return when the
        // remaining characters in the pattern are all `*`
        if (n == str.length()) {
            for (int i = m; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }

            return true;
        }

        // if the current wildcard character is `?` or the current character in
        // the pattern is the same as the current character in the input string
        if (pattern.charAt(m) == '?' || pattern.charAt(m) == str.charAt(n)) {
            // move to the next character in the pattern and the input string
            return patternMatch(str, n + 1, pattern, m + 1);
        }

        // if the current wildcard character is `*`
        if (pattern.charAt(m) == '*') {
            // move to the next character in the input string or
            // ignore `*` and move to the next character in the pattern
            return patternMatch(str, n + 1, pattern, m) ||
                    patternMatch(str, n, pattern, m + 1);
        }

        // we reach here when the current character in the pattern is not a
        // wildcard character, and it doesn't match the current
        // character in the input string
        return false;
    }

    // Check if a string matches with a given wildcard pattern
    public static boolean patternMatch(String str, String pattern) {
        return patternMatch(str, 0, pattern, 0);
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

    public static boolean in(String source, String... targetStrings) {
        for (String target : targetStrings) {
            if (BaseFunction.equals(source, target)) {
                return true;
            }
        }
        return false;
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
            return StringUtil.EMPTY;
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
            return StringUtil.EMPTY;
        } else {
            return string.replaceAll("\\s+$", "");
        }
    }

    /**
     * Rimuove gli spazi a destra e a sinistra
     *
     * @param string
     * @return
     */
    public static String trim(String string) {
        return ltrim(rtrim(string));
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

        return initCap(string).replace(" ", "").replace("_", "").replace("-", "").replace(".", "");
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
            return EMPTY;
        }

        StringBuilder result = new StringBuilder();
        string = toJavaClassName(string);
        for (int i = 0; i < string.length(); i++) {
            if (i > 0 && string.charAt(i) >= 65 && string.charAt(i) <= 90 && !(string.charAt(i - 1) >= 65 && string.charAt(i - 1) <= 90))
                result.append("_");

            result.append(string.charAt(i));
        }

        return result.toString().toUpperCase();
    }

    public static String toJavaStringParameter(String string) {
        if (BaseFunction.isBlank(string)) {
            return "null";
        }

        String result = string.replace("\n", "\\n");
        return "\"" + result + "\"";
    }

    public static String toFileName(String string) {
        if (BaseFunction.isBlank(string)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                builder.append("_");
            } else if (string.charAt(i) == '/') {
                builder.append("-");
            } else if (string.charAt(i) == ':') {
                builder.append(".");
            } else if (string.charAt(i) == 'à') {
                builder.append("a");
            } else if (string.charAt(i) == 'ì') {
                builder.append("i");
            } else if (string.charAt(i) == 'ù') {
                builder.append("u");
            } else if (string.charAt(i) == 'è') {
                builder.append("e");
            } else if (string.charAt(i) == 'é') {
                builder.append("e");
            } else if (string.charAt(i) >= 48 && string.charAt(i) <= 57 ||
                    string.charAt(i) >= 65 && string.charAt(i) <= 90 ||
                    string.charAt(i) >= 97 && string.charAt(i) <= 122 || string.charAt(i) == '-' || string.charAt(i) == '_' || i > 0 && string.charAt(i) == '.') {
                builder.append(string.charAt(i));
            }
        }

        String result = builder.toString();

        result = result.replace("--", "-");
        result = result.replace("_-", "_");
        result = result.replace("-_", "_");
        result = result.replace("__", "_");

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

    public static String parseString(String string) {
        if (BaseFunction.isBlank(string)) {
            return null;
        } else {
            return string;
        }
    }

    /**
     * Verifica la mail passata
     *
     * @param mail
     * @return
     * @throws ParseException
     */
    public static String parseMail(String mail) throws FrameworkException {
        if (BaseFunction.isBlank(mail)) {
            return null;
        } else if (mail.matches(MAIL_ADDRESS)) {
            return mail;
        } else {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, "Indirizzo email errato");
        }
    }

    /**
     * Valida un codice fiscale
     *
     * @param codiceFiscale
     * @return
     * @throws FrameworkException
     */
    public static String parseCodiceFiscale(String codiceFiscale) throws FrameworkException {
        if (BaseFunction.isBlank(codiceFiscale)) {
            return null;
        }

        // Normalizzo il codice fiscale
        codiceFiscale = codiceFiscale.replaceAll("[ \t\r\n]", "");
        codiceFiscale = codiceFiscale.toUpperCase();

        if (codiceFiscale.length() != 16) {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, "Lunghezza codice fiscale errata");
        }

        // Reg Ex semplice   ^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$
        // Reg Ex articolata ^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$
        if (!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[ABCDEHLMPRST]{1}[0-9]{2}([A-Z]{1}[0-9]{3})[A-Z]{1}$")) {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, "Trovati caratteri non validi");
        }

        int s = 0;
        String evenMap = "BAFHJNPRTVCESULDGIMOQKWZYX";
        for (int i = 0; i < 15; i++) {
            int c = codiceFiscale.charAt(i);
            int n;
            if ('0' <= c && c <= '9') {
                n = c - '0';
            } else {
                n = c - 'A';
            }

            if ((i & 1) == 0) {
                n = evenMap.charAt(n) - 'A';
            }

            s += n;
        }
        if (s % 26 + 'A' != codiceFiscale.charAt(15))
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, "Codice di controllo non valido");

        return codiceFiscale;
    }

    /**
     * Valida una partita iva
     *
     * @param partitaIva
     * @return
     * @throws FrameworkException
     */
    public static String parsePartitaIva(String partitaIva) throws FrameworkException {
        if (BaseFunction.isBlank(partitaIva)) {
            return null;
        }

        // Elimino spaszi
        partitaIva = partitaIva.replaceAll("[ \t\r\n]", "");


        if (partitaIva.length() != 11) {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, "Lunghezza partita iva errata");
        }

        if (!partitaIva.matches("^[0-9]{11}$")) {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, "Trovati caratteri non validi");
        }

        int s = 0;
        for (int i = 0; i < 11; i++) {
            int n = partitaIva.charAt(i) - '0';
            if ((i & 1) == 1) {
                n *= 2;
                if (n > 9)
                    n -= 9;
            }
            s += n;
        }
        if (s % 10 != 0) {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, "Codice di controllo non valido");
        }

        return partitaIva;
    }

}
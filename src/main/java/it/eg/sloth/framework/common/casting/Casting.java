package it.eg.sloth.framework.common.casting;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import org.apache.commons.text.StringEscapeUtils;

import java.text.ParseException;

/**
 * @author Enrico Grillini
 * <p>s
 * Questa classe implemente i controlli formali sui tipi dato
 */
public class Casting {

    private static final String MAIL_ADDRESS = "^[-_a-z0-9\\'+*$^&%=~!?{}]++(?:\\.[-_a-z0-9\\'+*$^&%=~!?{}]+)*+@(?:(?![-.])[-a-z0-9.]+(?<![-.])\\.[a-z]{2,6}|\\d{1,3}(?:\\.\\d{1,3}){3})(?::\\d++)?$";

    /**
     * Verifica la mail passata
     *
     * @param string
     * @return
     * @throws ParseException
     */
    public static String parseMail(String string) {
        if (BaseFunction.isBlank(string))
            return null;
        else if (string.matches(MAIL_ADDRESS))
            return string;
        else
            throw new RuntimeException(string);
    }

    public static String parsePIVA(String string) {
        int i;
        int c;
        int s;
        if (BaseFunction.isBlank(string)) {
            return null;
        }

        if (string.length() != 11) {
            throw new RuntimeException("La lunghezza della partita IVA non e' corretta.\n");
        }
        for (i = 0; i < 11; i++) {
            if (string.charAt(i) < '0' || string.charAt(i) > '9') {
                throw new RuntimeException("La partita IVA contiene dei caratteri non ammessi.\n");
            }
        }
        s = 0;
        for (i = 0; i <= 9; i += 2) {
            s += string.charAt(i) - '0';
        }

        for (i = 1; i <= 9; i += 2) {
            c = 2 * (string.charAt(i) - '0');
            if (c > 9)
                c = c - 9;
            s += c;
        }

        if ((10 - s % 10) % 10 != string.charAt(10) - '0') {
            throw new RuntimeException("Il codice di controllo non corrisponde.");
        }

        return string;
    }

    public static String parseCodiceFiscale(String cf) {
        int i;
        int s;
        int c;
        if (BaseFunction.isBlank(cf)) {
            return null;
        }

        int[] setdisp = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
        if (cf.length() != 16) {
            throw new RuntimeException("La lunghezza del codice fiscale non e' corretta.");
        }

        String cf2 = cf.toUpperCase();
        for (i = 0; i < 16; i++) {
            c = cf2.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
                throw new RuntimeException("Il codice fiscale contiene dei caratteri non validi.");
            }
        }
        s = 0;
        for (i = 1; i <= 13; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9')
                s = s + c - '0';
            else
                s = s + c - 'A';
        }
        for (i = 0; i <= 14; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9')
                c = c - '0' + 'A';
            s = s + setdisp[c - 'A'];
        }
        if (s % 26 + 'A' != cf2.charAt(15))
            throw new RuntimeException("Il codice di controllo non corrisponde.");

        return "";
    }

    public static String parseString(String string) {
        if (BaseFunction.isBlank(string)) {
            return null;
        } else {
            return string;
        }
    }


    /**
     * Converte l'oggetto passato in una stringa HTML
     *
     * @param testo
     * @return
     */
    public static String getHtml(String testo) {
        return getHtml(testo, true, true);
    }

    /**
     * Converte l'oggetto passato in una stringa HTML
     *
     * @param testo
     * @return
     */
    public static String getHtml(String testo, boolean br, boolean nbsp) {
        if (BaseFunction.isNull(testo)) {
            return StringUtil.EMPTY;
        }

        String result = StringEscapeUtils.escapeHtml4(testo);

        if (br) {
            result = result.replaceAll("\\n", "<br/>");
            result = result.replaceAll("\\r", "");
        }

        if (nbsp) {
            result = result.replaceAll("  ", "&nbsp; ");
        }

        return result;
    }

    /**
     * Ritorna la codifica esadecimale del carattere passato
     *
     * @param ch
     * @return
     */
    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase();
    }

    /**
     * Converte l'oggetto passato in una stringa Js
     *
     * @param testo
     * @return
     */
    public static String getJs(String testo) {
        if (BaseFunction.isNull(testo))
            return "";

        if (testo == null) {
            return null;
        }

        StringBuilder writer = new StringBuilder(testo.length() * 2);

        int sz = testo.length();
        for (int i = 0; i < sz; i++) {
            char ch = testo.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                writer.append("\\u");
                writer.append(hex(ch));
            } else if (ch > 0xff) {
                writer.append("\\u0");
                writer.append(hex(ch));
            } else if (ch > 0x7f) {
                writer.append("\\u00");
                writer.append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        writer.append('\\');
                        writer.append('b');
                        break;
                    case '\n':
                        writer.append('\\');
                        writer.append('n');
                        break;
                    case '\t':
                        writer.append('\\');
                        writer.append('t');
                        break;
                    case '\f':
                        writer.append('\\');
                        writer.append('f');
                        break;
                    case '\r':
                        writer.append('\\');
                        writer.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            writer.append("\\u00");
                            writer.append(hex(ch));
                        } else {
                            writer.append("\\u000");
                            writer.append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        // If we wanted to escape for Java strings then we would
                        // not need this next line.
                        writer.append('\\');
                        writer.append('\'');
                        break;
                    case '"':
                        writer.append('\\');
                        writer.append('"');
                        break;
                    case '\\':
                        writer.append('\\');
                        writer.append('\\');
                        break;
                    default:
                        writer.append(ch);
                        break;
                }
            }
        }

        return writer.toString();
    }

}

package it.eg.sloth.framework.utility.html;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;

public class HtmlColor {

    private static final List<String> COLOR_1 = Arrays.asList("#4e73df", "#1cc88a", "#36b9cc", "#f6c23e", "#e74a3b", "#858796");
    private static final List<String> COLOR_2 = Arrays.asList("#5668E2", "#8A56E2", "#CF56E2", "#E256AE", "#E25668", "#E28956", "#E2CF56", "#AEE256", "#68E256", "#56E289", "#56E2CF", "#56AEE2");

    private HtmlColor() {
        // NOP
    }

    private static String rgbFromHex(String hexColor, String open, String close) throws ParseException {
        if (BaseFunction.isBlank(hexColor)) {
            return StringUtil.EMPTY;
        }

        hexColor = hexColor.toUpperCase();
        if (hexColor.length() != 7) {
            throw new ParseException("Lunghezza colore errato", 0);
        }

        return new StringBuilder()
                .append(open)
                .append(Integer.parseInt(StringUtil.substr(hexColor, 1, 2), 16))
                .append(", ")
                .append(Integer.parseInt(StringUtil.substr(hexColor, 3, 2), 16))
                .append(", ")
                .append(Integer.parseInt(StringUtil.substr(hexColor, 5, 2), 16))
                .append(close)
                .toString();
    }

    /**
     * Converete un colore in formato HEX in rgb
     *
     * @param hexColor
     * @return
     * @throws ParseException
     */
    public static String rgbFromHex(String hexColor) throws ParseException {
        return rgbFromHex(hexColor, "rgb(", ")");
    }

    /**
     * Converete un colore in formato HEX in rgba
     *
     * @param hexColor
     * @param opacity
     * @return
     * @throws ParseException
     */
    public static String rgbaFromHex(String hexColor, double opacity) throws ParseException {
        return rgbFromHex(hexColor, "rgba(", ", " + opacity + ")");
    }

    /**
     * Ritorna una Palette della giusta dimensione
     *
     * @param size
     * @return
     */
    public static List<String> getColorPalette(int size) {
        if (size <= 6) {
            return COLOR_1;
        } else if (size <= 12) {
            return COLOR_2;
        } else {
            return new ArrayList<>();
        }
    }
}

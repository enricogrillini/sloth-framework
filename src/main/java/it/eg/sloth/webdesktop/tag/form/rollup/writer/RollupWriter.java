package it.eg.sloth.webdesktop.tag.form.rollup.writer;

import it.eg.sloth.form.dwh.Attribute;
import it.eg.sloth.form.dwh.Level;
import it.eg.sloth.form.dwh.Measure;

public class RollupWriter {
    /**
     * Scrive un campo: Measure
     *
     * @param measure
     * @return
     */
    public static String writeMeasure(Measure<?> measure) {
        return measure.escapeHtmlText();
    }

    /**
     * Scrive un campo: Level
     *
     * @param level
     * @return
     */
    public static String writeLevel(Level<?> level) {
        return level.getHtmlDecodedText();
    }

    /**
     * Scrive un campo: Attribute
     *
     * @param attribute
     * @return
     */
    public static String writeAttribute(Attribute<?> attribute) {
        return attribute.getHtmlDecodedText();
    }
}

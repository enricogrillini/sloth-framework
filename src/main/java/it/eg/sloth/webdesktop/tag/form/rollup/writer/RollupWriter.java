package it.eg.sloth.webdesktop.tag.form.rollup.writer;

import it.eg.sloth.form.dwh.Attribute;
import it.eg.sloth.form.dwh.Level;
import it.eg.sloth.form.dwh.Measure;

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
 *
 * @author Enrico Grillini
 */
public class RollupWriter {

    private RollupWriter() {
        // NOP
    }

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
        return level.escapeHtmlDecodedText();
    }

    /**
     * Scrive un campo: Attribute
     *
     * @param attribute
     * @return
     */
    public static String writeAttribute(Attribute<?> attribute) {
        return attribute.escapeHtmlDecodedText();
    }
}

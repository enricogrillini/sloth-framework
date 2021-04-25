package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.card.writer.CardWriter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class CardWriterTest {

    private static final String CONTENT_TEMPLATE =
            "   <div class=\"col mr-2\"{2}>\n" +
                    "    <div class=\"text-xs font-weight-bold text-primary text-uppercase mb-1\">{0}</div>\n" +
                    "    <div class=\"row no-gutters align-items-center\">\n" +
                    "     <div class=\"col-auto\">\n" +
                    "      <div class=\"h5 mb-0 mr-3 font-weight-bold text-gray-800\">{1}</div>\n" +
                    "     </div>\n" +
                    "    </div>\n" +
                    "   </div>\n" +
                    "{3}";

    @Test
    void fieldCardContentTest() throws FrameworkException {
        Text<BigDecimal> field = new Text<BigDecimal>("name", "description", DataTypes.INTEGER);
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "description", "", "", ""), CardWriter.fieldCardContent(field));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "description", "10", "", ""), CardWriter.fieldCardContent(field));

        field.setBaseLink("www?");
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "description", "10", "", "   <a href=\"www?10\" class=\"stretched-link\"></a>\n"), CardWriter.fieldCardContent(field));

        field.setTooltip("tooltip");
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "description", "10", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"", "   <a href=\"www?10\" class=\"stretched-link\"></a>\n"), CardWriter.fieldCardContent(field));
    }

}

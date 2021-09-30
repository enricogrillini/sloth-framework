package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.card.writer.CardWriter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
 *
 * @author Enrico Grillini
 */
class CardWriterTest {

    private static final String CONTENT_TEMPLATE_1 = ResourceUtil.normalizedResourceAsString("snippet-html/card/field-card-content-1.html");
    private static final String CONTENT_TEMPLATE_2 = ResourceUtil.normalizedResourceAsString("snippet-html/card/field-card-content-2.html");
    private static final String CONTENT_TEMPLATE_3 = ResourceUtil.normalizedResourceAsString("snippet-html/card/field-card-content-3.html");
    private static final String CONTENT_TEMPLATE_4 = ResourceUtil.normalizedResourceAsString("snippet-html/card/field-card-content-4.html");

    private static final String FIELDS_CARD_OPEN = ResourceUtil.normalizedResourceAsString("snippet-html/card/fields-card-open.html");

    @Test
    void fieldCardContentTest() throws FrameworkException {
        Text<BigDecimal> field = new Text<BigDecimal>("name", "description", DataTypes.INTEGER);
        assertEquals(CONTENT_TEMPLATE_1, CardWriter.fieldCardContent(field));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(CONTENT_TEMPLATE_2, CardWriter.fieldCardContent(field));

        field.setBaseLink("www?");
        assertEquals(CONTENT_TEMPLATE_3, CardWriter.fieldCardContent(field));

        field.setTooltip("tooltip");
        assertEquals(CONTENT_TEMPLATE_4, CardWriter.fieldCardContent(field));
    }


    @Test
    void fieldsCardOpenTest() throws FrameworkException {
        Fields fields = new Grid<>("prova", null);
        fields.setDescription("Prova sottotitolo");

        Text<BigDecimal> text = new Text("campo1", "campo 1", DataTypes.CURRENCY);
        text.setLocale(Locale.ITALY);
        text.setValue(BigDecimal.valueOf(100));
        fields.addChild(text);

        text = new Text("campo2", "campo 2", DataTypes.CURRENCY);
        text.setLocale(Locale.ITALY);
        fields.addChild(text);

        text = new Text("campo3", "campo 3", DataTypes.CURRENCY);
        text.setLocale(Locale.ITALY);
        text.setValue(BigDecimal.valueOf(300));
        fields.addChild(text);

        text = new Text("campo4", "campo 4", DataTypes.CURRENCY);
        text.setLocale(Locale.ITALY);
        text.setValue(BigDecimal.valueOf(400));
        text.setState(ControlState.DANGER);
        fields.addChild(text);

        assertEquals(FIELDS_CARD_OPEN, CardWriter.fieldsCardOpen(fields));
    }
}

package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Locale;

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
class FormControlInputWriterTest {

    // Controlli
    private static final String CTRL_INPUT = "<input id=\"{0}\" name=\"{0}\" type=\"{1}\" value=\"{2}\"{3}{4}/>";
    private static final String CTRL_INPUT_GROUP = "<div class=\"input-group input-group-sm\"><input id=\"{0}\" name=\"{0}\" type=\"{1}\" value=\"{2}\"{3}{4}/></div><div class=\"small text-danger\">Lorem ipsum</div>";
    private static final String CTRL_INPUT_GROUP_LINK = "<div class=\"input-group input-group-sm\"><input id=\"{0}\" name=\"{0}\" type=\"{1}\" value=\"{2}\"{3}{4}/><div class=\"input-group-append\"><a href=\"/api/testo\" class=\"btn btn-outline-secondary\"><i class=\"fas fa-link\"></i></a></div></div><div class=\"small text-danger\">Lorem ipsum</div>";

    // Attributi
    private static final String ATTR_STEP = " step=\"1\"";

    private static final String ATTR_VIEW = " class=\"form-control form-control-sm\" disabled=\"\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"";
    private static final String ATTR_VIEW_DANGER = " class=\"form-control form-control-sm is-invalid\" disabled=\"\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"";

    private static final String ATTR_EDIT = " class=\"form-control form-control-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"";
    private static final String ATTR_EDIT_DANGER = " class=\"form-control form-control-sm is-invalid\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"";

    // Input - String
    @Test
    void inputTest() throws FrameworkException {
        Input<String> field = new Input<String>("name", "description", DataTypes.STRING);
        field.setTooltip("tooltip");

        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue("testo");
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "testo", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "testo", "", ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "testo", "", ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT));

        // State
        field.setState(ControlState.DANGER);
        field.setStateMessage("Lorem ipsum");
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP, "name", "text", "testo", "", ATTR_EDIT_DANGER), FormControlWriter.writeControl(field, null, ViewModality.EDIT));

        // Link
        field.setBaseLink("/api/");
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP, "name", "text", "testo", "", ATTR_EDIT_DANGER), FormControlWriter.writeControl(field, null, ViewModality.EDIT));
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP_LINK, "name", "text", "testo", "", ATTR_VIEW_DANGER), FormControlWriter.writeControl(field, null, ViewModality.VIEW));

        // Empty
        field.setHidden(true);
        assertEquals(StringUtil.EMPTY, FormControlWriter.writeControl(field, null, ViewModality.EDIT));
    }

    // Input - Date
    @Test
    void inputDateTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", DataTypes.DATE);
        field.setLocale(Locale.ITALY);
        field.setTooltip("tooltip");

        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "01/01/2020", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "date", "2020-01-01", "", ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "date", "2020-01-01", "", ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT));
    }

    // Input - DateTime
    @Test
    void inputDatetimeTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", DataTypes.DATETIME);
        field.setLocale(Locale.ITALY);
        field.setTooltip("tooltip");

        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020 10:11:12", "dd/MM/yyyy hh:mm:ss"));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "01/01/2020 10:11:12", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "datetime-local", "2020-01-01T10:11:12", ATTR_STEP, ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "datetime-local", "2020-01-01T10:11:12", ATTR_STEP, ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT));
    }

    // InputTotalizer - Integer
    @Test
    void inputTotalizerTest() throws FrameworkException {
        InputTotalizer field = new InputTotalizer("name", "description", DataTypes.INTEGER);
        field.setTooltip("tooltip");

        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "10", "", ATTR_VIEW), FormControlWriter.writeInput(field, null, ViewModality.VIEW));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "number", "10", "", ATTR_EDIT), FormControlWriter.writeInput(field, null, ViewModality.EDIT));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "number", "10", "", ATTR_EDIT), FormControlWriter.writeControl(field, null, ViewModality.EDIT));
    }

    @Test
    void textTest() throws FrameworkException {
        Text<String> field = Text.<String>builder()
                .name("name")
                .description("description")
                .dataType(DataTypes.STRING)
                .tooltip("tooltip")
                .build();

        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "", "", ATTR_VIEW), FormControlWriter.writeText(field, null));

        field.setValue("testo");
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "testo", "", ATTR_VIEW), FormControlWriter.writeText(field, null));

        // Controllo generico
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "text", "testo", "", ATTR_VIEW), FormControlWriter.writeControl(field, null, ViewModality.EDIT));

        // State
        field.setState(ControlState.DANGER);
        field.setStateMessage("Lorem ipsum");
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP, "name", "text", "testo", "", ATTR_VIEW_DANGER), FormControlWriter.writeControl(field, null, ViewModality.EDIT));

        // Link
        field.setBaseLink("/api/");
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP_LINK, "name", "text", "testo", "", ATTR_VIEW_DANGER), FormControlWriter.writeControl(field, null, ViewModality.EDIT));
        assertEquals(MessageFormat.format(CTRL_INPUT_GROUP_LINK, "name", "text", "testo", "", ATTR_VIEW_DANGER), FormControlWriter.writeControl(field, null, ViewModality.VIEW));
    }

    @Test
    void textTotalizerTest() throws FrameworkException {
        TextTotalizer field = TextTotalizer.builder()
                .name("name")
                .description("description")
                .dataType(DataTypes.INTEGER)
                .tooltip("tooltip")
                .build();

        // TextTotalizer field = new TextTotalizer("name", "description",  DataTypes.INTEGER);
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "number", "", "", ATTR_VIEW), FormControlWriter.writeTextTotalizer(field, null));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "number", "10", "", ATTR_VIEW), FormControlWriter.writeTextTotalizer(field, null));

        // Generico controllo
        assertEquals(MessageFormat.format(CTRL_INPUT, "name", "number", "10", "", ATTR_VIEW), FormControlWriter.writeControl(field, null, ViewModality.EDIT));
    }

}

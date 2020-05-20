package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Locale;

import org.junit.Test;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.CheckBox;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.DecodedText;
import it.eg.sloth.form.fields.field.impl.Hidden;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextArea;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;

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
 *
 */
public class TextControlWriterTest {

    private static final String BASE_CHECKBOX = "<div class=\"custom-control custom-checkbox\"><input type=\"checkbox\" class=\"custom-control-input\"{0} disabled=\"\"/><span class=\"custom-control-label\"></span></div>";

    @Test
    public void buttonTest() throws FrameworkException {
        Button field = new Button("name", "description", "tooltip");
        assertEquals(FormControlWriter.writeButton(field, null, null), TextControlWriter.writeButton(field));
    }

    @Test
    public void checkBoxTest() throws FrameworkException {
        CheckBox<String> field = new CheckBox<String>("name", "description", "tooltip", DataTypes.STRING);
        assertEquals(MessageFormat.format(BASE_CHECKBOX, ""), TextControlWriter.writeCheckBox(field));

        field.setChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX, " checked=\"\""), TextControlWriter.writeCheckBox(field));

        field.setUnChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX, ""), TextControlWriter.writeCheckBox(field));

        // Generico controllo
        assertEquals(MessageFormat.format(BASE_CHECKBOX, ""), TextControlWriter.writeCheckBox(field));
    }

    @Test
    public void comboBoxTest() throws FrameworkException {
        ComboBox<String> field = new ComboBox<String>("name", "description", "tooltip", DataTypes.STRING);
        field.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeComboBox(field));

        field.setValue("A");
        assertEquals("Scelta A", TextControlWriter.writeComboBox(field));

        // Controllo generico
        assertEquals("Scelta A", TextControlWriter.writeControl(field));
    }

    @Test
    public void decodedTextTest() throws FrameworkException {
        DecodedText<String> field = new DecodedText<String>("name", "description", "tooltip", DataTypes.STRING);
        field.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeDecodedText(field));

        field.setValue("A");
        assertEquals("Scelta A", TextControlWriter.writeDecodedText(field));

        // Controllo generico
        assertEquals("Scelta A", TextControlWriter.writeControl(field));
    }

    @Test
    public void hiddenTest() throws FrameworkException {
        Hidden<String> field = new Hidden<String>("name", "description", "tooltip", DataTypes.STRING);

        assertEquals(TextControlWriter.writeHidden(field), FormControlWriter.writeHidden(field));

        field.setValue("testo");
        assertEquals(TextControlWriter.writeHidden(field), FormControlWriter.writeHidden(field));

        // Controllo generico
        assertEquals(TextControlWriter.writeHidden(field), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void inputTest() throws FrameworkException {
        Input<String> field = new Input<String>("name", "description", "tooltip", DataTypes.STRING);
        assertEquals(StringUtil.EMPTY, TextControlWriter.writeInput(field));

        field.setValue("testo");
        assertEquals("testo", TextControlWriter.writeInput(field));

        // Generico controllo
        assertEquals("testo", TextControlWriter.writeControl(field));
    }

    @Test
    public void inputDataTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", "tooltip", DataTypes.DATE);
        field.setLocale(Locale.ITALY);

        assertEquals(StringUtil.EMPTY, TextControlWriter.writeInput(field));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        assertEquals("01/01/2020", TextControlWriter.writeInput(field));

        // Generico controllo
        assertEquals("01/01/2020", TextControlWriter.writeControl(field));
    }

    @Test
    public void inputTotalizerTest() throws FrameworkException {
        InputTotalizer field = new InputTotalizer("name", "description", "tooltip", DataTypes.INTEGER);
        assertEquals(StringUtil.EMPTY, TextControlWriter.writeInput(field));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals("10", TextControlWriter.writeInput(field));

        // Generico controllo
        assertEquals("10", TextControlWriter.writeControl(field));
    }

    @Test
    public void textTest() throws FrameworkException {
        Text<String> field = new Text<String>("name", "description", "tooltip", DataTypes.STRING);
        assertEquals(StringUtil.EMPTY, TextControlWriter.writeText(field));

        field.setValue("testo");
        assertEquals("testo", TextControlWriter.writeText(field));

        // Generico controllo
        assertEquals("testo", TextControlWriter.writeControl(field));
    }

    @Test
    public void textAreaTest() throws FrameworkException {
        TextArea<String> field = new TextArea<String>("name", "description", "tooltip", DataTypes.STRING);
        assertEquals(StringUtil.EMPTY, TextControlWriter.writeTextArea(field));

        field.setValue("testo");
        assertEquals("testo", TextControlWriter.writeTextArea(field));

        // Generico controllo
        assertEquals("testo", TextControlWriter.writeControl(field));
    }

    @Test
    public void textTotalizerTest() throws FrameworkException {
        TextTotalizer field = new TextTotalizer("name", "description", "tooltip", DataTypes.INTEGER);
        assertEquals(StringUtil.EMPTY, TextControlWriter.writeText(field));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals("10", TextControlWriter.writeText(field));

        // Generico controllo
        assertEquals("10", TextControlWriter.writeControl(field));
    }

}

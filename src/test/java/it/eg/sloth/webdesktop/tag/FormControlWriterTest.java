package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Locale;

import org.junit.Test;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.AutoComplete;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.CheckBox;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.DecodedText;
import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.form.fields.field.impl.Hidden;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextArea;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;

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
public class FormControlWriterTest {

    private static final String BASE_AUTOCOMPLETE = "<input id=\"{0}\" name=\"{0}\" value=\"{1}\" class=\"form-control form-control-sm autoComplete\"{2}{3}/>";
    private static final String LINK_AUTOCOMPLETE = "<div class=\"input-group input-group-sm\"><input id=\"{0}\" name=\"{0}\" value=\"{1}\" class=\"form-control form-control-sm autoComplete\" disabled=\"\"/><div class=\"input-group-append\"><a href=\"{2}\" class=\"btn btn-outline-secondary\"><i class=\"fas fa-link\"></i></a></div></div>";

    private static final String BASE_BUTTON = "<button id=\"navigationprefix___button___name\" name=\"navigationprefix___button___name\" class=\"btn btn-outline-primary btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\"/>description</button>";

    private static final String BASE_CHECKBOX_VIS = "<div class=\"custom-control custom-checkbox\"><input id=\"name\" name=\"name\" type=\"checkbox\" class=\"custom-control-input\" value=\"S\" disabled=\"\"/><span class=\"custom-control-label\"></span></div>";
    private static final String BASE_CHECKBOX_MOD = "<div class=\"custom-control custom-checkbox\"><input id=\"name\" name=\"name\" type=\"checkbox\" class=\"custom-control-input\" value=\"S\"{0}/><label class=\"custom-control-label\" for=\"name\"></label></div>";

    private static final String BASE_COMBOBOX = "<select id=\"{0}\" name=\"{0}\" value=\"{1}\" class=\"form-control form-control-sm\"{2}><option value=\"\"></option><option value=\"A\"{3}>Scelta A</option><option value=\" B\"> Scelta B</option></select>";

    private static final String BASE_TEXT = "<input id=\"{0}\" name=\"{0}\" value=\"{1}\" class=\"form-control form-control-sm\" disabled=\"\"/>";

    private static final String BASE_FILE_VIEW = "<div class=\"input-group input-group-sm\"><a href=\"ClientiPage.html?idcliente=3\" class=\"form-control form-control-sm btn btn-outline-secondary\">Download</a></div>";
    private static final String BASE_FILE_MODIFY = "<div class=\"custom-file small\"><input id=\"{0}\" name=\"{0}\" type=\"file\" data-toggle=\"tooltip\"{1}><label class=\"custom-file-label\" for=\"name\">Choose file</label></div>";

    private static final String BASE_HIDDEN = "<input id=\"{0}\" name=\"{0}\" type=\"hidden\" value=\"{1}\"/>";

    private static final String BASE_INPUT = "<input id=\"{0}\" name=\"{0}\" type=\"{1}\" value=\"{2}\"{3} class=\"form-control form-control-sm\"{4}{5}/>";

    private static final String BASE_TEXTAREA = "<textarea id=\"{0}\" name=\"{0}\" class=\"" + BootStrapClass.CONTROL_CLASS + "\"{1}>{2}</textarea>";

    @Test
    public void autoCompleteTest() throws FrameworkException {
        Fields fields = new Fields("Master");
        AutoComplete<String> autocomplete = new AutoComplete<String>("name", "description", "tooltip", DataTypes.STRING);
        autocomplete.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE, "name", "", "", " disabled=\"\""), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        autocomplete.setValue("A");
        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE, "name", "Scelta A", "", " disabled=\"\""), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE, "name", "Scelta A", " fields=\"master\"", ""), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_AUTOCOMPLETE, "name", "Scelta A", " fields=\"master\"", ""), FormControlWriter.writeControl(autocomplete, fields, null, ViewModality.VIEW_MODIFICA, null, null));

        // Link
        autocomplete.setBaseLink("destPage.html?name=");
        assertEquals(MessageFormat.format(LINK_AUTOCOMPLETE, "name", "Scelta A", "destPage.html?name=A"), FormControlWriter.writeAutoComplete(autocomplete, fields, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
    }

    @Test
    public void buttonTest() throws FrameworkException {
        Button field = new Button("name", "description", "tooltip");
        assertEquals(BASE_BUTTON, FormControlWriter.writeButton(field, null, null));

        // Controllo generico
        assertEquals(BASE_BUTTON, FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void checkBoxTest() throws FrameworkException {
        CheckBox<String> field = new CheckBox<String>("name", "description", "tooltip", DataTypes.STRING);
        assertEquals(BASE_CHECKBOX_VIS, FormControlWriter.writeCheckBox(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        field.setChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX_MOD, " checked=\"\""), FormControlWriter.writeCheckBox(field, ViewModality.VIEW_MODIFICA, null, null));

        field.setUnChecked();
        assertEquals(MessageFormat.format(BASE_CHECKBOX_MOD, ""), FormControlWriter.writeCheckBox(field, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_CHECKBOX_MOD, ""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void comboBoxTest() throws FrameworkException {
        ComboBox<String> field = new ComboBox<String>("name", "description", "tooltip", DataTypes.STRING);
        field.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(MessageFormat.format(BASE_COMBOBOX, "name", "", " disabled=\"\"", ""), FormControlWriter.writeComboBox(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        field.setValue("A");
        assertEquals(MessageFormat.format(BASE_COMBOBOX, "name", "A", "", " selected=\"selected\""), FormControlWriter.writeComboBox(field, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_COMBOBOX, "name", "A", "", " selected=\"selected\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void decodedTextTest() throws FrameworkException {
        DecodedText<String> field = new DecodedText<String>("name", "description", "tooltip", DataTypes.STRING);
        field.setDecodeMap(new StringDecodeMap("A,Scelta A; B, Scelta B"));

        assertEquals(MessageFormat.format(BASE_TEXT, "name", ""), FormControlWriter.writeDecodedText(field, null, null));

        field.setValue("A");
        assertEquals(MessageFormat.format(BASE_TEXT, "name", "Scelta A"), FormControlWriter.writeDecodedText(field, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_TEXT, "name", "Scelta A"), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void fileTest() throws FrameworkException {
        File field = new File("name", "description", "tooltip");

        assertEquals(MessageFormat.format(BASE_FILE_VIEW, "name", "text", "", "", " disabled=\"\""), FormControlWriter.writeFile(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
        assertEquals(MessageFormat.format(BASE_FILE_MODIFY, "name", " data-placement=\"bottom\" title=\"tooltip\" class=\"custom-file-input\""), FormControlWriter.writeFile(field, ViewModality.VIEW_MODIFICA, null, null));


        // Controllo generico
        assertEquals(MessageFormat.format(BASE_FILE_MODIFY, "name", " data-placement=\"bottom\" title=\"tooltip\" class=\"custom-file-input\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void hiddenTest() throws FrameworkException {
        Hidden<String> field = new Hidden<String>("name", "description", "tooltip", DataTypes.STRING);

        assertEquals(MessageFormat.format(BASE_HIDDEN, "name", ""), FormControlWriter.writeHidden(field));

        field.setValue("testo");
        assertEquals(MessageFormat.format(BASE_HIDDEN, "name", "testo"), FormControlWriter.writeHidden(field));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_HIDDEN, "name", "testo"), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }


    @Test
    public void inputTest() throws FrameworkException {
        Input<String> field = new Input<String>("name", "description", "tooltip", DataTypes.STRING);

        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        field.setValue("testo");
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "testo", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "testo", "", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "testo", "", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    /**
     * input - Date
     *
     * @throws FrameworkException
     */
    @Test
    public void inputDateTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", "tooltip", DataTypes.DATE);
        field.setLocale(Locale.ITALY);

        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "01/01/2020", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "date", "2020-01-01", "", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "date", "2020-01-01", "", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    /**
     * input - Datetime
     *
     * @throws FrameworkException
     */
    @Test
    public void inputDatetimeTest() throws FrameworkException {
        Input<Timestamp> field = new Input<Timestamp>("name", "description", "tooltip", DataTypes.DATETIME);
        field.setLocale(Locale.ITALY);

        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        field.setValue(TimeStampUtil.parseTimestamp("01/01/2020 10:11:12", "dd/MM/yyyy hh:mm:ss"));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "01/01/2020 10:11:12", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "datetime-local", "2020-01-01T10:11:12", " step=\"1\"", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "datetime-local", "2020-01-01T10:11:12", " step=\"1\"", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void inputTotalizerTest() throws FrameworkException {
        InputTotalizer field = new InputTotalizer("name", "description", "tooltip", DataTypes.INTEGER);

        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "10", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "number", "10", "", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeInput(field, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "number", "10", "", "", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void textTest() throws FrameworkException {
        Text<String> field = new Text<String>("name", "description", "tooltip", DataTypes.STRING);
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeText(field, null, null));

        field.setValue("testo");
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "testo", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeText(field, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "testo", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void textAreaTest() throws FrameworkException {
        TextArea<String> field = new TextArea<String>("name", "description", "tooltip", DataTypes.STRING);
        assertEquals(MessageFormat.format(BASE_TEXTAREA, "name", " disabled=\"\"", ""), FormControlWriter.writeTextArea(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));

        field.setValue("testo");
        assertEquals(MessageFormat.format(BASE_TEXTAREA, "name", " disabled=\"\"", "testo"), FormControlWriter.writeTextArea(field, ViewModality.VIEW_VISUALIZZAZIONE, null, null));
        assertEquals(MessageFormat.format(BASE_TEXTAREA, "name", "", "testo"), FormControlWriter.writeTextArea(field, ViewModality.VIEW_MODIFICA, null, null));

        // Controllo generico
        assertEquals(MessageFormat.format(BASE_TEXTAREA, "name", "", "testo"), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

    @Test
    public void textTotalizerTest() throws FrameworkException {
        TextTotalizer field = new TextTotalizer("name", "description", "tooltip", DataTypes.INTEGER);
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeTextTotalizer(field, null, null));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "10", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeTextTotalizer(field, null, null));

        // Generico controllo
        assertEquals(MessageFormat.format(BASE_INPUT, "name", "text", "10", "", " disabled=\"\"", " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"tooltip\""), FormControlWriter.writeControl(field, null, null, ViewModality.VIEW_MODIFICA, null, null));
    }

}

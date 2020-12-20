package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.LabelControlWriter;
import org.junit.Test;

import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;

public class LabelControlWriterTest {
    private static final String LABEL = "<label for=\"{0}\" class=\"col-form-label float-right form-control-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"{2}\">{1}{3}:&nbsp;</label>";

    private static final String AAA = "<div class=\"m-0 pl-1 pr-1 col-1\"><label for=\"{0}\" class=\"col-form-label float-right form-control-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"{2}\">{1}{3}:&nbsp;</label></div><div class=\"m-0 pl-1 pr-1 col-1\"><input id=\"{0}\" name=\"{0}\" type=\"text\" value=\"\" class=\"form-control form-control-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"{2}\"/></div>";

    private static final String BBB = "<div class=\"m-0 pl-1 pr-1 col-1\"><button id=\"navigationprefix___button___{0}\" name=\"navigationprefix___button___{0}\" class=\"btn btn-outline-primary btn-sm\">{1}</button></div>";

    @Test
    public void labelTest() throws FrameworkException {
        // Controlli con label
        Input<String> field = new Input<String>("name", "description", DataTypes.STRING);
        field.setTooltip("tooltip");

        assertEquals(MessageFormat.format(LABEL, "name", "description", "tooltip", ""), LabelControlWriter.writeLabel(field));

        field.setRequired(true);
        assertEquals(MessageFormat.format(LABEL, "name", "description", "tooltip", "*"), LabelControlWriter.writeLabel(field));

        // Controlli senza label (Button)
        Button button = new Button("name", "description");
        assertEquals(StringUtil.EMPTY, LabelControlWriter.writeLabel(button));
    }

    @Test
    public void labelControlTest() throws FrameworkException {
        // Controlli con label
        Input<String> field = new Input<String>("name", "description", DataTypes.STRING);
        field.setTooltip("tooltip");

        assertEquals(MessageFormat.format(AAA, "name", "description", "tooltip", ""), LabelControlWriter.writeLblControl(field, null, ViewModality.VIEW_MODIFICA, "1cols", "1cols"));

        field.setRequired(true);
        assertEquals(MessageFormat.format(AAA, "name", "description", "tooltip", "*"), LabelControlWriter.writeLblControl(field, null, ViewModality.VIEW_MODIFICA, "1cols", "1cols"));

        // Controlli senza label (Button)
        Button button = new Button("name", "description");
        assertEquals(MessageFormat.format(BBB, "name", "description"), LabelControlWriter.writeLblControl(button, null, ViewModality.VIEW_MODIFICA, "1cols", "1cols"));
    }
}

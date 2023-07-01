package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.AbstractTest;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.LabelControlWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabelControlWriterTest extends AbstractTest {

    Input<String> field;

    @BeforeEach
    public void init() {
        field = new Input<String>("name", "description", DataTypes.STRING);
        field.setTooltip("tooltip");
    }

    @Test
    void label() {
        assertEqualsStr("label.html", LabelControlWriter.writeLabel(field));
    }

    @Test
    void label_required() {
        field.setRequired(true);

        assertEqualsStr("label_required.html", LabelControlWriter.writeLabel(field));
    }

    @Test
    void label_button() {
        Button button = new Button("name", "description");
        assertEqualsStr("label_button.html",LabelControlWriter.writeLabel(button));
    }

    @Test
    void lblControl() throws FrameworkException {
        assertEqualsStr("lblControl.html", LabelControlWriter.writeLblControl(field, null, ViewModality.EDIT, "1cols", "1cols"));
    }

    @Test
    void lblControl_required() throws FrameworkException {
        field.setRequired(true);

        assertEqualsStr("lblControl_required.html", LabelControlWriter.writeLblControl(field, null, ViewModality.EDIT, "1cols", "1cols"));
    }

    @Test
    void lblControl_button() throws FrameworkException {
        Button button = new Button("name", "description");
        assertEqualsStr("lblControl_button.html", LabelControlWriter.writeLblControl(button, null, ViewModality.EDIT, "1cols", "1cols"));
    }

}
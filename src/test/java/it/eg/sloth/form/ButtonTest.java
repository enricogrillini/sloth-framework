package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ButtonTest {

    @Test
    public void buttonTest() {
        Button button = new Button("name", "description", "tooltip", false, false, null, null);
        assertFalse(button.isHidden());
        assertFalse(button.isDisabled());

        button = new Button("name", "description", "tooltip", true, true, null, null);
        assertTrue(button.isHidden());
        assertTrue(button.isDisabled());

        button = new Button("name", "description", "tooltip", null, null, null, null);
        assertFalse(button.isHidden());
        assertFalse(button.isDisabled());
    }
}

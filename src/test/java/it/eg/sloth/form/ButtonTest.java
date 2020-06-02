package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ButtonTest {

    @Test
    public void buttonTest() {
        Button button = new Button("name", "description");
        assertEquals("name", button.getName());
        assertEquals(Locale.getDefault(), button.getLocale());
        assertFalse(button.isHidden());
        assertFalse(button.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY, button.getButtonType());
    }


    @Test
    public void buttonBuilderTest() {
        Button link = Button.builder()
                .name("Name")
                .locale(null)
                .description("description")
                .tooltip("tooltip")
                .hidden(null)
                .disabled(null)
                .buttonType(null)
                .imgHtml("imgHtml")
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.getDefault(), link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertFalse(link.isDisabled());
    }
}

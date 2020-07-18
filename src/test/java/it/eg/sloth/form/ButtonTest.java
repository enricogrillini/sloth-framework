package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

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
        // Default
        Button button = Button.builder()
                .name("Name")
                .description("description")
                .build();

        assertEquals("name", button.getName());
        assertEquals("description", button.getDescription());
        assertEquals(Locale.getDefault(), button.getLocale());
        assertFalse(button.isHidden());
        assertFalse(button.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY, button.getButtonType());

        // False
        button = Button.builder()
                .name("Name")
                .locale(Locale.ITALY)
                .description("description")
                .tooltip("tooltip")
                .hidden(false)
                .disabled(false)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", button.getName());
        assertEquals(Locale.ITALY, button.getLocale());
        assertFalse(button.isHidden());
        assertFalse(button.isDisabled());
        assertEquals(ButtonType.BTN_INFO, button.getButtonType());

        // True
        button = Button.builder()
                .name("Name")
                .locale(Locale.ITALY)
                .description("description")
                .tooltip("tooltip")
                .hidden(true)
                .disabled(true)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", button.getName());
        assertEquals(Locale.ITALY, button.getLocale());
        assertTrue(button.isHidden());
        assertTrue(button.isDisabled());
        assertEquals(ButtonType.BTN_INFO, button.getButtonType());

        // NewInstance
        Button button2 = button.newInstance();
        assertNotSame(button, button2);
        assertEquals(button.getName(), button2.getName());
    }
}

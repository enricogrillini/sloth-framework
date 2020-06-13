package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Link;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class LinkTest {

    @Test
    public void linkTest() {
        Link link = new Link("name", "description", "href");
        assertEquals("name", link.getName());
        assertEquals(Locale.getDefault(), link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY , link.getButtonType());
    }

    @Test
    public void linkBuilderTest() {
        Link link = Link.builder()
                .name("Name")
                .description("description")
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.getDefault(), link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY , link.getButtonType());

        link = Link.builder()
                .name("Name")
                .description("description")
                .locale(Locale.ITALY)
                .tooltip("tooltip")
                .hidden(false)
                .disabled(false)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.ITALY, link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertEquals(ButtonType.BTN_INFO , link.getButtonType());

        link = Link.builder()
                .name("Name")
                .description("description")
                .locale(Locale.ITALY)
                .tooltip("tooltip")
                .hidden(true)
                .disabled(true)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.ITALY, link.getLocale());
        assertTrue(link.isHidden());
        assertTrue(link.isDisabled());
        assertEquals(ButtonType.BTN_INFO , link.getButtonType());

        // NewInstance
        Link link2 = link.newInstance();
        assertFalse(link == link2);
        assertEquals(link.getName(), link2.getName());
    }
}

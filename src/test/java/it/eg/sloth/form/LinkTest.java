package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Link;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
                .locale(null)
                .description("description")
                .tooltip("tooltip")
                .hidden(null)
                .disabled(null)
                .buttonType(null)
                .imgHtml("imgHtml")
                .href("href")
                .target("target")
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.getDefault(), link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY , link.getButtonType());
    }
}

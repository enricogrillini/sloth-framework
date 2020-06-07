package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.form.fields.field.impl.Link;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FileTest {

    @Test
    public void fileTest() {
        File file = new File("name", "description");
        assertEquals("name", file.getName());
        assertEquals(Locale.getDefault(), file.getLocale());
        assertEquals("name", file.getAlias());
        assertFalse(file.isRequired());
        assertFalse(file.isReadOnly());
        assertFalse(file.isHidden());
        assertEquals(ViewModality.VIEW_AUTO, file.getViewModality());
        assertEquals(0, file.getMaxSize());
    }


    @Test
    public void fileBuilderTest() {
        File file = File.builder()
                .name("Name")
                .alias(null)
                .description("description")
                .tooltip("tooltip")
                .required(null)
                .readOnly(null)
                .hidden(null)
                .viewModality(null)
                .maxSize(null)
                .build();

        assertEquals("name", file.getName());
        assertEquals(Locale.getDefault(), file.getLocale());
        assertEquals("name", file.getAlias());
        assertFalse(file.isRequired());
        assertFalse(file.isReadOnly());
        assertFalse(file.isHidden());
        assertEquals(ViewModality.VIEW_AUTO, file.getViewModality());
        assertEquals(0, file.getMaxSize());
    }
}

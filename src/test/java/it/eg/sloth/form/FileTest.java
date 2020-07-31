package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

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

        file = File.builder()
                .name("Name")
                .alias(null)
                .description("description")
                .tooltip("tooltip")
                .required(false)
                .readOnly(false)
                .hidden(false)
                .viewModality(ViewModality.VIEW_MODIFICA)
                .maxSize(0)
                .build();

        assertEquals("name", file.getName());
        assertEquals(Locale.getDefault(), file.getLocale());
        assertEquals("name", file.getAlias());
        assertFalse(file.isRequired());
        assertFalse(file.isReadOnly());
        assertFalse(file.isHidden());
        assertEquals(ViewModality.VIEW_MODIFICA, file.getViewModality());
        assertEquals(0, file.getMaxSize());

        file = File.builder()
                .locale(Locale.ITALY)
                .name("Name")
                .alias("name")
                .description("description")
                .tooltip("tooltip")
                .required(true)
                .readOnly(true)
                .hidden(true)
                .viewModality(ViewModality.VIEW_MODIFICA)
                .maxSize(0)
                .build();

        assertEquals("name", file.getName());
        assertEquals(Locale.ITALY, file.getLocale());
        assertEquals("name", file.getAlias());
        assertTrue(file.isRequired());
        assertTrue(file.isReadOnly());
        assertTrue(file.isHidden());
        assertEquals(ViewModality.VIEW_MODIFICA, file.getViewModality());
        assertEquals(0, file.getMaxSize());

        // NewInstance
        File file2 = file.newInstance();
        assertNotSame(file, file2);
        assertEquals(file.getName(), file2.getName());
    }
}

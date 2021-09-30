package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
 */
class FileTest {

    @Test
    void fileTest() {
        File file = new File("name", "description");
        assertEquals("name", file.getName());
        assertEquals(Locale.getDefault(), file.getLocale());
        assertEquals("name", file.getAlias());
        assertFalse(file.isRequired());
        assertFalse(file.isReadOnly());
        assertFalse(file.isHidden());
        assertEquals(ViewModality.AUTO, file.getViewModality());
        assertEquals(0, file.getMaxSize());
    }

    @Test
    void fileBuilderTest1() {
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
        assertEquals(ViewModality.AUTO, file.getViewModality());
        assertEquals(0, file.getMaxSize());

        file = File.builder()
                .name("Name")
                .alias(null)
                .description("description")
                .tooltip("tooltip")
                .required(false)
                .readOnly(false)
                .hidden(false)
                .viewModality(ViewModality.EDIT)
                .maxSize(0)
                .build();

        assertEquals("name", file.getName());
        assertEquals(Locale.getDefault(), file.getLocale());
        assertEquals("name", file.getAlias());
        assertFalse(file.isRequired());
        assertFalse(file.isReadOnly());
        assertFalse(file.isHidden());
        assertEquals(ViewModality.EDIT, file.getViewModality());
        assertEquals(0, file.getMaxSize());
    }

    @Test
    void fileBuilderTest2() {
        File file = File.builder()
                .locale(Locale.ITALY)
                .name("Name")
                .alias("name")
                .description("description")
                .tooltip("tooltip")
                .required(true)
                .readOnly(true)
                .hidden(true)
                .viewModality(ViewModality.EDIT)
                .maxSize(0)
                .build();

        assertEquals("name", file.getName());
        assertEquals(Locale.ITALY, file.getLocale());
        assertEquals("name", file.getAlias());
        assertTrue(file.isRequired());
        assertTrue(file.isReadOnly());
        assertTrue(file.isHidden());
        assertEquals(ViewModality.EDIT, file.getViewModality());
        assertEquals(0, file.getMaxSize());

        // NewInstance
        File file2 = file.newInstance();
        assertNotSame(file, file2);
        assertEquals(file.getName(), file2.getName());
    }
}

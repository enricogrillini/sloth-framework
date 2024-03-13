package it.eg.sloth.form;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.form.fields.field.impl.MultipleFile;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
class MultipleFileTest {

    @Test
    void fileTest() throws FrameworkException {
        MultipleFile file = new MultipleFile("name", "description");
        assertEquals("name", file.getName());
        assertEquals(Locale.getDefault(), file.getLocale());
        assertEquals("name", file.getAlias());
        assertEquals(DataTypes.STRING, file.getDataType());
        assertFalse(file.isRequired());
        assertFalse(file.isReadOnly());
        assertFalse(file.isHidden());
        assertEquals(ViewModality.AUTO, file.getViewModality());
        assertEquals(0, file.getMaxSize());
    }

    @Test
    void fileBuilderTest1() {
        MultipleFile file = MultipleFile.builder()
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

        file = MultipleFile.builder()
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
        MultipleFile file = MultipleFile.builder()
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
        MultipleFile file2 = file.newInstance();
        assertNotSame(file, file2);
        assertEquals(file.getName(), file2.getName());
    }

    @Test
    void postTest() throws ServletException, IOException, FrameworkException {
        Row row = new Row();
        row.setString("files", "f1.txt|f2.txt");

        MultipleFile files = MultipleFile.builder()
                .locale(Locale.ITALY)
                .name("files")
                .alias("files")
                .description("description")
                .tooltip("tooltip")
                .required(true)
                .readOnly(false)
                .hidden(true)
                .viewModality(ViewModality.EDIT)
                .maxSize(0)
                .build();

        // Validate - Nessun file presente
        assertFalse(files.validate(new MessageList()));

        // Validate - Dopo "copyFromDataSource" per file precedentemente inseriti
        files.copyFromDataSource(row);
        assertEquals(2, files.getValue().size());
        assertTrue(files.validate(new MessageList()));

        // Validate - Dopo "post" WebRequest per nuovi file
        files.setValue(null);

        WebRequest webRequest = TestFactory.getMockedWebRequest(
                new TestFactory.FramePart("files", "file1.txt", "prova1".getBytes(StandardCharsets.UTF_8)),
                new TestFactory.FramePart("files", "file2.txt", "prova2".getBytes(StandardCharsets.UTF_8))
        );

        files.post(webRequest);
        assertEquals(2, files.getParts().size());
        assertTrue(files.validate(new MessageList()));
    }


}

package it.eg.sloth.form;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.common.BffFieldsForFieldTest;
import it.eg.sloth.form.fields.field.impl.RadioButtons;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
class RadioButtonsTest {

    private MessageList messageList;
    private RadioButtons<String> field;

    @BeforeEach
    void init() {
        messageList = new MessageList();

        field = RadioButtons.<String>builder()
                .name("FieldName")
                .description("FieldDescription")
                .dataType(DataTypes.STRING)
                .decodeMap(new StringDecodeMap("A", "B", "C"))
                .build();
    }

    @Test
    void builder() {
        assertEquals("fieldname", field.getName());
        assertEquals("FieldDescription", field.getDescription());
        assertEquals("fieldname", field.getAlias());
        assertFalse(field.isRequired());
        assertFalse(field.isReadOnly());
        assertFalse(field.isHidden());
        assertEquals(ViewModality.AUTO, field.getViewModality());
    }

    @Test
    void validate() throws FrameworkException {
        field.setData("A");
        field.validate(messageList);

        assertTrue(field.isValid());
        assertNull(field.check());
        assertTrue(messageList.isEmpty());
    }

    @Test
    void validate_KO() throws FrameworkException {
        field.setData("X");
        field.validate(messageList);

        assertFalse(field.isValid());
        assertNotNull(field.check());
        assertNotNull(field.check().getSeverity());

        assertFalse(messageList.isEmpty());
        assertEquals(Level.WARN, messageList.get(0).getSeverity());
    }

    @Test
    void radioButtonsWebRequestTest() throws IOException, ServletException, FrameworkException {
        // Mock HttpServletRequest
        HashMap<String, String[]> map = new HashMap<>();
        map.put("fieldname", new String[]{"A", "B"});

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getParameterMap()).thenReturn(map);

        WebRequest webRequest = new WebRequest(request);

        // Test
        field.post(webRequest);
        assertNull(field.check());

        field.setRequired(true);
        field.post(webRequest);
        assertNull(field.check());

        field.setName("testo2");
        field.post(webRequest);
        assertNotNull(field.check());
    }

    @Test
    void radioButtonsBffTest() throws IOException, ServletException, FrameworkException {
        BffFieldsForFieldTest bffFields = new BffFieldsForFieldTest();
        bffFields.setFieldName("A");

        field.post(bffFields);
        assertNull(field.check());

        field.setRequired(true);
        field.post(bffFields);
        assertNull(field.check());

        field.setName("testo2");
        field.post(bffFields);
        assertNotNull(field.check());
    }
}

package it.eg.sloth.form;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.fields.field.impl.RadioButtons;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
class ComboBoxTest {

    private MessageList messageList;
    private ComboBox<String> field;

    @BeforeEach
    void init() {
        messageList = new MessageList();

        field = ComboBox.<String>builder()
                .name("FieldName")
                .description("FieldDescription")
                .dataType(DataTypes.STRING)
                .decodeMap(new StringDecodeMap("A", "B", "C"))
                .build();
    }

    @Test
    void builder() throws FrameworkException {
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

}

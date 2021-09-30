package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.ForceCase;
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
class InputTest {

    @Test
    void validateInputOk() throws FrameworkException {
        MessageList messageList = new MessageList();

        // Valid
        Input<BigDecimal> input = new Input<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        input.setValue(BigDecimal.valueOf(10));
        input.validate(messageList);

        assertTrue(input.isValid());
        assertEquals(null, input.check());
        assertTrue(messageList.isEmpty());
    }

    @Test
    void validateInputKo() throws FrameworkException {
        MessageList messageList = new MessageList();

        // Valid
        Input<BigDecimal> input = new Input<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        input.setData("XX");
        input.validate(messageList);

        assertFalse(input.isValid());
        assertNotEquals(null, input.check().getSeverity());
        assertFalse(messageList.isEmpty());
        assertEquals(Level.WARN, messageList.get(0).getSeverity());

        assertThrows(FrameworkException.class, () -> {
            input.getValue();
        });

        assertEquals("XX", input.escapeJsValue());
    }

    @Test
    void inputBuilderTest1() {
        // Default
        Input<String> input = Input.<String>builder()
                .name("Name")
                .description("description")
                .build();

        assertEquals("name", input.getName());
        assertEquals("description", input.getDescription());
        assertEquals("name", input.getAlias());
        assertFalse(input.isRequired());
        assertFalse(input.isReadOnly());
        assertFalse(input.isHidden());
        assertEquals(ViewModality.AUTO, input.getViewModality());
        assertEquals(ForceCase.NONE, input.getForceCase());
        assertEquals(0, input.getMaxLength());

        // False
        input = Input.<String>builder()
                .name("Name")
                .description("description")
                .alias("Alias")
                .required(false)
                .readOnly(false)
                .hidden(false)
                .viewModality(ViewModality.EDIT)
                .forceCase(ForceCase.UPPER)
                .maxLength(10)
                .build();

        assertEquals("name", input.getName());
        assertEquals("description", input.getDescription());
        assertEquals("alias", input.getAlias());
        assertFalse(input.isRequired());
        assertFalse(input.isReadOnly());
        assertFalse(input.isHidden());
        assertEquals(ViewModality.EDIT, input.getViewModality());
        assertEquals(ForceCase.UPPER, input.getForceCase());
        assertEquals(10, input.getMaxLength());
    }

    @Test
    void inputBuilderTest2() {
        // True
        Input<String> input = Input.<String>builder()
                .name("Name")
                .description("description")
                .alias("Alias")
                .required(true)
                .readOnly(true)
                .hidden(true)
                .viewModality(ViewModality.EDIT)
                .forceCase(ForceCase.UPPER)
                .maxLength(10)
                .build();

        assertEquals("name", input.getName());
        assertEquals("description", input.getDescription());
        assertEquals("alias", input.getAlias());
        assertTrue(input.isRequired());
        assertTrue(input.isReadOnly());
        assertTrue(input.isHidden());
        assertEquals(ViewModality.EDIT, input.getViewModality());
        assertEquals(ForceCase.UPPER, input.getForceCase());
        assertEquals(10, input.getMaxLength());

        // NewInstance
        Input<String> input2 = input.newInstance();
        assertNotSame(input, input2);
        assertEquals(input.getName(), input2.getName());
    }
}

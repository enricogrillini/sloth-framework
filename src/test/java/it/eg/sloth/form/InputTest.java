package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.ForceCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
class InputTest {

    private MessageList messageList;
    private Input<BigDecimal> field;

    @BeforeEach
    void init() {
        messageList = new MessageList();

        field = Input.<BigDecimal>builder()
                .name("FieldName")
                .description("FieldDescription")
                .dataType(DataTypes.INTEGER)
                .build();
    }

    @Test
    void builderDefault() {
        // Test per verificare l'impostazione di default dei campi accessori
        assertEquals("fieldname", field.getName());
        assertEquals("FieldDescription", field.getDescription());
        assertEquals("fieldname", field.getAlias());
        assertFalse(field.isRequired());
        assertFalse(field.isReadOnly());
        assertFalse(field.isHidden());
        assertEquals(ViewModality.AUTO, field.getViewModality());
        assertEquals(ForceCase.NONE, field.getForceCase());
        assertEquals(0, field.getMaxLength());
    }

    @Test
    void builderExplicit() {
        // Test per verificare l'impostazione esplicita dei campi accessori
        Input<String> input = Input.<String>builder()
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
    void forceCase() throws FrameworkException {
        // Valid
        Input<String> input = new Input<String>("Prova", "Prova", DataTypes.STRING);

        input.setForceCase(ForceCase.UPPER_TRIM);
        input.setValue("   ppp   ");
        assertEquals("PPP", input.getValue());

        input.setForceCase(ForceCase.LOWER_TRIM);
        input.setValue("   ppp   ");
        assertEquals("ppp", input.getValue());

        input.setForceCase(ForceCase.INIT_CAP_TRIM);
        input.setValue("   ppp ggg   ");
        assertEquals("Ppp Ggg", input.getValue());
    }

    @Test
    void newInstance() {
        Input<BigDecimal> fieldClone = field.newInstance();
        assertNotSame(field, fieldClone);
        assertEquals(field.getName(), fieldClone.getName());
    }

    @Test
    void validate() throws FrameworkException {
        field.setValue(BigDecimal.valueOf(10));
        field.validate(messageList);

        assertTrue(field.isValid());
        assertEquals(null, field.check());
        assertTrue(messageList.isEmpty());
    }

    @Test
    void validate_KO() throws FrameworkException {
        field.setData("XX");
        field.validate(messageList);

        assertFalse(field.isValid());
        assertNotEquals(null, field.check().getSeverity());
        assertFalse(messageList.isEmpty());
        assertEquals(Level.WARN, messageList.get(0).getSeverity());

        assertThrows(FrameworkException.class, () -> {
            field.getValue();
        });

        assertEquals("XX", field.escapeJsValue());
    }


}

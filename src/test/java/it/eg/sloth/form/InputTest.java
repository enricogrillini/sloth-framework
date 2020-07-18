package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.ForceCase;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InputTest {

    @Test
    public void validateInputOk() throws FrameworkException {
        MessageList messageList = new MessageList();

        // Valid
        Input<BigDecimal> input = new Input<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        input.setValue(BigDecimal.valueOf(10));
        input.validate(messageList);

        assertTrue(input.isValid());
        assertEquals(null, input.check());
        assertEquals(0, messageList.getList().size());
    }

    @Test
    public void validateInputKo() throws FrameworkException {
        MessageList messageList = new MessageList();

        // Valid
        Input<BigDecimal> input = new Input<BigDecimal>("Numero", "Numero", DataTypes.INTEGER);
        input.setData("XX");
        input.validate(messageList);

        assertFalse(input.isValid());
        assertNotEquals(null, input.check().getSeverity());
        assertEquals(1, messageList.getList().size());
        assertEquals(Level.WARN, messageList.getList().get(0).getSeverity());

        assertEquals(null, input.getValue());
        assertEquals("XX", input.escapeJsValue());
    }

    @Test
    public void inputBuilderTest() {
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
        assertEquals(ViewModality.VIEW_AUTO, input.getViewModality());
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
                .viewModality(ViewModality.VIEW_MODIFICA)
                .forceCase(ForceCase.UPPER)
                .maxLength(10)
                .build();

        assertEquals("name", input.getName());
        assertEquals("description", input.getDescription());
        assertEquals("alias", input.getAlias());
        assertFalse(input.isRequired());
        assertFalse(input.isReadOnly());
        assertFalse(input.isHidden());
        assertEquals(ViewModality.VIEW_MODIFICA, input.getViewModality());
        assertEquals(ForceCase.UPPER, input.getForceCase());
        assertEquals(10, input.getMaxLength());

        // True
        input = Input.<String>builder()
                .name("Name")
                .description("description")
                .alias("Alias")
                .required(true)
                .readOnly(true)
                .hidden(true)
                .viewModality(ViewModality.VIEW_MODIFICA)
                .forceCase(ForceCase.UPPER)
                .maxLength(10)
                .build();

        assertEquals("name", input.getName());
        assertEquals("description", input.getDescription());
        assertEquals("alias", input.getAlias());
        assertTrue(input.isRequired());
        assertTrue(input.isReadOnly());
        assertTrue(input.isHidden());
        assertEquals(ViewModality.VIEW_MODIFICA, input.getViewModality());
        assertEquals(ForceCase.UPPER, input.getForceCase());
        assertEquals(10, input.getMaxLength());

        // NewInstance
        Input<String> input2 = input.newInstance();
        assertNotSame(input, input2);
        assertEquals(input.getName(), input2.getName());
    }
}

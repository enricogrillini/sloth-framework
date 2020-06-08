package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InputTest {

    @Test
    public void validateInput() throws FrameworkException {
        MessageList messageList = new MessageList();

        Input<String> testo = new Input<String>("Testo", "Testo", DataTypes.STRING);
        testo.setValue("prova");
        testo.validate(messageList);

        assertTrue(testo.isValid());
        assertEquals(null, testo.check());
        assertEquals(0, messageList.getList().size());
    }
}

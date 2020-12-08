package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextTest {

    @Test
    public void validateText() throws FrameworkException {
        MessageList messageList = new MessageList();

        Text<String> testo = new Text<String>("Testo", "Testo", DataTypes.STRING);
        testo.setValue("prova");
        testo.validate(messageList);

        assertTrue(testo.isValid());
        assertEquals(null, testo.check());
        assertTrue(messageList.isEmpty());
    }

}

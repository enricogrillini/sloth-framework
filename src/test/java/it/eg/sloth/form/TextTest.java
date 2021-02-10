package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
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

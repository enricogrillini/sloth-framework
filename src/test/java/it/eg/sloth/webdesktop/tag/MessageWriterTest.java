package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.webdesktop.tag.pagearea.writer.MessageWriter;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class MessageWriterTest {

    private static final String CONTENT_TEMPLATE = "\n" +
            "    <div class=\"alert alert-dismissible alert-danger\">\n" +
            "{0}" +
            "     <p class=\"mb-0  font-weight-bold\">{1}</p>\n" +
            "    </div>\n" +
            "";

    @Test
    void writeMessageListTest() {
        MessageList messageList = new MessageList();
        messageList.addBaseError("Si è rotto");

        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "     <h4 class=\"alert-heading\">Errore!</h4>\n", "Si &egrave; rotto"), MessageWriter.writeMessages(messageList, true));
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "", "Si &egrave; rotto"), MessageWriter.writeMessages(messageList, false));
    }

}

package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.pagearea.writer.MessageWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class MessageWriterTest {

    private static final String MESSAGE_OPEN = ResourceUtil.normalizedResourceAsString("snippet-html/message/message-open.html");
    private static final String MESSAGE_CLOSE = ResourceUtil.normalizedResourceAsString("snippet-html/message/message-close.html");

    private static final String MESSAGE_CONTENT = ResourceUtil.normalizedResourceAsString("snippet-html/message/message-content.html");
    private static final String MESSAGE_CONTENT_SEVERITY = ResourceUtil.normalizedResourceAsString("snippet-html/message/message-content-severity.html");

    MessageList messageList;

    @BeforeEach
    void init() {
        messageList = new MessageList();
        messageList.addBaseError("Si è rotto");
    }

    @Test
    void writeDialogTest() {
        assertEquals(MESSAGE_OPEN, MessageWriter.openDialog(messageList));
        assertEquals(MESSAGE_CLOSE, MessageWriter.closeDialog());
    }

    @Test
    void writeMessageListTest() {
        assertEquals(MESSAGE_CONTENT, MessageWriter.writeMessages(messageList, false));
        assertEquals(MESSAGE_CONTENT_SEVERITY, MessageWriter.writeMessages(messageList, true));
    }

}

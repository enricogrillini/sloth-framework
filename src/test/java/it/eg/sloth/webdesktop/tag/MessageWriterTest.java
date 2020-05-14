package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;

import org.junit.Test;

import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.webdesktop.tag.pagearea.writer.MessageWriter;

public class MessageWriterTest {

  private static final String CONTENT_TEMPLATE = "\n" +
                                                 "    <div class=\"alert alert-dismissible alert-danger\">\n" +
                                                 "{0}" +
                                                 "     <p class=\"mb-0  font-weight-bold\">{1}</p>\n" +
                                                 "    </div>\n" +
                                                 "";

  @Test
  public void writeMessageListTest() {
    MessageList messageList = new MessageList();
    messageList.addBaseError("Si è rotto");

    assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "     <h4 class=\"alert-heading\">Errore!</h4>\n", "Si &egrave; rotto"), MessageWriter.writeMessages(messageList, true));
    assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "", "Si &egrave; rotto"), MessageWriter.writeMessages(messageList, false));
  }

}

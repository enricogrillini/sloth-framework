package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.pagearea.writer.MessageWriter;

public class CheckMessageTag extends WebDesktopTag {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {
    MessageList messageList = getWebDesktopDto().getMessageList();

    if (!messageList.isEmpty()) {
      if (messageList.isPopup()) {
        writeln(MessageWriter.openDialog(messageList));
        writeln(MessageWriter.writeMessages(messageList, false));
        writeln(MessageWriter.closeDialog());
      } else {
        writeln(MessageWriter.writeMessages(messageList, true));
      }
    }
    return SKIP_BODY;
  }

  @Override
  protected void endTag() throws Throwable {
    // NOP
  }

}

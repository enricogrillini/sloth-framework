package it.eg.sloth.webdesktop.api.model;

import it.eg.sloth.framework.common.message.MessageList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BffComponent {

  private boolean sessionExpired;
  private boolean wrongPage;
  private MessageList messageList;

  public BffComponent() {
    messageList = new MessageList();
  }

}

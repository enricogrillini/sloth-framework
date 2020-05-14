package it.eg.sloth.webdesktop.dto;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.dto.notificationcenter.NotificationCenter;
import it.eg.sloth.webdesktop.search.SearchManager;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class WebDesktopDto extends FrameComponent {

    @Setter
    private User user;
    @Setter
    private Form form;
    @Setter
    private String lastController;

    private MessageList messageList;
    private SearchManager searchManager;
    private NotificationCenter notificationCenter;
    private Map<String, Object> map;

    public WebDesktopDto() {
        this.user = null;
        this.form = null;
        this.lastController = null;
        this.messageList = new MessageList();
        this.searchManager = new SearchManager();
        this.notificationCenter = new NotificationCenter();

        this.map = new HashMap<>();
    }

}

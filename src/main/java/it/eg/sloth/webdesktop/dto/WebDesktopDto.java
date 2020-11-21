package it.eg.sloth.webdesktop.dto;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.search.SearchManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

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
@Getter
@ToString
public class WebDesktopDto {

    @Setter
    private User user;
    @Setter
    private Form form;
    @Setter
    private String lastController;

    private MessageList messageList;
    private SearchManager searchManager;
    private Map<String, Object> map;

    public WebDesktopDto() {
        this.user = null;
        this.form = null;
        this.lastController = null;
        this.messageList = new MessageList();
        this.searchManager = new SearchManager();

        this.map = new HashMap<>();
    }

}

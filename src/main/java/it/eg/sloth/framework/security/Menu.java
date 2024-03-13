package it.eg.sloth.framework.security;

import it.eg.sloth.framework.common.base.BaseFunction;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Data
public class Menu implements Iterable<Menu>, Serializable {

    private String code;
    private VoiceType voiceType;
    private String shortDescription;
    private String description;
    private String html;
    private String link;

    private LinkedHashMap<String, Menu> childs;

    public Menu() {
        this(null, null, null, null, null, null);
    }

    public Menu(String codice, VoiceType voiceType, String shortDescription, String description, String html, String link) {
        this.code = codice;
        this.voiceType = voiceType;
        this.shortDescription = shortDescription;
        this.description = description;
        this.html = html;
        this.link = link;
        this.childs = new LinkedHashMap<>();
    }

    public Iterator<Menu> iterator() {
        return childs.values().iterator();
    }

    public Menu add(Menu menu) {
        childs.put(menu.getCode(), menu);
        return this;
    }

    public Menu addChild(String codice, VoiceType tipoVoce, String descrizioneBreve, String descrizione, String imgUrl, String link) {
        return add(new Menu(codice, tipoVoce, descrizioneBreve, descrizione, imgUrl, link));
    }

    public boolean isActive(String page) {
        boolean active = page != null && getLink() != null && getLink().toLowerCase().startsWith(page.toLowerCase());

        return active || hasChildActive(page);
    }

    public boolean hasChildActive(String page) {
        for (Menu menu : childs.values()) {
            if (menu.isActive(page)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasChild() {
        return childs.size() > 0;
    }

    public List<Menu> searchFunction(String search) {
        List<Menu> list = new ArrayList<>();

        for (Menu menu : childs.values()) {
            String descrizione = menu.getShortDescription().toLowerCase() + menu.getDescription().toLowerCase();

            if (!menu.hasChild() && (descrizione.contains(search.toLowerCase()))) {
                list.add(menu);
            } else {
                list.addAll(menu.searchFunction(search));
            }
        }

        return list;
    }

}

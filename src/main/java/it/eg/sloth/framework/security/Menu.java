package it.eg.sloth.framework.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import lombok.Data;

@Data
public class Menu implements Iterable<Menu> {

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

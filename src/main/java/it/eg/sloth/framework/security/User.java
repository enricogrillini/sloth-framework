package it.eg.sloth.framework.security;

import it.eg.sloth.framework.common.base.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

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
public class User implements Serializable {

    BigDecimal id;
    String userid;
    String surname;
    String name;
    String email;
    String emailPassword;
    Locale locale;
    boolean avatar;
    String addInfo;

    private Set<String> enabledFunction;
    private Menu menu;
    private Menu userMenu;

    private Map<String, Map<String, String>> groupMap;

    public User() {
        this.locale = Locale.getDefault();
        this.avatar = false;

        this.enabledFunction = new HashSet<>();
        this.menu = new Menu();
        this.userMenu = new Menu();

        this.groupMap = new HashMap<>();
    }

    public boolean isLogged() {
        return getId() != null;
    }

    public boolean accessAllowed(String functionName) {
        return enabledFunction.contains(functionName);
    }

    public void addFunction(String functionName) {
        enabledFunction.add(functionName);
    }

    public String getAvatarLetter() {
        return StringUtil.substr(name, 0, 1) + StringUtil.substr(surname, 0, 1);
    }

    public Map<String, String> getGroupSetting(String group) {
        group = group.toLowerCase();

        Map<String, String> result = new HashMap<>();
        if (groupMap.containsKey(group)) {
            result.putAll(groupMap.get(group));
        }

        return result;
    }

    public String getSetting(String group, String key) {
        group = group.toLowerCase();
        key = key.toLowerCase();

        if (groupMap.containsKey(group) && groupMap.get(group).containsKey(key)) {
            return groupMap.get(group).get(key);
        }

        return StringUtil.EMPTY;
    }

    public void setSetting(String group, String key, String value) {
        group = group.toLowerCase();
        key = key.toLowerCase();

        groupMap.putIfAbsent(group, new HashMap<>());
        groupMap.get(group).put(key, value);
    }

}

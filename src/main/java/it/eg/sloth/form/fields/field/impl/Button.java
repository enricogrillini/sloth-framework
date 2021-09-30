package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.jaxb.form.ButtonType;
import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Locale;

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
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Button implements SimpleField {

    String name;
    Locale locale;

    String description;
    String tooltip;

    Boolean hidden;
    Boolean disabled;
    ButtonType buttonType;
    String imgHtml;
    String confirmMessage;
    Integer index;

    boolean pressed;

    public Button(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    @Override
    public Locale getLocale() {
        return this.locale == null ? Locale.getDefault() : this.locale;
    }

    public boolean isHidden() {
        return hidden != null && hidden;
    }

    public boolean isDisabled() {
        return disabled != null && disabled;
    }

    public ButtonType getButtonType() {
        return buttonType == null ? ButtonType.BTN_OUTLINE_PRIMARY : buttonType;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.BUTTON;
    }

    public String getHtlmName() {
        if (getIndex() != null)
            return NavigationConst.navStr(NavigationConst.BUTTON, getName(), "" + getIndex());
        else
            return NavigationConst.navStr(NavigationConst.BUTTON, getName());
    }

    @Override
    public void post(WebRequest webRequest) {
        String[] navigation = webRequest.getNavigation();

        if (navigation.length > 1 && navigation[1].equalsIgnoreCase(getName())) {
            setPressed(true);

            if (navigation.length <= 2) {
                // Indice non specificato
                setIndex(null);
            } else {
                // Indice specificato
                setIndex(Integer.parseInt(navigation[navigation.length - 1]));
            }

        } else {
            setPressed(false);
            setIndex(null);
        }
    }

    @Override
    public void post(BffFields bffFields) {
        // NOP
    }


    public Button newInstance() {
        return toBuilder().build();
    }
}

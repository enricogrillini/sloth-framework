package it.eg.sloth.form.fields.field.impl;

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
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Descrizione: implenta un link base che non richiede azioni server side
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Link implements SimpleField {

    String name;
    Locale locale;

    String description;
    String tooltip;
    Boolean hidden;
    Boolean disabled;
    ButtonType buttonType;
    String imgHtml;
    String href;
    String target;

    public Link(String name, String description, String href) {
        this.name = name.toLowerCase();
        this.locale = Locale.getDefault();
        this.description = description;
        this.href = href;
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
        return FieldType.LINK;
    }

    @Override
    public void post(WebRequest webRequest) {
        // NOP - Non sono gestite operazioni server side
    }

    @Override
    public void post(BffFields bffFields) {
        // NOP - Non sono gestite operazioni server side
    }

    @Override
    public Link newInstance() {
        return toBuilder().build();
    }
}

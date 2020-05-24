package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.AbstractSimpleField;
import it.eg.sloth.jaxb.form.ButtonType;
import lombok.Getter;
import lombok.Setter;

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
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class Button extends AbstractSimpleField {

    static final long serialVersionUID = 1L;

    boolean hidden;
    boolean disabled;
    ButtonType buttonType;
    String imgHtml;
    int index;

    boolean pressed;

    public Button(String name, String description, String tooltip) {
        this(name, description, tooltip, false, false, null, null);
    }

    public Button(String name, String description, String tooltip, Boolean hidden, Boolean disabled, ButtonType buttonType, String imgHtml) {
        super(name, description, tooltip);
        this.hidden = hidden != null && hidden;
        this.disabled = disabled != null && disabled;
        this.pressed = false;
        this.buttonType = buttonType == null ? ButtonType.BTN_OUTLINE_PRIMARY : buttonType;
        this.imgHtml = imgHtml;
        this.index = -1;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.BUTTON;
    }

    public String getHtlmName() {
        if (getIndex() >= 0)
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
                setIndex(-1);
            } else {
                // Indice specificato
                setIndex(Integer.parseInt(navigation[navigation.length - 1]));
            }

        } else {
            setPressed(false);
            setIndex(-1);
        }
    }

    @Override
    public void postEscaped(WebRequest webRequest, String encoding) {
        post(webRequest);
    }

}

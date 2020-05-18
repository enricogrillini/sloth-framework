package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.form.base.AbstractElement;
import it.eg.sloth.framework.common.casting.Casting;
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
public abstract class AbstractSimpleField extends AbstractElement implements it.eg.sloth.form.fields.field.SimpleField {

    String description;
    String tooltip;

    public AbstractSimpleField(String name, String description, String tooltip) {
        super(name);
        this.description = description;
        this.tooltip = tooltip;
    }

    public String getHtmlTooltip() {
        return Casting.getHtml(getTooltip());
    }

    public String getJsTooltip() {
        return Casting.getJs(getTooltip());
    }

    @Override
    public String getHtmlDescription() {
        return Casting.getHtml(getDescription());
    }

    @Override
    public String getJsDescription() {
        return Casting.getJs(getDescription());
    }

}

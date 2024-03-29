
package it.eg.sloth.form.tabsheet;

import it.eg.sloth.form.base.Element;
import it.eg.sloth.jaxb.form.BadgeType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Locale;

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
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Tab implements Element {

    private String name;
    private Locale locale;

    private String description;
    private String tooltip;
    private Boolean hidden;
    private Boolean disabled;

    private String badgeHtml;
    private BadgeType badgeType;

    public Tab(String name, String description) {
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

}

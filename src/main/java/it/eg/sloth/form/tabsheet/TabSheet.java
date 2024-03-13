package it.eg.sloth.form.tabsheet;

import it.eg.sloth.form.base.AbstractElements;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TabSheet extends AbstractElements<Tab> {

    private String currentTabName;

    public TabSheet(String name) {
        super(name);
    }

    /**
     * Imposta il primo tab visibile come corrente
     *
     * @return
     */
    public void setCurrentTab() {
        currentTabName = null;
        for (Tab tab : this) {
            if (!tab.isHidden()) {
                setCurrentTabName(tab.getName());
                break;
            }
        }
    }

    public void setCurrentTabName(String currentTabName) {
        this.currentTabName = currentTabName.toLowerCase();
    }

    public Tab getCurrentTab() {
        if (getElements() == null || getElements().isEmpty())
            return null;

        if (currentTabName == null || getElement(currentTabName) == null || getElement(currentTabName).isHidden()) {
            // Imposto il primo tab visibile come corrente
            setCurrentTab();
        }

        if (currentTabName != null) {
            return getElement(currentTabName);
        } else {
            return null;
        }
    }

    public boolean currentTabEquals(String name) {
        return getCurrentTab() != null && getCurrentTab().getName().equalsIgnoreCase(name);
    }

    public boolean currentTabStartWith(String name) {
        return getCurrentTab() != null && getCurrentTab().getName().startsWith(name.toLowerCase());
    }

}

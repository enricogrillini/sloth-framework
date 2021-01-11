package it.eg.sloth.form.tabsheet;

import it.eg.sloth.form.base.AbstractElements;
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
        if (getCurrentTab().isHidden()) {
            for (Tab tab : this) {
                if (!tab.isHidden()) {
                    setCurrentTabName(tab.getName());
                    break;
                }
            }
        }
    }

    public Tab getCurrentTab() {
        if (getElements() == null || getElements().isEmpty())
            return null;

        if (getCurrentTabName() == null)
            return getElements().get(0);

        if (getElement(getCurrentTabName()) != null)
            return getElement(getCurrentTabName());

        return getElements().get(0);
    }

}

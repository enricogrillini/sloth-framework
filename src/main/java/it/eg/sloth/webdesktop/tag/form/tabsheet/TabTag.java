package it.eg.sloth.webdesktop.tag.form.tabsheet;

import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;

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
 *
 * @author Enrico Grillini
 */
public class TabTag extends BaseElementTag<Tab> {

    private static final long serialVersionUID = 1L;

    protected TabSheet getTabSheet() {
        return (TabSheet) getForm().getParentElement(getName());
    }

    public int startTag() {
        TabSheet tabSheet = getTabSheet();
        if (tabSheet.getCurrentTab() == getElement()) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    protected void endTag() {
        // NOP
    }

}

package it.eg.sloth.webdesktop.tag.form.base;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.form.base.Elements;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import lombok.Getter;
import lombok.Setter;

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
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public abstract class BaseElementTag<E extends Element> extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    String name = "";

    /**
     * Restituisce l'elemento da visualizzare
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected E getElement() {
        return (E) getForm().getElement(getName());
    }

    /**
     * Restituisce l'elemento padre
     *
     * @return
     */
    protected <S extends Element> Elements<S> getParentElement() {
        return (Elements<S>) getForm().getParentElement(getName());
    }

}

package it.eg.sloth.form.fields.field;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.framework.common.casting.Casting;

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
public interface SimpleField extends Element, Cloneable {

    /**
     * Ritorna la descrizione
     *
     * @return
     */
    String getDescription();

    /**
     * Imposta la descrizione
     *
     * @param description
     */
    public void setDescription(String description);


    default String getHtmlDescription() {
        return Casting.getHtml(getDescription());
    }

    default String getJsDescription() {
        return Casting.getJs(getDescription());
    }


    /**
     * Ritorna il tooltip
     *
     * @return
     */
    public String getTooltip();

    /**
     * Imposta il tooltip
     *
     * @param tooltip
     */
    public void setTooltip(String tooltip);


    default String getHtmlTooltip() {
        return Casting.getHtml(getTooltip());
    }

    default String getJsTooltip() {
        return Casting.getJs(getTooltip());
    }

    public FieldType getFieldType();

    /**
     * Effettua il post della Web Request
     *
     * @param webRequest
     * @return
     */
    public void post(WebRequest webRequest);

    /**
     * Effettua il post della Web Request contenente valori escaped tipicamente
     * usati con un post JSON
     *
     * @param webRequest
     * @return
     */
    public void postEscaped(WebRequest webRequest, String encoding);

    public SimpleField newInstance() throws CloneNotSupportedException;

}

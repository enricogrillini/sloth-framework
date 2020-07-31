package it.eg.sloth.form.fields.field;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.FrameworkException;

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
public interface DecodedDataField<T extends Object> extends DataField<T> {

    <D extends DecodeValue<T>> DecodeMap<T, D> getDecodeMap();

    void setDecodeMap(DecodeMap<T, ? extends DecodeValue<T>> values);

    String getDecodedText() throws FrameworkException;

    default String escapeHtmlDecodedText() throws FrameworkException {
        return escapeHtmlDecodedText(true, true);
    }

    default String escapeHtmlDecodedText(boolean br, boolean nbsp) throws FrameworkException {
        if (BaseFunction.isNull(getDecodedText())) {
            return "";
        } else {
            return Casting.getHtml(getDecodedText(), br, nbsp);
        }
    }

    default String escapeJsDecodedText() throws FrameworkException {
        return Casting.getJs(getDecodedText());
    }
}

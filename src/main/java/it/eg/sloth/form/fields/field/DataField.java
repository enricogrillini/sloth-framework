package it.eg.sloth.form.fields.field;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;

import java.text.ParseException;

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
public interface DataField<T> extends SimpleField {

    /**
     * Ritorna l'alias
     *
     * @return
     */
    String getAlias();

    /**
     * Imposta l'alias
     *
     * @param alias
     */
    void setAlias(String alias);

    /**
     * Ritorna il data type
     *
     * @return
     */
    DataTypes getDataType();

    /**
     * Imposta il data type
     *
     * @param dataType
     */
    void setDataType(DataTypes dataType);

    /**
     * Ritorna il formato
     *
     * @return
     */
    String getFormat();

    /**
     * Imposta il formato
     *
     * @param format
     */
    void setFormat(String format);

    String getData();

    void setData(String data);

    default String escapeHtmlText() {
        return getDataType().escapeHtmlText(getData(), getLocale(), getFormat());
    }

    default String escapeJsText() {
        return getDataType().escapeJsText(getData(), getLocale(), getFormat());
    }

    default String escapeHtmlValue() {
        return Casting.getHtml(getData(), false, false);
    }

    default String escapeJsValue() {
        return Casting.getJs(getData());
    }

    /**
     * Imposta il testo del campo formattando il valore passato
     *
     * @param value
     */
    void setValue(T value) throws FrameworkException;

    /**
     * Imposta il valore prelevandolo dal data source passato
     *
     * @param dataSource
     */
    void copyFromDataSource(DataSource dataSource) throws FrameworkException;

    /**
     * Imposta il valore sul data source passato
     *
     * @param dataSource
     */
    void copyToDataSource(DataSource dataSource) throws FrameworkException;

    /**
     * Ritorna il testo del campo nel formato nativo
     *
     * @return
     * @throws ParseException
     */
    T getValue() throws FrameworkException;

    /**
     * @return
     */
    boolean isValid();

    /**
     * Verifica la validita del testo passato
     *
     * @return
     */
    Message check();

    /**
     * Effettua la validazione della request relativamente al campo
     *
     * @param messages
     * @return
     */
    boolean validate(MessageList messages) throws FrameworkException;

}

package it.eg.sloth.framework.common.message;

import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.casting.Validator;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
 * Gestisce la lista di errori applicativi
 *
 * @author Enrico Grillini
 */
@Data
public class MessageList {

    private List<Message> list;
    private boolean popup;

    /**
     * Costruttore
     */
    public MessageList() {
        list = new ArrayList<>();
        popup = true;
    }


    /**
     * Aggiunge un messaggio
     *
     * @param message
     */
    public void add(Message message) {
        list.add(message);
    }

    /**
     * Aggiunge un informazione base
     *
     * @param messaggio
     */
    public void addBaseInfo(String messaggio) {
        list.add(new Message(Level.INFO, null, messaggio, null));
    }

    /**
     * Aggiunge un informazione di successo
     *
     * @param messaggio
     */
    public void addBaseSuccess(String messaggio) {
        list.add(new Message(Level.SUCCESS, null, messaggio, null));
    }

    /**
     * Aggiunge un warning base
     *
     * @param messaggio
     */
    public void addBaseWarning(String messaggio) {
        list.add(new Message(Level.WARN, null, messaggio, null));
    }

    /**
     * Aggiunge un errore base
     *
     * @param messaggio
     */
    public void addBaseError(String messaggio) {
        list.add(new Message(Level.ERROR, null, messaggio, null));
    }

    /**
     * Aggiunge un errore base
     *
     * @param error
     */
    public void addBaseError(Exception error) {
        addBaseError("Errore: " + error.getMessage(), error);
    }

    /**
     * Aggiunge un errore base
     *
     * @param messaggio
     */
    public void addBaseError(String messaggio, Throwable throwable) {
        list.add(new Message(Level.ERROR, null, messaggio, ExceptionUtils.getStackTrace(throwable)));
    }


    /**
     * Verifica la correttezza formale di un campo e nel caso in cui non sia rispettata aggiunge un errore alla lista
     *
     * @param fieldName
     * @param fieldDescription
     * @param data
     * @param locale
     * @param dataTypes
     * @param format
     * @return
     */
    public MessageList addIfInNotValid(String fieldName, String fieldDescription, String data, Locale locale, DataTypes dataTypes, String format) {
        Message message = Validator.verifyIfIsValid(fieldName, fieldDescription, data, locale, dataTypes, format);
        if (message != null) {
            add(message);
        }

        return this;
    }

    /**
     * Verica se un campo è valorizzato in caso contrario aggiunge un errore alla lista
     *
     * @param fieldName
     * @param fieldDescription
     * @param data
     * @param locale
     * @return
     */
    public MessageList addIfIsEmpty(String fieldName, String fieldDescription, String data, Locale locale) {
        Message message = Validator.verifyIfIsNotEmpty(fieldName, fieldDescription, data, locale);
        if (message != null) {
            add(message);
        }

        return this;
    }

    /**
     * Verifica se sono presenti messaggi nella lista
     *
     * @return
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Ritorna il più alto livello di segnalazione
     *
     * @return
     */
    public Level getMaxSeverity() {
        Level level = Level.INFO;
        for (Message message : list) {
            if (message.getSeverity().hasHigerSeverity(level)) {
                level = message.getSeverity();
            }
        }

        return level;
    }


    public String getMessagesDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Message message : list) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(" | ");
            }
            stringBuilder.append(message.getDescription());
        }

        return stringBuilder.toString();
    }
}

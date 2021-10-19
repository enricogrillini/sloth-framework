package it.eg.sloth.db.decodemap;

import it.eg.sloth.db.decodemap.value.BaseDecodeValue;

import java.util.Collection;
import java.util.List;

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
 * <p>
 *
 * @author Enrico Grillini
 */
public interface DecodeMap<T, V extends DecodeValue<T>> extends Iterable<V> {

    /**
     * Aggiunge un valore
     *
     * @param code
     * @param description
     */
    default void put(T code, String description) {
        put(code, description, true);
    }

    /**
     * Aggiunge un valore
     *
     * @param code
     * @param description
     * @param valid
     */
    void put(T code, String description, boolean valid);

    /**
     * Ritorna un valore
     *
     * @param code
     * @return
     */
    V get(T code);

    /**
     * Ritorna il codice corrispondente ad una descrizione
     *
     * @param descrizione
     * @return
     */
    T encode(String descrizione);

    /**
     * Ritorna la descrizione corrispondente al codice passato
     *
     * @param code
     * @return
     */
    String decode(T code);

    /**
     * Verifica se il codice passato à contenuto nella mappa
     *
     * @param code
     * @return
     */
    boolean contains(T code);

    /**
     * Ritorna il primo valore della Mappa
     *
     * @return
     */
    T getFirst();

    /**
     * Verifica se la decodifica à popolata
     *
     * @return
     */
    boolean isEmpty();

    /**
     * Ritorna la dimesione della mappa
     *
     * @return
     */
    int size();

    /**
     * Svuota la DecoodeMap
     */
    void clear();

    /**
     * Estrae tutti i record
     *
     * @return
     */
    Collection<V> values();

    /**
     * Estrae i soli record validi
     *
     * @return
     */
    Collection<V> valid();

    /**
     * Effettua una ricerca nella Map
     *
     * @param code
     * @return
     */
    List<V> performSearch(T code);

    /**
     * Effettua una ricerca nella Map
     *
     * @param query
     * @param searchType
     * @return
     */
    List<V> performSearch(String query, SearchType searchType, Integer sizeLimit);

}

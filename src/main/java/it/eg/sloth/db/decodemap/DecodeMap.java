package it.eg.sloth.db.decodemap;

import java.util.List;

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
public interface DecodeMap<T, V extends DecodeValue<T>> extends Iterable<V> {

  /**
   * Ritorna il codice corrispondente ad una descrizione
   * 
   * @param descrizione
   * @return
   */
  public T encode(String descrizione);

  /**
   * Ritorna la descrizione corrispondente al codice passato
   * 
   * @param code
   * @return
   */
  public String decode(T code);

  /**
   * Verifica se il codice passato à contenuto nella mappa
   * 
   * @param code
   * @return
   */
  public boolean contains(T code);

  /**
   * Ritorna il primo valore della Mappa
   * 
   * @return
   */
  public T getFirst();

  /**
   * Verifica se la decodifica à popolata
   * 
   * @return
   */
  public boolean isEmpty();

  /**
   * Ritorna la dimesione della mappa
   * 
   * @return
   */
  public int size();

  /**
   * Svuota la DecoodeMap
   */
  public void clear();

  /**
   * Estrae i soli record validi
   * 
   * @return
   */
  public List<V> valid();

  /**
   * Effettua una ricerca nella Map
   * 
   * @param code
   * @return
   */
  public List<V> performSearch(T code);

  /**
   * Effettua una ricerca nella Map
   * 
   * @param description
   * @param searchType
   * @return
   */
  public List<V> performSearch(String description, MapSearchType searchType, Integer sizeLimit);

}

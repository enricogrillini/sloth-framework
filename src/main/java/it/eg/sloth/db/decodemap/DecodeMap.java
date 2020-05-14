package it.eg.sloth.db.decodemap;

import java.util.List;

public interface DecodeMap<T extends Object, V extends DecodeValue<T>> extends Iterable<V> {

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
   * @param code
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

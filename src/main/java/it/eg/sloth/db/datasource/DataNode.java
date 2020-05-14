package it.eg.sloth.db.datasource;


import java.sql.SQLException;
import java.util.List;

public interface DataNode extends DataSource, Iterable<DataNode> {
  
  public boolean isOpen();
  
  public void collapse ();
  
  public void expand ();
  
  /**
   * Aggiunge un figlio
   * @param node
   */
  public void addChild (DataNode node);
  
  /**
   * Ritorna la lista dei figli
   * @return
   */
  public List<DataNode> getChilds();
  
  /**
   * Verifica se il nodo ha figli
   * @return
   */
  public boolean hasChilds();
  
  
  /**
   * Ritorna l'n-esimo nodo aperto
   * @param nodeIndex
   * @return
   */
  public DataNode getOpenNode (int nodeIndex);
  
  
  public void loadChildFromTableInterface (DataTable<?> table, String levelName) throws SQLException;
  
}

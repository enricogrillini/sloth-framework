package it.eg.sloth.webdesktop.controller.common.editable;


/**
 * 
 * Gestisce l'interfaccia completa per un'operazione CRUD
 * 
 * @author Enrico Grillini
 * 
 */
public interface FullEditingInterface extends BaseEditingInterface {

  public boolean execInsert() throws Exception;

  public boolean execDelete() throws Exception;

  public void onInsert() throws Exception;

  public void onDelete() throws Exception;

  
}

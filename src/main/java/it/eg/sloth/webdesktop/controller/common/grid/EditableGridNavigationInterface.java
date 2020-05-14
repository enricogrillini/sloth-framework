package it.eg.sloth.webdesktop.controller.common.grid;

/**
 * 
 * Gestisce l'interfaccia per la navigazione su un master detail
 * 
 * @author Enrico Grillini
 * 
 */
public interface EditableGridNavigationInterface extends BaseGridNavigationInterface {

  public boolean execPreMove() throws Exception;

  public void execPostMove() throws Exception;

}

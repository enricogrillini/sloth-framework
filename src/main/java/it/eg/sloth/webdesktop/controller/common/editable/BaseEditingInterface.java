package it.eg.sloth.webdesktop.controller.common.editable;

import it.eg.sloth.webdesktop.controller.common.SimplePageInterface;

/**
 * 
 * Gestisce l'interfaccia di base per la gestione dell'editing (modifica e
 * salvataggio)
 * 
 * @author Enrico Grillini
 * 
 */
public interface BaseEditingInterface extends SimplePageInterface {

  public boolean execPostDetail(boolean validate) throws Exception;

  public boolean execUpdate() throws Exception;

  public boolean execCommit() throws Exception;

  public boolean execRollback() throws Exception;

  public void onUpdate() throws Exception;

  public void onCommit() throws Exception;

  public void onRollback() throws Exception;
}

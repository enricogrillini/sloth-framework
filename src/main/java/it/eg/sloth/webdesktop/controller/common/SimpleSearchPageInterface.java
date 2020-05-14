package it.eg.sloth.webdesktop.controller.common;

/**
 * 
 * Gestisce l'interfaccia di base per una pagina
 * 
 * @author Enrico Grillini
 * 
 */
public interface SimpleSearchPageInterface extends SimplePageInterface {

  public void execLoad() throws Exception;

  public void execReset() throws Exception;

  public void onLoad() throws Exception;

  public void onReset() throws Exception;
  
}

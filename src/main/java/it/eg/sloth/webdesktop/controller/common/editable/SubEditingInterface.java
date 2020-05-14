package it.eg.sloth.webdesktop.controller.common.editable;

import it.eg.sloth.form.grid.Grid;

/**
 * 
 * Gestisce l'interfaccia per la gestione dell'editing sui sub elenchi
 * 
 * @author Enrico Grillini
 * 
 */
public interface SubEditingInterface extends BaseEditingInterface {

  public boolean execPostSubDetail(Grid<?> grid, boolean validate) throws Exception;

  public boolean execSubInsert(Grid<?> grid) throws Exception;

  public boolean execSubDelete(Grid<?> grid) throws Exception;

  public void onSubInsert(Grid<?> grid) throws Exception;

  public void onSubDelete(Grid<?> grid) throws Exception;

}

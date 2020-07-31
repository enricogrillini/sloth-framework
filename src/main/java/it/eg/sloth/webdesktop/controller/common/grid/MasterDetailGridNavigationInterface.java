package it.eg.sloth.webdesktop.controller.common.grid;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;

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
 * Gestisce l'interfaccia per la navigazione su un master detail
 *
 * @author Enrico Grillini
 */
public interface MasterDetailGridNavigationInterface extends BaseGridNavigationInterface {

  public void execLoadDetail() throws Exception;
  
  public void execLoadSubDetail(Grid<?> grid) throws Exception;

  public void onElenco() throws Exception;

  public void execSelectTab(TabSheet tabSheet, Tab tab) throws Exception;

  public void onSelectTab(String tabSheetName, String tabName) throws Exception;

  public void onSubGoToRecord(Grid<?> grid, int record) throws Exception;

  public void onSubFirstRow(Grid<?> grid) throws Exception;

  public void onSubPrevPage(Grid<?> grid) throws Exception;

  public void onSubPrev(Grid<?> grid) throws Exception;

  public void onSubNext(Grid<?> grid) throws Exception;

  public void onSubNextPage(Grid<?> grid) throws Exception;

  public void onSubLastRow(Grid<?> grid) throws Exception;
}

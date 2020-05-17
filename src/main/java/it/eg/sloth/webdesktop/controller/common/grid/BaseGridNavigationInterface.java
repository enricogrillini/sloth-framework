package it.eg.sloth.webdesktop.controller.common.grid;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.controller.common.SimpleSearchPageInterface;

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
 * Gestisce l'interfaccia di base per la navigazione sulle griglie
 *
 * @author Enrico Grillini
 */
public interface BaseGridNavigationInterface extends SimpleSearchPageInterface {

  public void onGoToRecord(int record) throws Exception;

  public void onFirstRow() throws Exception;

  public void onPrevPage() throws Exception;

  public void onPrev() throws Exception;

  public void onNext() throws Exception;

  public void onNextPage() throws Exception;

  public void onLastRow() throws Exception;

  public void onSort(Grid<?> grid, String fieldName, int sortType) throws Exception;

  public void onExcel(Grid<?> grid) throws Exception;
}

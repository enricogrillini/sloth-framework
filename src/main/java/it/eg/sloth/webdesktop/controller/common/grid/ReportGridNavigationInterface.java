package it.eg.sloth.webdesktop.controller.common.grid;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.controller.common.SimpleSearchPageInterface;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Gestisce l'interfaccia per la navigazione su una pagina di report
 *
 * @author Enrico Grillini
 */
public interface ReportGridNavigationInterface<F extends Form> extends SimpleSearchPageInterface<F> {

    default void onFirstRow(Grid<?> grid) {
        grid.getDataSource().first();
    }

    default void onPrevPage(Grid<?> grid) {
        grid.getDataSource().prevPage();
    }

    default void onNextPage(Grid<?> grid) {
        grid.getDataSource().nextPage();
    }

    default void onLastRow(Grid<?> grid) {
        grid.getDataSource().last();
    }

}

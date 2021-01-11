package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

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
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public abstract class AbstractGridToolBarTag<E extends Grid<?>> extends AbstractToolBarTag {

    private static final long serialVersionUID = 1L;

    private String name = "";

    /**
     * Restituisce l'elemento da visualizzare
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected E getElement() {
        return (E) getForm().getElement(getName());
    }

    /**
     * Navigazione di base: primo record
     */
    protected void elencoButton() throws IOException {
        writeln(ToolbarWriter.elencoButton(getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Navigazione di base: estrazione excel
     */
    protected void excelButton() throws IOException {
        writeln(ToolbarWriter.excelButton(getName(), getWebDesktopDto().getLastController(), getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Navigazione di base: primo record
     */
    protected void firstRowButton(boolean disableIfChanging) throws IOException {
        writeln(ToolbarWriter.firstRowButton(getElement(), disableIfChanging && getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Navigazione di base: pagina precedente
     */
    protected void prevPageButton(boolean disableIfChanging) throws IOException {
        writeln(ToolbarWriter.prevPageButton(getElement(), disableIfChanging && getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Navigazione di base: record precedente
     */
    protected void prevButton(boolean disableIfChanging) throws IOException {
        writeln(ToolbarWriter.prevButton(getElement(), disableIfChanging && getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Navigazione di base: record successivo
     *
     * @param disableIfChanging
     */
    protected void nextButton(boolean disableIfChanging) throws IOException {
        writeln(ToolbarWriter.nextButton(getElement(), disableIfChanging && getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Navigazione di base: pagina sucessiva
     *
     * @param disableIfChanging
     */
    protected void nextPageButton(boolean disableIfChanging) throws IOException {
        writeln(ToolbarWriter.nextPageButton(getElement(), disableIfChanging && getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Navigazione di base: ultimo record
     *
     * @param disableIfChanging
     */
    protected void lastRowButton(boolean disableIfChanging) throws IOException {
        writeln(ToolbarWriter.lastRowButton(getElement(), disableIfChanging && getForm().getPageInfo().getPageStatus().isChanging()));
    }

    /**
     * Scrive il pulsante Insert
     */
    protected void insertButton() throws IOException {
        writeln(ToolbarWriter.insertButton(getElement()));
    }

    /**
     * Scrive il pulsante Delete
     */
    protected void deleteButton() throws IOException {
        writeln(ToolbarWriter.deleteButton(getElement()));
    }

    /**
     * Scrive il pulsante Update
     */
    @Override
    protected void updateButton() throws IOException {
        writeln(ToolbarWriter.updateButton(getElement()));
    }

}

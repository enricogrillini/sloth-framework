package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Fornisce gli elementi base per il disegno si una ToolBar
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

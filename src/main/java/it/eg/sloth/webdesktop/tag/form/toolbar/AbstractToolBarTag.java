package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;

import java.io.IOException;

/**
 * Fornisce gli elementi base per il disegno si una ToolBar
 *
 * @author Enrico Grillini
 */
public abstract class AbstractToolBarTag extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    /**
     * Apre la sezione di sinistra
     */
    protected void openLeft() throws IOException {
        writeln(" <div class=\"float-left form-inline\">");
    }

    /**
     * Chiude la sezione di sinistra
     */
    protected void closeLeft() throws IOException {
        writeln(" </div>");
    }

    /**
     * Apre la sezione di destra
     */
    protected void openRight() throws IOException {
        writeln(" <div class=\"float-right\">");
    }

    /**
     * Chiude la sezione di destra
     */
    protected void closeRight() throws IOException {
        writeln(" </div>");
    }

    /**
     * Scrive il pulsante Update
     */
    protected void updateButton() throws IOException {
        writeln(ToolbarWriter.updateButton("", false, false));
    }

    /**
     * Scrive il pulsante Commit
     */
    protected void commitButton() throws IOException {
        writeln(ToolbarWriter.commitButton());
    }

    /**
     * Scrive il pulsante Rollback
     */
    protected void rollbackButton() throws IOException {
        writeln(ToolbarWriter.rollbackButton());
    }
}

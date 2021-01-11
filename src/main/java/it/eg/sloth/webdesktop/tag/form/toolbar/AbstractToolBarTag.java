package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;

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

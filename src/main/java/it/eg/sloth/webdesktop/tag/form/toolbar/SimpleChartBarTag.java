package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

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
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class SimpleChartBarTag extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    private String name = "";

    protected <D extends DataTable<?>> SimpleChart<D> getElement() {
        return (SimpleChart) getForm().getElement(getName());
    }

    @Override
    public int startTag() throws IOException {
        write(ToolbarWriter.simpleChart(getElement(), getWebDesktopDto().getLastController(), getForm().getPageInfo().getPageStatus()));

        return SKIP_BODY;
    }

    @Override
    protected void endTag() throws IOException {
        // NOP
    }

}

package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
public abstract class AbstractGridTag<T extends Grid<?>> extends BaseElementTag<T> {

    static final long serialVersionUID = 1L;

    String detailName;

    public boolean hasDetail() {
        return !BaseFunction.isBlank(getDetailName());
    }

    public <D extends DataSource> Fields<D> getDetailFields() {
        if (hasDetail()) {
            return (Fields) getForm().getElement(getDetailName());
        } else {
            return null;
        }
    }

}

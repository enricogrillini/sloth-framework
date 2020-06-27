package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.math.BigDecimal;

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
public abstract class AbstractGridTag<T extends Grid<?>> extends BaseElementTag<T> {

    static final long serialVersionUID = 1L;

    String detailName;

    public boolean hasDetail() {
        return getDetailName() != null && !getDetailName().equals("");
    }

    protected void writeTotal() throws  FrameworkException, IOException {

        if (getElement().hasTotalizer()) {
            writeln(" <tr>");

            for (SimpleField field : getElement().getElements()) {
                if (field instanceof TextTotalizer || field instanceof InputTotalizer) {
                    TextField<BigDecimal> textField = (TextField<BigDecimal>) field.newInstance();

                    BigDecimal totale = new BigDecimal(0);
                    for (DataRow dataRow : getElement().getDataSource()) {
                        totale = totale.add(BaseFunction.nvl(dataRow.getBigDecimal(textField.getAlias()), new BigDecimal(0)));
                    }

                    textField.setValue(totale);

                    writeln("  <td class=\"frSum\" style=\"text-align:right\">" + textField.escapeHtmlText() + "</td>");
                } else {
                    writeln("  <td class=\"frSum\">&nbsp;</td>");
                }
            }
            writeln(" </tr>");
        }
    }
}

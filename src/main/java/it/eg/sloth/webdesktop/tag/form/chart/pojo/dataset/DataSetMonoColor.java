package it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset;

import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.html.HtmlColor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class DataSetMonoColor extends DataSet<BigDecimal, String> {

    public DataSetMonoColor() {
        super();
    }

    public void setColors(String color, double opacity) throws FrameworkException {
        setBorderColor(color);
        setBackgroundColor(HtmlColor.rgbaFromHex(color, opacity));

        setPointBackgroundColor(color);
        setPointBorderColor(color);
    }

}

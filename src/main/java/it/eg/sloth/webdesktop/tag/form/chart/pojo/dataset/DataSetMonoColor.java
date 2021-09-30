package it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset;

import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.html.HtmlColor;
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
public class DataSetMonoColor extends DataSet {

    int borderWidth;
    String borderColor;
    String backgroundColor;
    String pointBackgroundColor;
    String pointBorderColor;

    public DataSetMonoColor() {
        super();
        borderWidth = 2;
    }

    public void setColors(String color) throws FrameworkException {
        this.borderColor = color;
        this.backgroundColor = HtmlColor.rgbaFromHex(color, 0.05);

        this.pointBackgroundColor = color;
        this.pointBorderColor = color;
    }

}

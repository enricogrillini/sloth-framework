package it.eg.sloth.webdesktop.tag.form.chart;

import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.chart.writer.ChartWriter;

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
public class ChartCanvasTag extends BaseElementTag<SimpleChart<?>> {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws IOException {

    writeln();
    writeln(ChartWriter.writeCanvas(getElement()));

    return SKIP_BODY;
  }

  @Override
  protected void endTag() {
    // NOP
  }

}

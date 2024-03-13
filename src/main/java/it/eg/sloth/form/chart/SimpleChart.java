package it.eg.sloth.form.chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.base.AbstractElements;
import it.eg.sloth.form.chart.element.AbstractChartField;
import it.eg.sloth.form.chart.element.Labels;
import it.eg.sloth.form.chart.element.Series;
import it.eg.sloth.jaxb.form.ChartType;
import it.eg.sloth.jaxb.form.LegendPosition;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class SimpleChart<D extends DataTable<?>> extends AbstractElements<AbstractChartField<?>> {

    D dataTable;
    ChartType chartType;
    String title;
    LegendPosition legendPosition;
    List<String> palette;
    Boolean filled;
    Boolean stacked;

    Integer limitTo;
    String limitDescription;

    public SimpleChart(String name, ChartType chartType, String title, LegendPosition legendPosition) {
        super(name);

        this.chartType = chartType;
        this.title = title;
        this.legendPosition = legendPosition;
        this.dataTable = null;
        this.filled = null;
        this.stacked = null;

        this.limitTo = null;
        this.limitDescription = null;
    }

    public boolean isFilled() {
        return filled != null && filled;
    }

    public boolean isStacked() {
        return stacked != null && stacked;
    }

    /**
     * Ritorna il Ticks
     *
     * @return
     */
    public <T> Labels<T> getLabels() {
        for (AbstractChartField<?> chartField : this) {
            if (chartField instanceof Labels<?>) {
                return (Labels) chartField;
            }
        }

        return null;
    }

    /**
     * Ritorna la lista di Series
     *
     * @return
     */
    public List<Series> getSeriesList() {
        List<Series> list = new ArrayList<>();

        for (AbstractChartField<?> chartField : this) {
            if (chartField instanceof Series) {
                list.add((Series) chartField);
            }
        }

        return list;
    }

    public void setPalette(String... color) {
        palette = Arrays.asList(color);
    }

}

package it.eg.sloth.form.chart;

import java.util.ArrayList;
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
 * @author Enrico Grillini
 */
@Getter
@Setter
public class SimpleChart<D extends DataTable<?>> extends AbstractElements<AbstractChartField<?>> {

    D dataTable;
    ChartType chartType;
    String title;
    LegendPosition legendPosition;

    public SimpleChart(String name, ChartType chartType, String title, LegendPosition legendPosition) {
        super(name);

        this.chartType = chartType;
        this.title = title;
        this.legendPosition = legendPosition;
        this.dataTable = null;
    }

    /**
     * Ritorna il Ticks
     *
     * @return
     */
    public Labels getLabels() {
        for (AbstractChartField<?> chartField : this) {
            if (chartField instanceof Labels<?>) {
                return (Labels<?>) chartField;
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

}

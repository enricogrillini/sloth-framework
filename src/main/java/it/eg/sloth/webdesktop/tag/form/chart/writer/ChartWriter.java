package it.eg.sloth.webdesktop.tag.form.chart.writer;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.form.chart.element.Labels;
import it.eg.sloth.form.chart.element.Series;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.utility.html.HtmlColor;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJs;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJsData;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJsLegend;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJsTitle;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset.DataSetMonoColor;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public class ChartWriter extends AbstractHtmlWriter {

    public static final String writeCanvas(SimpleChart<?> simpleChart) {
        return new StringBuilder()
                .append("<!-- ChartCanvas -->\n")
                .append("<div class=\"chart-area\">\n")

                .append(" <canvas")
                .append(getAttribute("id", simpleChart.getName()))
                .append("></canvas>\n")

                .append("</div>")
                .toString();
    }

    public static final String writeScript(SimpleChart<?> simpleChart) throws CloneNotSupportedException, BusinessException, ParseException {

        ChartJs chartJs = populateChart(simpleChart);


        return new StringBuilder()
                .append("<!-- ChartScript -->\n")
                .append("<script>\n")
                .append(" var chartData = " + chartJs.toString() + ";\n")
                //.append(" var chartData = " + chartJs.getData().toString() + ";\n")
                .append(" chart(\"" + simpleChart.getName() + "\", chartData);\n")
                .append("</script>\n")
                .toString();
    }

    public static final ChartJs populateChart(SimpleChart<?> simpleChart) throws CloneNotSupportedException, BusinessException, ParseException {
        ChartJs chartJs = new ChartJs();
        chartJs.setType(simpleChart.getChartType().toString().toLowerCase());
        chartJs.setData(populateChartData(simpleChart));
        chartJs.getOptions().setTitle(new ChartJsTitle(simpleChart.getTitle()));
        chartJs.getOptions().setLegend(new ChartJsLegend(simpleChart.getLegendPosition()));

        // NumerFormat
        if (!simpleChart.getSeriesList().isEmpty()) {
            Series series = simpleChart.getSeriesList().get(0);
            chartJs.getAdditionalInfo().setNumerFormat(series.getDataType().getNumerFormat());
        }

        return chartJs;
    }

    public static final ChartJsData populateChartData(SimpleChart<?> simpleChart) throws CloneNotSupportedException, BusinessException, ParseException {
        ChartJsData chartData = new ChartJsData();


        if (simpleChart.getDataTable() != null) {
            // Labels
            Labels<?> labels = (Labels<?>) simpleChart.getLabels().clone();
            for (DataRow row : simpleChart.getDataTable()) {
                labels.copyFromDataSource(row);
                chartData.getLabels().add(labels.escapeJsText());
            }

            // Mono Colors
            switch (simpleChart.getChartType()) {
                case LINE:
                case BAR:
                    dataSetMonoColor(simpleChart, chartData);
                    break;

                case PIE:
                    dataSetMultiColor(simpleChart, chartData);

            }


        }

        return chartData;
    }

    private static final void dataSetMonoColor(SimpleChart<?> simpleChart, ChartJsData chartData) throws CloneNotSupportedException, BusinessException, ParseException {
        // Datasets
        List<Series> seriesList = simpleChart.getSeriesList();
        List<String> palette = HtmlColor.getColorPalette(seriesList.size());

        int i = 0;
        for (Series series : seriesList) {
            Series seriesClone = (Series) series.clone();

            DataSetMonoColor dataSet = new DataSetMonoColor();
            dataSet.setLabel(series.getDescription());
            for (DataRow row : simpleChart.getDataTable()) {
                seriesClone.copyFromDataSource(row);

                if (seriesClone.getValue() instanceof BigDecimal) {
                    dataSet.getData().add(seriesClone.getValue());
                } else {
                    dataSet.getData().add(null);
                }
            }

            dataSet.setColors(palette.get(i), HtmlColor.rgbaFromHex(palette.get(i), 0.05), palette.get(i));
            chartData.getDatasets().add(dataSet);

            i++;
        }
    }

    private static final void dataSetMultiColor(SimpleChart simpleChart, ChartJsData chartData) throws CloneNotSupportedException {
// TODO
    }

}

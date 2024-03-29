package it.eg.sloth.webdesktop.tag.form.chart.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.form.chart.element.Labels;
import it.eg.sloth.form.chart.element.Series;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.html.HtmlColor;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJs;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJsData;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJsLegend;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJsTitle;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset.DataSetMonoColor;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset.DataSetMultiColor;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset.DataSetWaterfall;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 *
 * @author Enrico Grillini
 */
public class ChartWriter extends HtmlWriter {

    private static final String CANVAS = ResourceUtil.normalizedResourceAsString("snippet/chart/canvas.html");

    private static final String CANVAS_CLASS = "chart-area";

    public static final String writeCanvas(SimpleChart<?> simpleChart, String className) {
        return MessageFormat.format(CANVAS, simpleChart.getName(), BaseFunction.isBlank(className) ? CANVAS_CLASS : CANVAS_CLASS + " " + className);
    }

    public static final String openScript(SimpleChart<?> simpleChart) throws FrameworkException, JsonProcessingException {
        ChartJs chartJs = populateChart(simpleChart);

        ObjectMapper objectMapper = new ObjectMapper();
        String chartJsHtml = objectMapper.writeValueAsString(chartJs);

        return new StringBuilder()
                .append("<!-- ChartScript -->\n")
                .append("<script>\n")
                .append(" var chartData = " + chartJsHtml + ";\n")
                .append(" var newChart = chart(\"" + simpleChart.getName() + "\", chartData);\n")
                .toString();
    }


    public static final String closeScript() throws FrameworkException, JsonProcessingException {
        return "</script>\n";
    }

    public static final ChartJs populateChart(SimpleChart<?> simpleChart) throws FrameworkException {
        ChartJs chartJs = new ChartJs();
        chartJs.setType(simpleChart.getChartType().value());
        chartJs.setData(populateChartData(simpleChart));
        chartJs.getOptions().setTitle(new ChartJsTitle(simpleChart.getTitle()));
        chartJs.getOptions().setLegend(new ChartJsLegend(simpleChart.getLegendPosition()));

        chartJs.getAdditionalInfo().setStacked(simpleChart.isStacked());

        // NumerFormat
        if (!simpleChart.getSeriesList().isEmpty()) {
            Series series = simpleChart.getSeriesList().get(0);
            chartJs.getAdditionalInfo().setNumerFormat(series.getDataType().getNumerFormat());
        }

        return chartJs;
    }

    public static final ChartJsData populateChartData(SimpleChart<?> simpleChart) throws FrameworkException {
        ChartJsData chartData = new ChartJsData();

        if (simpleChart.getDataTable() != null) {
            // Labels
            Labels<?> labels = simpleChart.getLabels().newInstance();
            int j = 0;
            for (DataRow row : simpleChart.getDataTable()) {
                if (simpleChart.getLimitTo() != null && j >= simpleChart.getLimitTo()) {
                    if (j == simpleChart.getLimitTo()) {
                        chartData.getLabels().add(simpleChart.getLimitDescription());
                    }
                } else {
                    labels.copyFromDataSource(row);
                    chartData.getLabels().add(labels.getText());
                }

                j++;
            }

            // Mono Colors
            switch (simpleChart.getChartType()) {
                case LINE:
                case BAR:
                case RADAR:
                    dataSetMonoColor(simpleChart, chartData);
                    break;

                case PIE:
                case POLAR_AREA:
                case DOUGHNUT:
                    dataSetMultiColor(simpleChart, chartData);
                    break;

                case WATERFALL:
                    dataSetWatterFall(simpleChart, chartData);
                    break;

                default:
                    dataSetMonoColor(simpleChart, chartData);
            }
        }

        return chartData;
    }

    private static final void dataSetMonoColor(SimpleChart<?> simpleChart, ChartJsData chartData) throws FrameworkException {
        // Datasets
        List<Series> seriesList = simpleChart.getSeriesList();

        List<String> palette = simpleChart.getPalette();
        if (palette == null) {
            palette = HtmlColor.getColorPalette(seriesList.size());
        }

        int i = 0;
        for (Series series : seriesList) {
            Series seriesClone = series.newInstance();

            DataSetMonoColor dataSet = new DataSetMonoColor();
            dataSet.setLabel(series.getDescription());

            DataTable<?> dataTable = simpleChart.getDataTable();

            int j = 0;
            for (DataRow row : dataTable) {
                seriesClone.copyFromDataSource(row);

                if (simpleChart.getLimitTo() != null && j >= simpleChart.getLimitTo()) {
                    if (j == simpleChart.getLimitTo()) {
                        dataSet.getData().add(null);
                    }

                    if (seriesClone.getValue() instanceof BigDecimal) {
                        BigDecimal curValue = dataSet.getData().get(simpleChart.getLimitTo());

                        dataSet.getData().set(simpleChart.getLimitTo(), BigDecimalUtil.sum(curValue, seriesClone.getValue()));
                    }

                } else {
                    if (seriesClone.getValue() instanceof BigDecimal) {
                        dataSet.getData().add(seriesClone.getValue());
                    } else {
                        dataSet.getData().add(null);
                    }
                }

                j++;
            }

            if (simpleChart.isFilled()) {
                dataSet.setColors(palette.get(i), 1);
            } else {
                dataSet.setColors(palette.get(i), 0.05);
            }
            chartData.getDatasets().add(dataSet);

            i++;
        }
    }

    private static final void dataSetMultiColor(SimpleChart<?> simpleChart, ChartJsData chartData) throws FrameworkException {
        List<Series> seriesList = simpleChart.getSeriesList();
        List<String> palette = simpleChart.getPalette();
        if (palette == null) {
            palette = HtmlColor.getColorPalette(simpleChart.getDataTable().size());
        }

        for (Series series : seriesList) {
            Series seriesClone = series.newInstance();

            DataSetMultiColor dataSet = new DataSetMultiColor();
            dataSet.setLabel(series.getDescription());

            int i = 0;
            DataTable<?> dataTable = simpleChart.getDataTable();
            for (DataRow row : dataTable) {
                seriesClone.copyFromDataSource(row);

                if (seriesClone.getValue() instanceof BigDecimal) {
                    dataSet.getData().add(seriesClone.getValue());
                } else {
                    dataSet.getData().add(null);
                }

                dataSet.addColors(palette.get(i));
                i++;
            }

            chartData.getDatasets().add(dataSet);

        }
    }

    private static final void dataSetWatterFall(SimpleChart<?> simpleChart, ChartJsData chartData) throws FrameworkException {
        List<Series> seriesList = simpleChart.getSeriesList();
        List<String> palette = simpleChart.getPalette();
        if (palette == null) {
            palette = HtmlColor.getColorPalette(simpleChart.getDataTable().size());
        }

        Series seriesFrom = seriesList.get(0).newInstance();
        Series seriesTo = seriesList.get(1).newInstance();

        DataSetWaterfall dataSet = new DataSetWaterfall();
        dataSet.setLabel(seriesFrom.getDescription());

        int i = 0;
        DataTable<?> dataTable = simpleChart.getDataTable();
        for (DataRow row : dataTable) {
            seriesFrom.copyFromDataSource(row);
            seriesTo.copyFromDataSource(row);

            BigDecimal valueFrom = BigDecimal.ZERO;
            if (seriesFrom.getValue() instanceof BigDecimal) {
                valueFrom = seriesFrom.getValue();
            }

            BigDecimal valueTo = BigDecimal.ZERO;
            if (seriesTo.getValue() instanceof BigDecimal) {
                valueTo = seriesTo.getValue();
            }

            List<BigDecimal> values = new ArrayList<>(Arrays.asList(valueFrom, valueTo));
            dataSet.getData().add(values);

            dataSet.addColors(palette.get(i % palette.size()));

            i++;
        }

        chartData.getDatasets().add(dataSet);
    }


}

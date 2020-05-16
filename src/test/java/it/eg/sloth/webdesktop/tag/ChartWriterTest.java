package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.form.chart.element.Labels;
import it.eg.sloth.form.chart.element.Series;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.jaxb.form.ChartType;
import it.eg.sloth.jaxb.form.LegendPosition;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.ChartJs;
import it.eg.sloth.webdesktop.tag.form.chart.writer.ChartWriter;

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
 *
 */
public class ChartWriterTest {

    static final String CONTENT_TEMPLATE = "<!-- ChartCanvas -->\n" +
            "<div class=\"chart-area\">\n" +
            " <canvas id=\"{0}\"></canvas>\n" +
            "</div>";

    static final String SCRIPT_TEMPLATE = "<!-- ChartScript -->\n" +
            "<script>\n" +
            " var chartData = {\"type\":\"line\",\"data\":{\"labels\":[\"01-2020\",\"02-2020\",\"03-2020\"],\"datasets\":[{\"label\":\"Excecutions\",\"data\":[25,15,35],\"borderWidth\":2,\"borderColor\":\"#4e73df\",\"backgroundColor\":\"rgba(78, 115, 223, 0.05)\",\"pointBackgroundColor\":\"#4e73df\",\"pointBorderColor\":\"#4e73df\"}]},\"options\":{\"title\":{\"display\":true,\"text\":\"Prova\"},\"legend\":{\"display\":true,\"position\":\"top\"}},\"additionalInfo\":{\"numerFormat\":{\"decimals\":0,\"decimalSeparator\":\",\",\"thousandsSeparator\":\".\",\"suffix\":\"\"}}};\n" +
            " chart(\"prova\", chartData);\n" +
            "</script>\n";

    SimpleChart<DataTable<?>> simpleChart;

    @Before
    public void init() throws BusinessException {
        simpleChart = new SimpleChart<>("Prova", ChartType.LINE, "Prova", LegendPosition.TOP);

        simpleChart.addChild(new Labels<Timestamp>("Ora", null, "Ora", null, DataTypes.MONTH, null, null, 0));
        simpleChart.addChild(new Series("Excecutions", null, "Excecutions", null, DataTypes.INTEGER, null, null));

        DataTable<?> table = new Table();
        DataRow row = table.add();
        row.setTimestamp("Ora", TimeStampUtil.parseTimestamp("01/01/2020"));
        row.setBigDecimal("Excecutions", BigDecimal.valueOf(25));

        row = table.add();
        row.setTimestamp("Ora", TimeStampUtil.parseTimestamp("01/02/2020"));
        row.setBigDecimal("Excecutions", BigDecimal.valueOf(15));

        row = table.add();
        row.setTimestamp("Ora", TimeStampUtil.parseTimestamp("01/03/2020"));
        row.setBigDecimal("Excecutions", BigDecimal.valueOf(35));

        simpleChart.setDataTable(table);
    }

    @Test
    public void writeCanvasTest() {
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "prova"), ChartWriter.writeCanvas(simpleChart));
    }

    @Test
    public void writeScriptTest() throws CloneNotSupportedException, ParseException, BusinessException {
        assertEquals(SCRIPT_TEMPLATE, ChartWriter.writeScript(simpleChart));
    }

    @Test
    public void populateChartDataTest() throws CloneNotSupportedException, ParseException, BusinessException {
        ChartJs chartJs = ChartWriter.populateChart(simpleChart);

        assertEquals(3, chartJs.getData().getLabels().size());
        assertEquals(1, chartJs.getData().getDatasets().size());
        assertEquals(3, chartJs.getData().getDatasets().get(0).getData().size());
        assertNotNull(chartJs.getOptions());
        assertTrue(chartJs.getOptions().getTitle().isDisplay());
        assertTrue(chartJs.getOptions().getLegend().isDisplay());

        assertEquals(0, chartJs.getAdditionalInfo().getNumerFormat().getDecimals());
        assertEquals(",", chartJs.getAdditionalInfo().getNumerFormat().getDecimalSeparator());
        assertEquals(".", chartJs.getAdditionalInfo().getNumerFormat().getThousandsSeparator());
        assertEquals("", chartJs.getAdditionalInfo().getNumerFormat().getSuffix());
    }

}

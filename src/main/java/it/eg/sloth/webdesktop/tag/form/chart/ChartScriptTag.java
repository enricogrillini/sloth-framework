package it.eg.sloth.webdesktop.tag.form.chart;

import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.chart.writer.ChartWriter;

/**
 * Tag per il disegno della canvas dei grafici
 *
 * @author Enrico Grillini
 */
public class ChartScriptTag extends BaseElementTag<SimpleChart<?>> {

    private static final long serialVersionUID = 1L;

    @Override
    protected int startTag() throws Throwable {

        writeln();
        writeln(ChartWriter.writeScript(getElement()));

        return SKIP_BODY;
    }

    @Override
    protected void endTag() throws Throwable {
        //NOP
    }

}

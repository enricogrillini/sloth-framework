package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import java.util.ArrayList;
import java.util.List;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset.DataSet;
import lombok.Getter;
import lombok.Setter;

/**
 * Pojo per la rappresentazione dei grafici con Chart JS
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class ChartJsData extends FrameComponent {

    List<String> labels;
    List<DataSet> datasets;

    public ChartJsData() {
        labels = new ArrayList<>();
        datasets = new ArrayList<>();
    }

}

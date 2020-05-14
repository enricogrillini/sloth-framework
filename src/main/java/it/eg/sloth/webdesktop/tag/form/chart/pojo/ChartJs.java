package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import it.eg.sloth.framework.FrameComponent;
import lombok.Getter;
import lombok.Setter;

/**
 * Pojo per la rappresentazione dei grafici con Chart JS
 *
 * @author Enrico Grillini
 */
@Getter
@Setter

public class ChartJs extends FrameComponent {
    String type;
    ChartJsData data;
    ChartJsOptions options;
    AdditionalInfo additionalInfo;

    public ChartJs() {
        type = null;
        data = new ChartJsData();
        options = new ChartJsOptions();
        additionalInfo = new AdditionalInfo();
    }
}

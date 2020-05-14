package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import it.eg.sloth.framework.common.base.BaseFunction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChartJsTitle {
    boolean display;
    String text;

    public ChartJsTitle (String text) {
        if (BaseFunction.isBlank(text)) {
            this.display = false;
            this.text = null;
        } else {
            this.display = true;
            this.text = text;
        }
    }
}

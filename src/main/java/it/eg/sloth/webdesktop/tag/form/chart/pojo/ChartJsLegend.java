package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.eg.sloth.jaxb.form.LegendPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartJsLegend {

    boolean display;
    String position;

    public ChartJsLegend(LegendPosition legendPosition) {
        if (legendPosition == null) {
            this.display = false;
            this.position = null;

        } else {
            this.display = true;
            this.position = legendPosition.value().toLowerCase();
        }
    }

}

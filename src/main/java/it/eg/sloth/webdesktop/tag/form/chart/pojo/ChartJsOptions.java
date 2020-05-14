package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import it.eg.sloth.framework.FrameComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pojo per la rappresentazione dei grafici con Chart JS
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ChartJsOptions extends FrameComponent {

    ChartJsTitle title;
    ChartJsLegend legend;


}

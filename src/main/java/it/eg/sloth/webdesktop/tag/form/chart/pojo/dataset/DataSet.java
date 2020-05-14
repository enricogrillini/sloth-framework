package it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class DataSet extends FrameComponent {

    String label;
    List<BigDecimal> data;

    public DataSet() {
        label = StringUtil.EMPTY;
        data = new ArrayList<BigDecimal>();
    }

}

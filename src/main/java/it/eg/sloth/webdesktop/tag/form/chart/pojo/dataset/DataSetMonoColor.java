package it.eg.sloth.webdesktop.tag.form.chart.pojo.dataset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSetMonoColor extends DataSet {

    int borderWidth;
    String borderColor;
    String backgroundColor;
    String pointBackgroundColor;
    String pointBorderColor;

    public DataSetMonoColor() {
        super();
        borderWidth = 2;
    }

    public void setColors(String borderColor, String backgroundColor, String pointColor) {
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;

        this.pointBackgroundColor = pointColor;
        this.pointBorderColor = pointColor;
    }

}

package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import it.eg.sloth.framework.FrameComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalInfo extends FrameComponent {

  NumerFormat numerFormat;
  
  public AdditionalInfo () {
    numerFormat = NumerFormat.DECIMAL_FORMAT;
  }

}

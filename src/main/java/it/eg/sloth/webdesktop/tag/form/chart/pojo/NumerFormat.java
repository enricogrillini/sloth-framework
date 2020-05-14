package it.eg.sloth.webdesktop.tag.form.chart.pojo;

import it.eg.sloth.framework.FrameComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NumerFormat extends FrameComponent {

  public static final NumerFormat CURRENCY_FORMAT = new NumerFormat(2, ",", ".", " €");
  public static final NumerFormat DECIMAL_FORMAT = new NumerFormat(2, ",", ".", "");
  public static final NumerFormat INTEGER_FORMAT = new NumerFormat(0, ",", ".", "");
  public static final NumerFormat PERC_FORMAT = new NumerFormat(2, ",", ".", " %");

  int decimals;
  String decimalSeparator;
  String thousandsSeparator;
  String suffix;

}

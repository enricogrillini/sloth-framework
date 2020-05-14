package it.eg.sloth.framework.utility.xlsx.style;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BaseExcelStyle {

  private BaseExcelContainer baseExcelContainer;
  private BaseExcelFont baseExcelFont;
  private BaseExcelType baseExcelType;

}

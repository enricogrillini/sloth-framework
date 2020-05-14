package it.eg.sloth.form.fields.field.impl;

import java.math.BigDecimal;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.ForceCase;

/**
 * 
 * Definisce un Totalizer per le Grid
 * 
 * @author Enrico Grillini
 * 
 */
public class InputTotalizer extends Input<BigDecimal> {

  public InputTotalizer(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null, null, false, false, false, ViewModality.VIEW_AUTO, 0, ForceCase.NONE);
  }

  public InputTotalizer(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality, Integer maxLength, ForceCase forceCase) {
    super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality, maxLength, forceCase);
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.INPUT_TOTALIZER;
  }

}

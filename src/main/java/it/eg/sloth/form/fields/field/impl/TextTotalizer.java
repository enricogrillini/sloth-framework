package it.eg.sloth.form.fields.field.impl;

import java.math.BigDecimal;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;

/**
 * 
 * Definisce un Totalizer per le Grid
 * 
 * @author Enrico Grillini
 * 
 */
public class TextTotalizer extends Text<BigDecimal> {

  public TextTotalizer(String name, String description, String tooltip, DataTypes dataType) {
    super(name, description, tooltip, dataType);
  }

  public TextTotalizer(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tooltip, dataType, format, baseLink);
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.TEXT_TOTALIZER;
  }

}

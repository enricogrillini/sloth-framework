package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;

public class Semaphore extends ComboBox<String> {

  static final long serialVersionUID = 1L;

  public static final String WHITE = "W";
  public static final String GREEN = "G";
  public static final String YELLOW = "Y";
  public static final String RED = "R";
  public static final String BLACK = "B";

  static final String VALUES = "W,Bianco;G,Verde;Y,Giallo;R,Rosso;B,Nero";

  static final String GIF_WHITE = "<i class=\"fas fa-circle text-light\"></i>";
  static final String GIF_GREEN = "<i class=\"fas fa-circle text-success\"></i>";
  static final String GIF_YELLOW = "<i class=\"fas fa-circle text-warning\"></i>";
  static final String GIF_RED = "<i class=\"fas fa-circle text-danger\"></i>";
  static final String GIF_BLACK = "<i class=\"fas fa-circle text-dark\"></i>";

  public static final StringDecodeMap SEMAFORO_MAP = new StringDecodeMap(VALUES);

  public Semaphore(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null);
  }

  public Semaphore(String name, String alias, String description, String tooltip, DataTypes dataType, String format) {
    this(name, alias, description, tooltip, dataType, format, false, false, false);
  }

  public Semaphore(String name, String alias, String description, String tooltip, DataTypes dataType, String format, Boolean required, Boolean readOnly, Boolean hidden) {
    this(name, alias, description, tooltip, dataType, format, null, required, readOnly, hidden, ViewModality.VIEW_AUTO);
  }

  public Semaphore(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality) {
    super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality);
    setDecodeMap(SEMAFORO_MAP);
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.SEMAPHORE;
  }

  @Override
  public String getHtmlDecodedText() {
    switch (getValue()) {
    case Semaphore.WHITE:
      return GIF_WHITE;

    case Semaphore.GREEN:
      return GIF_GREEN;

    case Semaphore.YELLOW:
      return GIF_YELLOW;

    case Semaphore.RED:
      return GIF_RED;

    case Semaphore.BLACK:
      return GIF_BLACK;

    default:
      return GIF_WHITE;
    }

  }

}

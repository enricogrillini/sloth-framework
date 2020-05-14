package it.eg.sloth.form.base;

import java.util.Locale;

public interface Element extends Cloneable {
  
  public String getName();

  public Locale getLocale();

  public void  setLocale(Locale locale);

  public Object clone() throws CloneNotSupportedException;

}

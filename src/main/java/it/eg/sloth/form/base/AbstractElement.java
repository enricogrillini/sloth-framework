package it.eg.sloth.form.base;

import it.eg.sloth.framework.FrameComponent;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
public abstract class AbstractElement extends FrameComponent implements Element {

  private String name;
  private Locale locale;

  public AbstractElement(String name) {
    this.name = name.toLowerCase();
    this.locale = Locale.getDefault();
  }

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

}

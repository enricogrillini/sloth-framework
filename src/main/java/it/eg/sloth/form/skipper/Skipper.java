package it.eg.sloth.form.skipper;

import it.eg.sloth.form.base.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skipper extends AbstractElement {

  private boolean skipBody;

  public Skipper(String name, Boolean skipBody) {
    super(name);
    this.skipBody = skipBody == null ? false : skipBody;
  }

}

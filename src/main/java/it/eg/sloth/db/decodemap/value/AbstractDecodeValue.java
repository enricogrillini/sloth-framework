package it.eg.sloth.db.decodemap.value;

import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.BaseFunction;

public abstract class AbstractDecodeValue<T> extends FrameComponent implements DecodeValue<T> {

  private boolean valid;

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public boolean match(String matchString) {
    if (BaseFunction.isBlank(matchString)) {
      return false;
    }

    if (BaseFunction.isBlank(getDescription())) {
      return false;
    }

    return getDescription().toLowerCase().trim().indexOf(matchString.toLowerCase().trim()) >= 0;
  }

}

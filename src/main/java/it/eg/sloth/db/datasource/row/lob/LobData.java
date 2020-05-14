package it.eg.sloth.db.datasource.row.lob;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import it.eg.sloth.framework.FrameComponent;

public abstract class LobData<O extends Object> extends FrameComponent implements Externalizable {

  public static final int OFF_LINE = 0;
  public static final int ON_LINE = 1;
  public static final int CHANGED = 2;

  private int status;

  protected O value;

  public LobData() {
    setStatus(OFF_LINE);
    value = null;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public O getValue() {
    return value;
  }

  public void setValue(O value) {
    setStatus(CHANGED);
    this.value = value;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(getStatus());
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    setStatus(in.readInt());
  }

}

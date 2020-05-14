package it.eg.sloth.db.decodemap.value;


public class BaseDecodeValue<T> extends AbstractDecodeValue<T> {

  

  private T code;
  private String description;

  public BaseDecodeValue(T code, String description) {
    this(code, description, true);
  }

  public BaseDecodeValue(T code, String description, boolean valid) {
    this.code = code;
    this.description = description;
    setValid(valid);
  }

  public T getCode() {
    return code;
  }

  public void setCode(T code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}

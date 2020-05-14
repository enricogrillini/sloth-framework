package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.jaxb.form.ButtonType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Link extends Button {

  

  @Override
  public FieldType getFieldType() {
    return FieldType.LINK;
  }

  public Link(String name, String description, String tooltip) {
    super(name, description, tooltip);
  }

  public Link(String name, String description, String tooltip, Boolean hidden, Boolean disabled, ButtonType buttonType, String imgHtml) {
    super(name, description, tooltip, hidden, disabled, buttonType, imgHtml);
  }

}

package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.form.base.AbstractElement;
import it.eg.sloth.framework.common.casting.Casting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSimpleField extends AbstractElement implements it.eg.sloth.form.fields.field.SimpleField {

  String description;
  String tooltip;

  public AbstractSimpleField(String name, String description, String tooltip) {
    super(name);
    this.description = description;
    this.tooltip = tooltip;
  }

  public String getHtmlTooltip() {
    return Casting.getHtml(getTooltip());
  }

  public String getJsTooltip() {
    return Casting.getJs(getTooltip());
  }

  @Override
  public String getHtmlDescription() {
    return Casting.getHtml(getDescription());
  }

  @Override
  public String getJsDescription() {
    return Casting.getJs(getDescription());
  }

}

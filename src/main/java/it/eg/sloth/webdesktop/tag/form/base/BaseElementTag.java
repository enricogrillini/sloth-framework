package it.eg.sloth.webdesktop.tag.form.base;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Enrico Grillini
 *
 */
@Getter
@Setter
public abstract class BaseElementTag<E extends Element> extends WebDesktopTag<Form> {

  private static final long serialVersionUID = 1L;
  
  String name = "";

  /**
   * Restituisce l'elemento da visualizzare
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  protected E getElement() {
    return (E) getForm().getElement(getName());
  }

  /**
   * Restituisce l'elemento padre
   * 
   * @return
   */
  protected Element getParentElement() {
    return (Element) getForm().getParentElement(getName());
  }

}

package it.eg.sloth.form;

import it.eg.sloth.form.base.AbstractElements;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.framework.pageinfo.PageInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Form extends AbstractElements<Element> {

  private PageInfo pageInfo;

  public Form(String title) {
    super("");
    this.pageInfo = new PageInfo(title);
  }
  
}

package it.eg.sloth.webdesktop.tag.page;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.view.AbstractTag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyTag extends AbstractTag {

  static final long serialVersionUID = 1L;

  String className;

  @Override
  protected int startTag() throws Throwable {
    String classHtml = "";
    if (!BaseFunction.isBlank(getClassName())) {
      classHtml = " class=\"" + getClassName() + "\"";
    }

    writeln("<body id=\"page-top\"" + classHtml + ">");

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln("</body>");
  }

}

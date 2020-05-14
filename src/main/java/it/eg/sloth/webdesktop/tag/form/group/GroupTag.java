package it.eg.sloth.webdesktop.tag.form.group;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import lombok.Getter;
import lombok.Setter;

/**
 * Tag per il disegno di un gruppo di campi
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
public class GroupTag extends WebDesktopTag {

  private static final long serialVersionUID = 1L;

  public static final String CLASS_NAME = "frGroup";

  private String legend;

  @Override
  public int startTag() throws Throwable {
    writeln("");
    writeln("<fieldset>");
    if (!BaseFunction.isBlank(getLegend())) {
      String legendHtml = Casting.getHtml(getLegend());
      writeln(" <legend>" + legendHtml + "</legend>");
    }

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln("</fieldset>");
  }

}

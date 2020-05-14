package it.eg.sloth.webdesktop.tag.form.group;

import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Tag per il disegno di una cella
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
public class CellTag extends WebDesktopTag {

  static final long serialVersionUID = 1L;

  String width;
  String style;
  String classname;

  @Override
  public int startTag() throws Throwable {
    write(GroupWriter.openCell(getClassname(), getStyle(), getWidth()));

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    write("</div>");
  }


}

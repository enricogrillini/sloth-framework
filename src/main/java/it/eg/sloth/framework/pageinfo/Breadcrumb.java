package it.eg.sloth.framework.pageinfo;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.casting.Casting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Breadcrumb extends FrameComponent {

  String titolo;
  String hint;
  String link;

  public String getText() {
    return titolo;
  }

  public String getHtml() {
    if (link == null)
      return Casting.getHtml(titolo);
    else {
      if (hint == null)
        return "<a href=\"" + link + "\">" + Casting.getHtml(titolo) + "</a>";
      else
        return "<a href=\"" + link + "\" title=\"" + hint + "\">" + Casting.getHtml(titolo) + "</a>";
    }
  }

}

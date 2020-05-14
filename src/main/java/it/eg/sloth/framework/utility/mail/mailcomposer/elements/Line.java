package it.eg.sloth.framework.utility.mail.mailcomposer.elements;

/**
 * 
 * @author Enrico Grillini
 * 
 */
public class Line {
  String text;

  public Line(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}

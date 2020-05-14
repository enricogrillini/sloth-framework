package it.eg.sloth.framework.utility.mail.mailcomposer.elements;

/**
 * 
 * @author Enrico Grillini
 * 
 */
public class TableCell {
  private String text;
  private int colspan;
  private boolean[] border;
  private boolean alignRight;

  public TableCell(String text) {
    this(1, text, new boolean[] { false, true, true, false }, false);
  }

  public TableCell(String text, boolean alignRight) {
    this(1, text, new boolean[] { false, true, true, false }, alignRight);
  }
  
  public TableCell(int colspan, String text) {
    this(colspan, text, new boolean[] { false, true, true, false }, false);
  }

  public TableCell(int colspan, String text, boolean alignRight) {
    this(colspan, text, new boolean[] { false, true, true, false }, alignRight);
  }

  public TableCell(int colspan, String text, boolean[] border, boolean alignRight) {
    this.colspan = colspan;
    this.text = text;
    this.border = border;
    this.alignRight = alignRight;
  }

  public int getColspan() {
    return colspan;
  }

  public void setColspan(int colspan) {
    this.colspan = colspan;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean[] getBorder() {
    return border;
  }

  public void setBorder(boolean[] border) {
    this.border = border;
  }

  public boolean isAlignRight() {
    return alignRight;
  }

  public void setAlignRight(boolean alignRight) {
    this.alignRight = alignRight;
  }

}

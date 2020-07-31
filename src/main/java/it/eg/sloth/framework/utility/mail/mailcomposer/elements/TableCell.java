package it.eg.sloth.framework.utility.mail.mailcomposer.elements;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
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

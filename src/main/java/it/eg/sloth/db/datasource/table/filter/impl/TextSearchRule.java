package it.eg.sloth.db.datasource.table.filter.impl;

import java.util.Iterator;
import java.util.List;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.table.filter.FilterRule;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.StringUtil;

public class TextSearchRule extends FrameComponent implements FilterRule {

  private String text;
  private List<String> words;

  public TextSearchRule(String text) {
    setText(text);

  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
    words = StringUtil.words(text);
  }

  private List<String> getWords() {
    return words;
  }

  @Override
  public boolean check(DataSource dataSource) {
    if (getWords().size() <= 0) {
      return true;
    }

    StringBuilder baseText = new StringBuilder();

    Iterator<Object> iterator = dataSource.valueIterator();
    while (iterator.hasNext()) {
      Object value = iterator.next();

      if (value != null) {
        baseText.append(" " + value.toString());
      }
    }

    String match = baseText.toString().toLowerCase();

    for (String word : getWords()) {
      if (match.indexOf(word.toLowerCase()) > 0) {
        return false;
      }
    }

    return true;
  }
}

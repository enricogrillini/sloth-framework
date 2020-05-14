package it.eg.sloth.webdesktop.search.model.suggestion;

public class SearchFunction {

  private String name;
  private String url;
  private boolean external;
  private String html;

  public SearchFunction(String name, String url, boolean external, String html) {
    super();
    this.name = name;
    this.url = url;
    this.external = external;
    this.html = html;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isExternal() {
    return external;
  }

  public void setExternal(boolean external) {
    this.external = external;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }
}

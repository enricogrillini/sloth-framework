package it.eg.sloth.webdesktop.search.model.suggestion;

public class SearchSnippet {

  private String imgUrl;
  private String title;
  private String value;

  public SearchSnippet(String imgUrl, String title, String value) {
    this.imgUrl = imgUrl;
    this.title = title;
    this.value = value;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}

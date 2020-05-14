package it.eg.sloth.framework.pageinfo;

import java.util.HashMap;
import java.util.Map;

import it.eg.sloth.framework.FrameComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Gestione delle informazioni che caratterizzazno una pagina
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
public class PageInfo extends FrameComponent {

  String title;
  String description;
  String keyWords;
  Breadcrumbs breadcrumbs;
  ViewModality viewModality;
  PageStatus pageStatus;

  @Getter(AccessLevel.PRIVATE)
  @Setter(AccessLevel.PRIVATE)
  Map<String, Object> objects;

  public PageInfo(String title) {
    this.title = title;
    this.breadcrumbs = new Breadcrumbs();
    this.viewModality = ViewModality.VIEW_VISUALIZZAZIONE;
    this.pageStatus = PageStatus.MASTER;
    this.objects = new HashMap<>();
  }

  public void clearObjects() {
    objects.clear();
  }

  public void putObject(String key, Object value) {
    objects.put(key, value);
  }

  public Object getObject(String key) {
    return objects.get(key);
  }

  public boolean containsObject(String key) {
    return objects.containsKey(key);
  }

}

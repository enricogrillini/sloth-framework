package it.eg.sloth.webdesktop.controller.webpage;

import java.util.Random;

import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.MapSearchType;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.impl.AutoComplete;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.utility.FileType;
import it.eg.sloth.webdesktop.controller.common.SimplePageInterface;
import it.eg.sloth.webdesktop.controller.page.FormPage;
import it.eg.sloth.webdesktop.search.model.SimpleSuggestionList;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Fornisce una prima implementazione della navigazione. Rispetto al
 * BaseController aggiunge: - la gestione della navigazione base: metodo onInit
 * - la gestione ModelAndView di default
 * 
 * @author Enrico Grillini
 * 
 * @param <F>
 */
@Slf4j
public abstract class WebSimplePage<F extends Form> extends FormPage<F> implements SimplePageInterface {

  private ModelAndView modelAndView;

  protected abstract String getJspName();

  @Override
  public ModelAndView service() throws Exception {
    setModelAndView(new ModelAndView(getJspName()));

    // Gestisco il redirect
    String redirect = (String) getRequest().getSession().getAttribute("redirect");
    String navigation[] = getWebRequest().getNavigation();
    if (navigation.length == 2) {
      if ("redirect".equals(navigation[0])) {
        if (redirect != null && redirect.equals(navigation[1])) {
          return new ModelAndView(getJspName());
        } else {
          return new ModelAndView("redirect:" + getClass().getSimpleName() + ".html");
        }
      }
    }

    // Esecuzione della Page
    try {
      getMessageList().getList().clear();
      if (!defaultNavigation()) {
        onInit();
      }
    } catch (Exception e) {
      getMessageList().addBaseError(e);
      log.error("Errore", e);
    }

    if (getModelAndView() == null) {
      // Non pubblico nulla
      return null;
    } else {
      // Gestione custom
      return getModelAndView();
    }
  }

  protected void setDefaultForward() {
    setModelAndView(new ModelAndView(getJspName()));
  }

  protected void setDefaultRedirect() {
    String redirect = Math.abs(new Random().nextInt()) + "";

    getRequest().getSession().setAttribute("redirect", redirect);
    setModelAndView(new ModelAndView("redirect:" + getClass().getSimpleName() + ".html?" + NavigationConst.navStr("redirect", redirect) + "=true"));
  }

  protected boolean defaultNavigation() throws Exception {

    String navigation[] = getWebRequest().getNavigation();
    if (navigation.length == 2 && NavigationConst.AUTOCOMPLETE.equals(navigation[0])) {
      AutoComplete<?> autoComplete = (AutoComplete<?>) getForm().getElement(navigation[1]);

      DecodeMap<?, ?> decodeMap = null;
      if (getForm().getElement(navigation[1]) instanceof DecodedDataField) {
        DecodedDataField<?> decodedDataField = (DecodedDataField<?>) getForm().getElement(navigation[1]);
        decodeMap = decodedDataField.getDecodeMap();
      } else {
        MultipleAutoComplete<?, ?> decodedDataField = (MultipleAutoComplete<?, ?>) getForm().getElement(navigation[1]);
        decodeMap = decodedDataField.getDecodeMap();
      }
      
      String query = getWebRequest().getString("query");
      SimpleSuggestionList list = new SimpleSuggestionList();
      for (DecodeValue<?> decodeValue : decodeMap.performSearch(query, MapSearchType.match, 10)) {
        SimpleSuggestion simpleSuggestion = new SimpleSuggestion();
        simpleSuggestion.setValue(decodeValue.getDescription());
        
        list.getSuggestions().add(simpleSuggestion);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("value", decodeValue.getDescription());
//        jsonObject.put("valid", decodeValue.isValid());
//        rowsJsonArray.put(jsonObject);
      }
      
      ObjectMapper mapper = new ObjectMapper();
      String jsonString = mapper.writeValueAsString(list);
      
      log.info(jsonString);

      try {
        clearModelAndView();
        getResponse().getWriter().write(jsonString);
      } finally {
        getResponse().getWriter().close();
      }

      return true;
    }

    return false;
  }

  public void onInit() throws Exception {
    execInit();
  }

  public abstract void execInit() throws Exception;

  protected ModelAndView getModelAndView() {
    return modelAndView;
  }

  protected void clearModelAndView() {
    this.modelAndView = null;
  }

  protected void setModelAndView(String fileName, FileType fileType) {
    getResponse().setContentType(fileType.getContentType());
    getResponse().setHeader("Content-Disposition", "attachment; filename=" + StringUtil.toFileName(fileName));
    clearModelAndView();
  }

  protected void setModelAndView(ModelAndView modelAndView) {
    this.modelAndView = modelAndView;
  }

  protected void setModelAndView(String modelAndView) {
    setModelAndView(new ModelAndView(modelAndView));
  }

}

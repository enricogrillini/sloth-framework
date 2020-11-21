package it.eg.sloth.webdesktop.controller.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.MapSearchType;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.utility.FileType;
import it.eg.sloth.webdesktop.controller.common.SimplePageInterface;
import it.eg.sloth.webdesktop.search.model.SimpleSuggestionList;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;

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
 * Fornisce una prima implementazione della navigazione. Rispetto al
 * BaseController aggiunge: - la gestione della navigazione base: metodo onInit
 * - la gestione ModelAndView di default - L'autocomplete
 *
 * @param <F>
 * @author Enrico Grillini
 */
@Slf4j
public abstract class SimplePage<F extends Form> extends FormPage<F> implements SimplePageInterface {

    private ModelAndView modelAndView;

    protected abstract String getJspName();

    public SimplePage() {
        this.modelAndView = null;
    }

    @Override
    public ModelAndView service() throws Exception {
        setModelAndView(new ModelAndView());

        // Verifico che sia richiesta una sola visualizzazione e che la pagina
        // richiesta sia quella corrente
        boolean view = getWebRequest().getNavigation().length == 1 && "view".equals(getWebRequest().getNavigation()[0]) && !isNewForm();

        if (view) {
            log.info("Page view");
            return new ModelAndView(getJspName());
        } else {
            log.info("Page service");

            // Esecuzione della Page
            try {
                getMessageList().setPopup(true);
                getMessageList().getList().clear();

                // Eseguo le operazioni di inizializzaizone sel la form se è nuova o se la navigazione non è gestita
                if (isNewForm() || !defaultNavigation()) {
                    onInit();
                }
            } catch (Exception e) {
                getMessageList().addBaseError(e);
                log.error("Errore", e);
            }

            if (getModelAndView() == null) {
                // Non pubblico nulla
                return null;
            } else if (getModelAndView().getViewName() == null) {
                // Getione di default: redirect per evitare problemi con il refresh
                return new ModelAndView("redirect:" + getClass().getSimpleName() + ".html?" + NavigationConst.navStr("view") + "=true");
            } else {
                // Gestione custom
                return getModelAndView();
            }
        }
    }

    protected boolean defaultNavigation() throws Exception {

        String[] navigation = getWebRequest().getNavigation();
        if (navigation.length == 2 && NavigationConst.AUTOCOMPLETE.equals(navigation[0])) {
            log.info(NavigationConst.AUTOCOMPLETE);

            DecodeMap<?, ?> decodeMap = null;
            if (getForm().getElement(navigation[1]) instanceof DecodedDataField) {
                DecodedDataField<?> decodedDataField = (DecodedDataField<?>) getForm().getElement(navigation[1]);
                decodeMap = decodedDataField.getDecodeMap();
            }

            String query = getWebRequest().getString("query");
            SimpleSuggestionList list = new SimpleSuggestionList();
            if (decodeMap != null) {
                for (DecodeValue<?> decodeValue : decodeMap.performSearch(query, MapSearchType.MATCH, 10)) {
                    SimpleSuggestion simpleSuggestion = new SimpleSuggestion();
                    simpleSuggestion.setValue(decodeValue.getDescription());
                    simpleSuggestion.setValid(decodeValue.isValid());

                    list.getSuggestions().add(simpleSuggestion);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(list);

            clearModelAndView();
            try {
                getResponse().setContentType("text/x-json;charset=" + StandardCharsets.UTF_8.name());
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

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public void clearModelAndView() {
        this.modelAndView = null;
    }

    public void setModelAndView(String fileName, FileType fileType) {
        getResponse().setContentType(fileType.getContentType());
        getResponse().setHeader("Content-Disposition", "attachment; filename=" + StringUtil.toFileName(fileName));
        clearModelAndView();
    }

    public void setModelAndView(ModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }

    public void setModelAndView(String modelAndView) {
        setModelAndView(new ModelAndView(modelAndView));
    }

}

package it.eg.sloth.form;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.ToString;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.*;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@ToString
public class WebRequest {

    Map<String, List<Object>> map;

    public static final String PREFIX = "navigationprefix";
    public static final String SEPARATOR = "___";

    public void clear() {
        map.clear();
    }

    /**
     * Crea una WebRequest a partire da una request
     *
     * @param request
     * @throws IOException
     * @throws ServletException
     */
    public WebRequest(HttpServletRequest request) throws IOException, ServletException {
        map = new HashMap<>();

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            addStrings(entry.getKey(), entry.getValue());
        }

        if (request.getContentType() != null && request.getContentType().startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            for (Part part : request.getParts()) {
                if (part.getContentType() != null) {
                    add(part.getName(), part);
                }
            }
        }
    }


    /**
     * Ritorna la lista di oggetti afferenti al name passato
     *
     * @param name
     * @return
     */
    private List<Object> getList(String name) {
        if (map.containsKey(name.toLowerCase())) {
            return map.get(name.toLowerCase());
        } else {
            List<Object> list = new ArrayList<>();
            map.put(name.toLowerCase(), list);
            return list;
        }
    }

    private Object get(String name) {
        if (map.containsKey(name.toLowerCase())) {
            return getList(name.toLowerCase()).get(0);
        } else {
            return null;
        }
    }


    /**
     * Aggiunge un valore
     *
     * @param name
     * @param value
     */
    private void add(String name, Object value) {
        getList(name).add(value);
    }

    /**
     * Aggiunge un valore una lista di stringhe
     *
     * @param name
     * @param values
     */
    private void addStrings(String name, String[] values) {
        getList(name).addAll(Arrays.asList(values));
    }

    /**
     * Reperisce una valore di tipo stringa
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        Object object = get(name);

        if (object instanceof Part) {
            Part part = (Part) object;
            return part.getName();
        } else {
            return (String) object;
        }
    }


    /**
     * Reperisce la lista di Stringhe
     *
     * @param name
     * @return
     */
    public List<String> getStringList(String name) {
        List<String> result = new ArrayList<>();
        for (Object object : getList(name)) {
            if (object instanceof Part) {
                result.add(((Part) object).getName());
            } else {
                result.add((String) object);
            }
        }

        return result;
    }

    /**
     * Ritorna un Part
     *
     * @param name
     * @return
     */
    public Part getPart(String name) {
        return (Part) get(name);
    }

    /**
     * Reperisce la lista di Part
     *
     * @param name
     * @return
     */
    public List<Part> getPartList(String name) {
        List<Part> result = new ArrayList<>();
        for (Object object : getList(name)) {
            if (object instanceof Part) {
                result.add(((Part) object));
            }
        }

        return result;
    }

    /**
     * Ritorna i parametri di navigazione
     *
     * @return
     */
    public String[] getNavigation() {
        for (String attributeName : map.keySet()) {
            if (attributeName.indexOf(PREFIX) == 0) {
                String string = attributeName.substring(PREFIX.length() + SEPARATOR.length());

                String[] result = string.split(SEPARATOR);
                result[result.length - 1] = result[result.length - 1].replace("\\.x", "");
                result[result.length - 1] = result[result.length - 1].replace("\\.y", "");
                return result;
            }
        }

        return NavigationConst.INIT.split(SEPARATOR);
    }

    public long getNavigationSequence() {
        return Long.valueOf(getString("_navigation_sequence"));
    }


}

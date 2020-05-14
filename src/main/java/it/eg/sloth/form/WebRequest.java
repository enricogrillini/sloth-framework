package it.eg.sloth.form;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import it.eg.sloth.framework.FrameComponent;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Trasforma la request HTTP mascherando la gestione della trascodifica del
 * multipart
 *
 * @author Enrico Grillini
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WebRequest extends FrameComponent {

    Map<String, List<Object>> map;

    public static final String PREFIX = "navigationprefix";
    public static final String SEPARATOR = "___";

    public void clear() {
        map.clear();
    }

    /**
     * Crea una WebRequest a aprtire da una request
     *
     * @param request
     * @throws FileUploadException
     * @throws UnsupportedEncodingException
     */
    public WebRequest(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {
        map = new HashMap<>();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            // MULTIPART

            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List<FileItem> items = upload.parseRequest(request);

            // Lettura Multipart
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString(StandardCharsets.UTF_8.name());

                    add(name, value);

                } else {
                    add(item.getFieldName(), item);
                }
            }
        } else {
            // NON MULTIPART
            for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                addStrings(entry.getKey(), entry.getValue());
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

        if (object instanceof FileItem) {
            FileItem fileItem = (FileItem) object;
            return fileItem.getName();
        } else {
            return (String) object;
        }
    }

    /**
     * Ritorna un FileItem
     *
     * @param name
     * @return
     */
    public FileItem getFileItem(String name) {
        return (FileItem) get(name);
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
                result[result.length - 1] = result[result.length - 1].replaceAll("\\.x", "");
                result[result.length - 1] = result[result.length - 1].replaceAll("\\.y", "");
                return result;
            }
        }

        return NavigationConst.INIT.split(SEPARATOR);
    }

}

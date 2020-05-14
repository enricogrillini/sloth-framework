package it.eg.sloth.form.fields;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.base.AbstractElements;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.common.message.MessageList;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;

/**
 * @author Enrico Grillini
 */
@Getter
@Setter
public class Fields<D extends DataSource> extends AbstractElements<SimpleField> {

    String description;
    D dataSource;

    public Fields(String name) {
        this(name, null);
    }

    public Fields(String name, String description) {
        super(name);

        this.description = description;
        this.dataSource = null;
    }

    /**
     * Inizializza i valori
     */
    public void clearData() throws BusinessException {
        for (SimpleField element : this) {
            if (element instanceof TextField) {
                TextField<?> field = (TextField<?>) element;
                field.setValue(null);
            }
        }
    }

    /**
     * Imposta il contenuto della griglia prelevandolo dal DataRow associato
     */
    public void copyFromDataSource() throws BusinessException {
        if (getDataSource() != null) {
            copyFromDataSource(getDataSource());
        } else {
            clearData();
        }
    }

    /**
     * Ricopia il contenuto della griglia sulla DataRow associata
     */
    public void copyToDataSource() {
        if (getDataSource() != null) {
            copyToDataSource(getDataSource());
        }
    }

    /**
     * Popola il DataSource con i valori contenuti nei campi
     *
     * @param dataSource
     */
    public void copyToDataSource(DataSource dataSource) {
        for (SimpleField simpleField : this) {
            if (simpleField instanceof TextField) {
                TextField<?> field = (TextField<?>) simpleField;
                field.copyToDataSource(dataSource);
            }
        }
    }

    /**
     * Popola i campi con i valori del DataSource
     *
     * @param dataSource
     */
    public void copyFromDataSource(DataSource dataSource) throws BusinessException {
        if (dataSource == null)
            return;

        for (SimpleField element : getElements()) {
            if (element instanceof TextField) {
                TextField<?> field = (TextField<?>) element;
                field.copyFromDataSource(dataSource);
            }
        }
    }

    /**
     * Verifica che il contenuto della request sia congruente con le prorietà dei campi
     *
     * @param messages
     * @return
     * @throws BusinessException
     */
    public boolean validate(MessageList messages) throws BusinessException {
        for (SimpleField element : getElements()) {
            if (element instanceof InputField && !((InputField<?>) element).validate(messages)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ricopia il contenuto della request nei campi
     *
     * @param webRequest
     * @throws ParseException
     */
    public void post(WebRequest webRequest) {
        for (SimpleField element : getElements()) {
            if (element instanceof SimpleField) {
                element.post(webRequest);
            }
        }
    }

    public boolean postAndValidate(WebRequest webRequest, MessageList messageList) throws BusinessException {
        post(webRequest);
        return validate(messageList);
    }

    public boolean postValidateCopy(WebRequest webRequest, MessageList messageList, DataSource dataSource) throws BusinessException {
        post(webRequest);
        if (validate(messageList)) {
            copyToDataSource(dataSource);
            return true;
        }

        return false;
    }

}

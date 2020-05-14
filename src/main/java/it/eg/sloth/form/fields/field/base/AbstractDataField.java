package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.row.lob.LobData;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDataField<T extends Object> extends AbstractSimpleField implements DataField<T> {

    // Contiente il valore informativo come stringa
    private String data;

    private String alias;
    private DataTypes dataType;
    private String format;

    public AbstractDataField(String name, String alias, String description, String tooltip, DataTypes dataType, String format) {
        super(name, description, tooltip);
        this.alias = alias;
        this.dataType = dataType;
        this.format = format;
    }

    @Override
    public String getAlias() {
        return BaseFunction.isBlank(alias) ? getName() : alias;
    }

    @Override
    public String escapeHtmlText() {
       return getDataType().escapeHtmlText   (getData(), getLocale(), getFormat());
    }

    @Override
    public String escapeJsText() {
        return getDataType().escapeJsText   (getData(), getLocale(), getFormat());
    }


    @Override
    public String escapeHtmlValue() {
        return Casting.getHtml(getData(), false, false);
    }

    @Override
    public String escapeJsValue() {
        return Casting.getJs(getData());
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        try {
            return (T) getDataType().parseValue(data, getLocale(), getFormat());
        } catch (BusinessException e) {
            return null;
        }
    }

    @Override
    public void setValue(T value) throws BusinessException {
        setData(getDataType().formatValue(value, getLocale(), getFormat()));
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) throws BusinessException {
        if (dataSource != null) {
            Object object = dataSource.getObject(getAlias());

            // TODO: Sarebbe pià corretto rendere questo passaggio trasparente rispetto alla gestione dei LOB che dovrebbe essere demandata ai Bean
            if (object instanceof LobData) {
                object = ((LobData<?>) object).getValue();
            }

            setData(getDataType().formatValue(object, getLocale(), getFormat()));
        }
    }

    @Override
    public void copyToDataSource(DataSource dataSource) {
        if (dataSource != null) {
            dataSource.setObject(getAlias(), getValue());
        }
    }

    @Override
    public boolean isValid() {
        return check() == null;
    }

    @Override
    public Message check() {
        return getDataType().check(this);
    }

    @Override
    public boolean validate(MessageList messages) throws BusinessException {
        return true;
    }

    @Override
    public void post(WebRequest webRequest) {
        // Il campo à presentato in sola visualizzazione à non à modificabile dal
        // Browser
    }

    @Override
    public void postEscaped(WebRequest webRequest, String encoding) {
        // Il campo à presentato in sola visualizzazione à non à modificabile dal
        // Browser
    }

}

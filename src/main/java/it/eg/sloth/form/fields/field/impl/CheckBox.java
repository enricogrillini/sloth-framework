package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckBox<T> extends InputField<T> {

    static final long serialVersionUID = 1L;

    public static final String DEFAULT_VAL_CHECKED = "S";
    public static final String DEFAULT_VAL_UN_CHECKED = "N";

    T valChecked;
    T valUnChecked;

    public CheckBox(String name, String description, String tooltip, DataTypes dataType) {
        this(name, name, description, tooltip, dataType, null, false, false, false);
    }

    @SuppressWarnings("unchecked")
    public CheckBox(String name, String alias, String description, String tooltip, DataTypes dataType, String format, Boolean required, Boolean readOnly, Boolean hidden) {
        this(name, alias, description, tooltip, dataType, format, null, required, readOnly, hidden, ViewModality.VIEW_AUTO, (T) DEFAULT_VAL_CHECKED, (T) DEFAULT_VAL_UN_CHECKED);
    }

    public CheckBox(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality, T valChecked, T valUnChecked) {
        super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality);
        setValChecked(valChecked);
        setValUnChecked(valUnChecked);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.CHECK_BOX;
    }

    public boolean isChecked() {
        return !BaseFunction.isBlank(getData()) && getData().equals(getValChecked());
    }

    public void setChecked() throws BusinessException {
        setValue(getValChecked());
    }

    public void setUnChecked() throws BusinessException {
        setValue(getValUnChecked());
    }

    @Override
    public void post(WebRequest webRequest) {
        if (!isReadOnly()) {
            if (webRequest.getString(getName()) == null) {
                setData((String) BaseFunction.nvl(getValUnChecked(), "N"));
            } else {
                setData((String) BaseFunction.nvl(getValChecked(), "S"));
            }
        }
    }

}

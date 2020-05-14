package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.AbstractSimpleField;
import it.eg.sloth.framework.pageinfo.ViewModality;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.fileupload.FileItem;

@Getter
@Setter
public class File extends AbstractSimpleField {

    static final long serialVersionUID = 1L;

    String alias;
    boolean required;
    boolean readOnly;
    boolean hidden;
    ViewModality viewModality;
    int maxSize;

    private FileItem fileItem;

    public File(String name, String description, String tooltip) {
        this(name, name, description, tooltip, false, false, false, ViewModality.VIEW_AUTO, 0);
    }

    public File(String name, String alias, String description, String tooltip, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality, Integer maxSize) {
        super(name, description, tooltip);
        this.alias = (alias == null) ? name.toLowerCase() : alias.toLowerCase();
        this.required = required != null && required;
        this.readOnly = readOnly != null && readOnly;
        this.hidden = hidden != null && hidden;
        this.viewModality = viewModality;
        this.maxSize = maxSize == null ? 0 : maxSize;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.FILE;
    }

    @Override
    public void post(WebRequest webRequest) {
        if (!isReadOnly()) {
            setFileItem(webRequest.getFileItem(getName()));
        }
    }

    @Override
    public void postEscaped(WebRequest webRequest, String encoding) {
        post(webRequest);
    }

}

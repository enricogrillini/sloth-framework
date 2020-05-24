package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.AbstractSimpleField;
import it.eg.sloth.framework.pageinfo.ViewModality;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

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
 *
 * @author Enrico Grillini
 */
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

    private Part part;

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
            setPart(webRequest.getPart(getName()));
        }
    }

    public String getPartFileName() {
        if (part != null) {
            return getPart().getSubmittedFileName();
        } else {
            return null;
        }
    }

    public byte[] getPartContent() throws IOException {
        if (part != null) {
            try (InputStream inputStream = getPart().getInputStream()) {
                return IOUtils.toByteArray(inputStream);
            }
        } else {
            return new byte[0];
        }
    }

    @Override
    public void postEscaped(WebRequest webRequest, String encoding) {
        post(webRequest);
    }

}

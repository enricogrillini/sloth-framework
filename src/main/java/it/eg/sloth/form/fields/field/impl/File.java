package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.HtmlFileType;
import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
@SuperBuilder(toBuilder = true)
public class File implements SimpleField {

    String name;
    Locale locale;
    String alias;

    String description;
    String tooltip;

    Boolean required;
    Boolean readOnly;
    Boolean hidden;
    ViewModality viewModality;
    Integer maxSize;
    HtmlFileType htmlFileType;

    private Part part;

    @Getter(AccessLevel.NONE)
    private boolean exists;

    public File(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    @Override
    public Locale getLocale() {
        return this.locale == null ? Locale.getDefault() : this.locale;
    }

    public String getAlias() {
        return BaseFunction.isBlank(alias) ? getName() : alias.toLowerCase();
    }

    public boolean isRequired() {
        return required != null && required;
    }

    public boolean isReadOnly() {
        return readOnly != null && readOnly;
    }

    public boolean isHidden() {
        return hidden != null && hidden;
    }

    public ViewModality getViewModality() {
        return viewModality != null ? viewModality : ViewModality.AUTO;
    }

    public int getMaxSize() {
        return maxSize != null ? maxSize : 0;
    }

    public HtmlFileType getHtmlFileType() {
        return htmlFileType != null ? htmlFileType : HtmlFileType.GENERIC;
    }

    public boolean exists() {
        return this.exists;
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

    @Override
    public void post(BffFields bffFields) {
        // NOP
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
    public File newInstance() {
        return toBuilder().build();
    }

}

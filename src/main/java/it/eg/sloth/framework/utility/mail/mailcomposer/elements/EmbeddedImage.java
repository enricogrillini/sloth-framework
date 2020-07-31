package it.eg.sloth.framework.utility.mail.mailcomposer.elements;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
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
public class EmbeddedImage {
    private byte[] image;
    private String description;
    private String type;
    private String link;

    public EmbeddedImage(byte[] image, String description, String type, String link) {
        init(image, description, type, link);
    }

    public EmbeddedImage(File file, String description, String type, String link) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            init(IOUtils.toByteArray(inputStream), description, type, link);
        }
    }

    private void init(byte[] image, String description, String type, String link) {
        this.image = image;
        this.description = description;
        this.type = type;
        this.link = link;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}

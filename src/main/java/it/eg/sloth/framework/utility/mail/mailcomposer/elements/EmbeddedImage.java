package it.eg.sloth.framework.utility.mail.mailcomposer.elements;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
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

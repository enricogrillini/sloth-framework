package it.eg.sloth.framework.utility.mail.mailcomposer.elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import it.eg.sloth.framework.utility.stream.StreamUtil;

/**
 * @author Enrico Grillini
 */
public class EmbeddedImage {
    private byte[] image;
    private String description;
    private String type;
    private String link;

    public EmbeddedImage(byte[] image, String description, String type, String link) {
        this.image = image;
        this.description = description;
        this.type = type;
        this.link = link;
    }

    public EmbeddedImage(File file, String description, String type, String link) throws IOException {
        this(StreamUtil.inputStreamToByteArray(new FileInputStream(file)), description, type, link);
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

package it.eg.sloth.form.customgrid;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import lombok.Getter;
import lombok.Setter;

public class CustomCell {

    private String text;
    private String html;

    @Setter
    private String css;

    public CustomCell(String html, String css) {
        setHtml(html);
        this.css = css;
    }

    public void setHtml(String html) {
        this.html = BaseFunction.isBlank(html) ? "" : html;
        this.text = null;
    }

    public void setText(String text) {
        this.html = "";
        this.text = text;
    }

    public String getHtml() {
        return BaseFunction.isBlank(text) ? html : Casting.getHtml(text);
    }

    public String getCss() {
        return BaseFunction.isBlank(css) ? "" : css;
    }

}

package it.eg.sloth.webdesktop.tag.support;

import it.eg.sloth.form.Escaper;

public class SampleEscaper implements Escaper {

    @Override
    public String escapeText(String text) {
        return "Escaped - " + text;
    }
}

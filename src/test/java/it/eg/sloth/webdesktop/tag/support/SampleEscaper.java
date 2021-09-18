package it.eg.sloth.webdesktop.tag.support;

import it.eg.sloth.form.Escaper;
import it.eg.sloth.framework.common.base.BaseFunction;

public class SampleEscaper implements Escaper {

    @Override
    public String escapeText(String text) {
        return "Escaped - " + BaseFunction.nvl(text, "");
    }
}

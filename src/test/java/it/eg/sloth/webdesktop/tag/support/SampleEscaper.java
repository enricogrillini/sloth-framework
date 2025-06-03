package it.eg.sloth.webdesktop.tag.support;

import it.eg.sloth.escaper.Escaper;
import it.eg.sloth.framework.common.base.BaseFunction;

public class SampleEscaper implements Escaper {

    @Override
    public String escapeText(String text) {
        return "Escaped - " + BaseFunction.nvl(text, "");
    }
}

package it.eg.sloth.form;

import it.eg.sloth.framework.common.casting.Casting;

public interface Escaper {

    String escapeText(String text);

    default  String escapeValue(String value) {
        return Casting.getHtml(value, false, false);
    }

}

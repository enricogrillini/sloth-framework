package it.eg.sloth.escaper;

import it.eg.sloth.form.fields.field.impl.Semaphore;
import it.eg.sloth.framework.common.base.BaseFunction;

public class SemaforoTextEscaper implements Escaper {

    @Override
    public String escapeText(String text) {
        if (BaseFunction.isBlank(text)) {
            return "";
        }

        return switch (text) {
            case Semaphore.RED -> "<div class=\"semaforo red\"></div>";
            case Semaphore.YELLOW -> "<div class=\"semaforo yellow\"></div>";
            case Semaphore.GREEN -> "<div class=\"semaforo green\"></div>";
            default -> "";
        };
    }
}

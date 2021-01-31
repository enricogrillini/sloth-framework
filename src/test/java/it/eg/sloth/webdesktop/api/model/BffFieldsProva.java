package it.eg.sloth.webdesktop.api.model;

import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class BffFieldsProva extends BffFields {

    String autocomplete;
    String testo;
    String numero;
    String decimal;
    String currency;
    String data;

}

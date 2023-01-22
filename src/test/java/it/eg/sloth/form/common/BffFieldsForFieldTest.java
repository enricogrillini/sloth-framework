package it.eg.sloth.form.common;

import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class BffFieldsForFieldTest extends BffFields {

    String fieldName;

}

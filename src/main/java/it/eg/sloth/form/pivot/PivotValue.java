package it.eg.sloth.form.pivot;

import it.eg.sloth.jaxb.form.ConsolidateFunction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class PivotValue extends PivotElement {

    ConsolidateFunction consolidateFunction;

}

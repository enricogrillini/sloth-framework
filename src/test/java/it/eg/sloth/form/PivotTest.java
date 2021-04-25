package it.eg.sloth.form;

import it.eg.sloth.form.pivot.Pivot;
import it.eg.sloth.form.pivot.PivotColumn;
import it.eg.sloth.form.pivot.PivotRow;
import it.eg.sloth.form.pivot.PivotValue;
import it.eg.sloth.jaxb.form.ConsolidateFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

 class PivotTest {

    @Test
    void pivotTest() {
        Pivot pivot = Pivot.builder().name("Prova").title("Pivot").build();
        pivot.addChild(PivotRow.builder().name("idProgetto").fieldAlias(null).description("Progetto").build());
        pivot.addChild(PivotRow.builder().name("idSottoProgetto").fieldAlias(null).description("Sotto Progetto").build());
        pivot.addChild(PivotRow.builder().name("collaboratore").fieldAlias(null).description("collaboratore").build());
        pivot.addChild(PivotColumn.builder().name("mese").fieldAlias(null).description("Mese").build());
        pivot.addChild(PivotValue.builder().name("tariffa").fieldAlias(null).description("Tariffa").consolidateFunction(ConsolidateFunction.MIN).build());
        pivot.addChild(PivotValue.builder().name("oreTotali").fieldAlias(null).description("Ore Tot").consolidateFunction(ConsolidateFunction.SUM).build());
        pivot.addChild(PivotValue.builder().name("ggTotali").fieldAlias(null).description("gg Tot").consolidateFunction(ConsolidateFunction.SUM).build());
        pivot.addChild(PivotValue.builder().name("importoTotale").fieldAlias(null).description("Imp Tot").consolidateFunction(ConsolidateFunction.SUM).build());

        assertFalse(pivot.getElements().isEmpty());
    }
}

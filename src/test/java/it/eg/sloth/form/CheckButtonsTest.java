package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.CheckButtons;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
class CheckButtonsTest {

    @Test
    void checkButtonsTest() throws FrameworkException {
        CheckButtons checkButtons = CheckButtons.<List<String>, String>builder()
                .name("Prova")
                .alias(null)
                .orderByAlias(null)
                .description("Visualizza")
                .tooltip(null)
                .dataType(DataTypes.STRING)
                .format(null)
                .baseLink(null)
                .linkField(null)
                .required(null)
                .readOnly(null)
                .hidden(null)
                .viewModality(ViewModality.EDIT)
                .values("Consuntivati|Fatturazione|Trasferta|Previsionale")
                .descriptions("Cons.|Fatt.|Trasf.|Prev.")
                .tooltips(null)
                .build();

        checkButtons.setData("Consuntivati|Trasferta");

        assertEquals("Cons., Trasf.", checkButtons.getText());
    }


}

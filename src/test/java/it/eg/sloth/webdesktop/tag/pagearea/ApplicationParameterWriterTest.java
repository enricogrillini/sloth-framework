package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.parameter.ApplicationParametersSingleton;
import it.eg.sloth.webdesktop.parameter.model.ApplicationParameter;
import it.eg.sloth.webdesktop.tag.pagearea.writer.ContentWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

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
class ApplicationParameterWriterTest {
    private static final String APPLICATION_PARAMETER_TABLE_1 = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/application-parameter-table1.html");
    private static final String APPLICATION_PARAMETER_TABLE_2 = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/application-parameter-table2.html");

    Grid<Table> grid;

    @BeforeEach
    void init() {

        ApplicationParametersSingleton.getInstance().clear();
        ApplicationParametersSingleton.getInstance().put(ApplicationParameter.builder()
                .codParameter("PARAM_DATE")
                .dataType(DataTypes.DATE)
                .multivalue(false)
                .value("01/01/2023")
                .build());

        ApplicationParametersSingleton.getInstance().put(ApplicationParameter.builder()
                .codParameter("PARAM_DECIMAL")
                .dataType(DataTypes.DECIMAL)
                .multivalue(false)
                .value("13,55")
                .build());

        ApplicationParametersSingleton.getInstance().put(ApplicationParameter.builder()
                .codParameter("PARAM_MULTIVALUE")
                .dataType(DataTypes.STRING)
                .multivalue(true)
                .value("aaa|bbb")
                .build());

        grid = new Grid<>("Anagrafica", "Prova");
        grid.setDataSource(ApplicationParametersSingleton.getInstance().getTable());
    }


    @Test
    void writeAlertCenterTest() throws FrameworkException {
        assertEquals(APPLICATION_PARAMETER_TABLE_1, ContentWriter.applicationParameters(grid, ViewModality.VIEW, Locale.ITALY).replace("\r\n", "\n"));
    }


    @Test
    void writeAlertCenterTest2() throws FrameworkException {
        grid.getDataSource().setCurrentRow(2);

        assertEquals(APPLICATION_PARAMETER_TABLE_2, ContentWriter.applicationParameters(grid, ViewModality.EDIT, Locale.ITALY).replace("\r\n", "\n"));
    }


}

package it.eg.sloth.webdesktop.tag;


import it.eg.sloth.AbstractTest;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;


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
class GroupWriterTest extends AbstractTest {

    @Test
    void closeCell() {
        assertEqualsStr("closeCell.html", GroupWriter.closeCell());
    }


    @Test
    void closeGroup() {
        assertEqualsStr("closeGroup.html", GroupWriter.closeGroup());
    }

    @Test
    void closeRow() {
        assertEqualsStr("closeRow.html", GroupWriter.closeRow());
    }

    @Test
    void openCell() throws FrameworkException {
        assertEqualsStr("openCell.html", GroupWriter.openCell(null));
        assertEqualsStr("openCell.html", GroupWriter.openCell("10%"));
        assertEqualsStr("openCell.html", GroupWriter.openCell(""));
    }


    @Test
    void openCell_mobile() throws FrameworkException {
        assertEqualsStr("openCell_mobile.html", GroupWriter.openCell("4cols", "6cols"));
    }

    @Test
    void openGroup() {
        assertEqualsStr("openGroup.html", GroupWriter.openGroup(null));
    }

    @Test
    void openGroup_legend() {
        assertEqualsStr(MessageFormat.format("openGroup_legend.html", "<legend>prova</legend>"), GroupWriter.openGroup("prova"));
    }


    @Test
    void openRow() {
        assertEqualsStr("openRow.html", GroupWriter.openRow());
    }

}

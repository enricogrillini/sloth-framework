package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
class TextMdTest {

    @Test
    void buttonTest() throws FrameworkException {
        Input<String> field = new Input<String>("name", "description", DataTypes.STRING);
        field.setValue("# Titolo\n<script>alert(\"eeee\")</script>");

        assertEquals("<h1>Titolo</h1>\n" +
                "<p>&lt;script&gt;alert(&quot;eeee&quot;)&lt;/script&gt;</p>\n", TextControlWriter.writeMd(field));
    }

}


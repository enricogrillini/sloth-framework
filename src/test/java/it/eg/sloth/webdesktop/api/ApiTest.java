package it.eg.sloth.webdesktop.api;

import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.api.model.BffFieldsProva;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
@Slf4j
class ApiTest {

    BffFieldsProva fieldsProva;

    @BeforeEach
    void init() {
        fieldsProva = new BffFieldsProva();
        fieldsProva.setTesto("description");
        fieldsProva.setNumero("10");
        fieldsProva.setData("2021-01-01");
    }

    @Test
    void BffFieldsTest() throws FrameworkException {
        assertEquals("description", fieldsProva.getString("testo"));
        assertEquals("10", fieldsProva.getString("numero"));
        assertEquals("2021-01-01", fieldsProva.getString("data"));
    }

}

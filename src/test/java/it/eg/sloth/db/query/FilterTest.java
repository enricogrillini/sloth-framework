package it.eg.sloth.db.query;

import it.eg.sloth.db.query.filteredquery.filter.OrFilter;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
class FilterTest {


    @Test
    void inputFieldTest() {
        OrFilter orFilter = new OrFilter("campo = ?", Types.VARCHAR, "a", "b");
        assertEquals("(campo = ? OR campo = ?)", orFilter.getWhereCondition());

        orFilter = new OrFilter("campo = ?", Types.VARCHAR, new ArrayList<>());
        assertNull(orFilter.getWhereCondition());

        orFilter = new OrFilter("campo = ?", Types.VARCHAR);
        assertNull(orFilter.getWhereCondition());

        orFilter = new OrFilter("campo = ?", Types.VARCHAR);
        assertNull(orFilter.getWhereCondition());
    }


}

package it.eg.sloth.db.query.filteredquery.filter;

import lombok.Data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
 * <p>
 *
 * @author Enrico Grillini
 */
@Data
public class BaseFilter implements Filter {

    private String sql;
    private int sqlTypes;
    private Object value;

    public BaseFilter(String sql, int sqlTypes, Object value) {
        this.sql = sql;
        this.sqlTypes = sqlTypes;
        this.value = value;
    }

    public String getWhereCondition() {
        if (getValue() != null && !"".equals(getValue())) {
            return getSql();
        } else {
            return "";
        }
    }

    public int addValues(PreparedStatement statement, int i) throws SQLException {
        if (getValue() != null && !"".equals(getValue())) {
            statement.setObject(i++, getValue(), getSqlTypes());
        }

        return i;
    }

}

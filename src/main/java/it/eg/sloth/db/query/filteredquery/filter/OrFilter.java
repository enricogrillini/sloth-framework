package it.eg.sloth.db.query.filteredquery.filter;

import lombok.Data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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
public class OrFilter implements Filter {

    int sqlTypes;
    List<?> values;
    String sql;

    public OrFilter(String sql, int sqlTypes, Object... values) {
        this.sql = sql;
        this.sqlTypes = sqlTypes;
        this.values = Arrays.asList(values);
    }


    public OrFilter(String sql, int sqlTypes, List<?> values) {
        this.sql = sql;
        this.sqlTypes = sqlTypes;
        this.values = values;
    }


    @Override
    public String getWhereCondition() {
        if (getValues() == null || getValues().isEmpty()) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(" OR ");
            }
            builder.append(getSql());
        }
        builder.append(")");

        return builder.toString();
    }

    @Override
    public int addValues(PreparedStatement statement, int i) throws SQLException {
        if (getValues() != null && !getValues().isEmpty()) {
            for (Object object : getValues()) {
                statement.setObject(i++, object, getSqlTypes());
            }
        }

        return i;
    }

}

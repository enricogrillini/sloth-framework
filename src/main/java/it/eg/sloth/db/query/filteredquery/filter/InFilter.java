package it.eg.sloth.db.query.filteredquery.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

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
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class InFilter implements Filter {

    int sqlTypes;
    Collection<?> values;
    String sql;
    boolean manageEmpty;

    public InFilter(String sql, int sqlTypes, Collection<?> values) {
        this(sql, sqlTypes, values, false);
    }

    public InFilter(String sql, int sqlTypes, Collection<?> values, boolean manageEmpty) {
        this.sql = sql;
        this.sqlTypes = sqlTypes;
        this.values = values;
        this.manageEmpty = manageEmpty;
    }

    @Override
    public String getWhereCondition() {
        if (getValues() == null) {
            return "";
        }

        if (getValues().isEmpty()) {
            return manageEmpty ? "1=2" : "";
        } else {
            StringBuilder inStatement = new StringBuilder(getSql() + " In (");
            for (int i = 0; i < getValues().size(); i++) {
                inStatement.append(i == 0 ? "?" : ", ?");
            }
            inStatement.append(")");

            return inStatement.toString();
        }
    }

    @Override
    public int addValues(PreparedStatement statement, int i) throws SQLException {
        if (getValues() != null) {
            for (Object object : getValues()) {
                statement.setObject(i++, object, getSqlTypes());
            }
        }
        return i;
    }

}

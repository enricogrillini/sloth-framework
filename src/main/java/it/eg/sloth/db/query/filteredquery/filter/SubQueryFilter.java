package it.eg.sloth.db.query.filteredquery.filter;

import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
@Setter
@Getter
public class SubQueryFilter implements Filter {

    FilteredQuery subQuery;

    public SubQueryFilter(FilteredQuery subQuery) {
        this.subQuery = subQuery;
    }

    @Override
    public String getWhereCondition() {
        return subQuery.getStatement();
    }

    @Override
    public int addValues(PreparedStatement statement, int i) throws SQLException {
        i = subQuery.addValues(statement, i);

        return i;
    }

}

package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@AllArgsConstructor
public class TextSearch extends FrameComponent implements Filter {

    String sql;
    String value;

    private String getSearchString() {
        if (value == null || value.equals(""))
            return "";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.toUpperCase().charAt(i);

            if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90)) {
                result.append(c);
            } else {
                result.append(' ');
            }
        }

        return result.toString();
    }

    @Override
    public String getWhereCondition() {
        StringBuilder result = new StringBuilder();
        List<String> list = StringUtil.words(getSearchString());

        for (int i = 0; i < list.size(); i++) {
            if (result.length() == 0) {
                result.append("(");
            } else {
                result.append(" And ");
            }
            result.append(getSql());
        }

        if (result.length() != 0) {
            result.append(")");
        }

        return result.toString();
    }

    @Override
    public int addValues(PreparedStatement statement, int i) throws SQLException {
        List<String> list = StringUtil.words(getSearchString());

        for (String string : list) {
            statement.setObject(i++, string, Types.VARCHAR);
        }

        return i;
    }

    public int getSqlTypes() {
        return Types.VARCHAR;
    }

    public int getParameterCount() {
        return StringUtil.words(getSearchString()).size();
    }

    public Object getParameterValue(int i) {
        return StringUtil.words(getSearchString()).get(i);
    }

}

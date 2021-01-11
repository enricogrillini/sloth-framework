package it.eg.sloth.db.query.query;

import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.db.query.SelectAbstractQuery;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
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
@Slf4j
public class Query extends SelectAbstractQuery implements SelectQueryInterface {

    private List<Parameter> parameterList;

    public Query(String statement) {
        super(statement);
        this.parameterList = new ArrayList<>();
    }

    @Override
    protected String getSqlStatement() {
        return getStatement();
    }

    @Override
    protected void initStatement(PreparedStatement statement) throws SQLException {
        int i = 1;
        for (Parameter parameter : parameterList) {
            statement.setObject(i++, parameter.getValue(), parameter.getSqlType());
        }
    }

    public void addParameter(int sqlTypes, Object value) {
        parameterList.add(new Parameter(sqlTypes, value));
    }

    public void execute() throws SQLException, FrameworkException {
        execute((Connection) null);
    }

    public void execute(String connectionName) throws SQLException, FrameworkException {
        try (Connection connection = DataConnectionManager.getInstance().getDataSource(connectionName).getConnection()) {
            execute(connection);
        }
    }

    public void execute(Connection connection) throws SQLException, FrameworkException {
        if (connection == null) {
            try (Connection newConnection = DataConnectionManager.getInstance().getDataSource().getConnection()) {
                execute(newConnection);
            }
        } else {
            log.debug("Start execute");
            log.debug(toString());

            try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                initStatement(preparedStatement);
                preparedStatement.executeUpdate();
            }

            log.debug("End execute");
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(super.toString());
        for (Parameter parameter : parameterList) {
            stringBuilder.append("\nParameter " + parameter.toString());
        }

        return stringBuilder.toString();
    }

}

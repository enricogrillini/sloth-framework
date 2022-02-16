package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class MultipleInput<T> extends InputField<List<T>> {

    public static final String DELIMITER = "|";

    protected MultipleInput(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }

    @Override
    public void copyToDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            dataSource.setString(getAlias(), valueToString(getValue()));
        }
    }

    @Override
    public void copyFromDataSource(DataSource dataSource) throws FrameworkException {
        if (dataSource != null) {
            setValue(valueFromString(dataSource.getString(getAlias())));
        }
    }

    @Override
    public void setValue(List<T> values) throws FrameworkException {
        if (values == null || values.isEmpty()) {
            setData(StringUtil.EMPTY);
        } else {
            StringBuilder valueBuilder = new StringBuilder();
            for (T value : values) {
                if (valueBuilder.length() > 0) {
                    valueBuilder.append(DELIMITER);
                }
                valueBuilder.append(getDataType().formatValue(value, getLocale(), getFormat()));
            }

            setData(valueBuilder.toString());
        }
    }

    public void setValue(DataTable<?> dataTable, String columnName) throws FrameworkException {
        List<T> list = new ArrayList<>();
        for (DataRow row : dataTable) {
            list.add((T) row.getObject(columnName));
        }

        setValue(list);
    }

    protected String valueToString(List<T> values) throws FrameworkException {
        if (values == null || values.isEmpty()) {
            return StringUtil.EMPTY;
        } else {
            StringBuilder valueBuilder = new StringBuilder();
            for (T value : values) {
                if (valueBuilder.length() > 0) {
                    valueBuilder.append(DELIMITER);
                }
                valueBuilder.append(getDataType().formatValue(value, getLocale(), getFormat()));
            }

            return valueBuilder.toString();
        }
    }

    protected List<T> valueFromString(String valueStr) throws FrameworkException {
        List<T> result = new ArrayList<>();
        if (!BaseFunction.isBlank(valueStr)) {
            for (String value : Arrays.asList(StringUtil.split(valueStr, DELIMITER))) {
                result.add((T) getDataType().parseValue(value, getLocale(), getFormat()));
            }
        }

        return result;
    }
}

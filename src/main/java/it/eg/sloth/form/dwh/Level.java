package it.eg.sloth.form.dwh;

import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.DecodedTextField;
import it.eg.sloth.framework.common.casting.DataTypes;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Level<T> extends DecodedTextField<T> {

    private Integer sortType;

    public Level(String name, String alias, String description, String tootip, DataTypes dataType, String format, String baseLink) {
        super(name, alias, description, tootip, dataType, format, baseLink);
        this.sortType = SortingRule.SORT_ASC_NULLS_LAST;
    }


    public int getSortType() {
        return sortType == null ? 0 : sortType;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.LEVEL;
    }

    @Override
    public Level<T> newInstance() {
        return toBuilder().build();
    }
}

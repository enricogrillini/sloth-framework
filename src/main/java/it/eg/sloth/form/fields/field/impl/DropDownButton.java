package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.webdesktop.api.request.BffFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder(toBuilder = true)
public class DropDownButton extends Link {

    List<DropDownItem> dropDownItemList;

    @Override
    public FieldType getFieldType() {
        return FieldType.DROPDOWN_BUTTON;
    }

    @Override
    public void post(BffFields bffFields) {
        // NOP
    }

    public DropDownButton newInstance() {
        return toBuilder().build();
    }

    @Data
    @AllArgsConstructor
    public static class DropDownItem {
        String code;
        String description;
        String icon;
    }
}

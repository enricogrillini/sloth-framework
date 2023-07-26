package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;

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
public class BootStrapClass {

    private BootStrapClass() {
        // NOP
    }

    private static final String CONTROL_CLASS = "form-control form-control-sm";
    private static final String VIEW_CONTROL_CLASS = "form-control form-control-sm bg-gray-200";


    private static final String BACKGROUND_PREFIX = "bg-";
    private static final String BORDER_PREFIX = "border border-";
    private static final String TEXT_PREFIX = "text-";

    // Control
    public static final String LABEL_CLASS = "col-form-label form-control-sm float-right pr-0";
    public static final String CHECK_CLASS = "custom-control-input";
    public static final String BUTTON_CLASS = "btn {0} btn-sm";
    public static final String DROP_DOWNBUTTON = "btn {0} btn-sm dropdown-toggle";

    public static final String getControlClass(DataField<?> dataField) {
        return new StringBuilder()
                .append(CONTROL_CLASS)
                .append(dataField.getFieldType() == FieldType.AUTO_COMPLETE ? " autoComplete" : "")
                .append(dataField.getFieldType() == FieldType.MULTIPLE_AUTO_COMPLETE ? " multipleAutoComplete" : "")
                .append(dataField.getFieldType() == FieldType.MULTIPLE_AUTO_COMPLETE && ((MultipleAutoComplete) dataField).isFreeInput() ? " freeInput" : "")
                .append(dataField.getState() == null ? StringUtil.EMPTY : StringUtil.SPACE + getStateClass(dataField.getState()))
                .toString();
    }

    public static final String getViewControlClass(DataField<?> dataField) {
        return new StringBuilder()
                .append(VIEW_CONTROL_CLASS)
                .append(dataField.getState() == null ? StringUtil.EMPTY : StringUtil.SPACE + getStateClass(dataField.getState()))
                .toString();
    }


    public static String getStateClass(ControlState controlState) {
        if (BaseFunction.isNull(controlState)) {
            return StringUtil.EMPTY;
        }

        if (controlState == ControlState.SUCCESS) {
            return "is-valid";
        } else if (controlState == ControlState.DANGER) {
            return "is-invalid";
        } else {
            return getStateBorderClass(controlState);
        }
    }

    public static String getStateBackgroundClass(ControlState controlState) {
        if (BaseFunction.isNull(controlState)) {
            return StringUtil.EMPTY;
        } else {
            return BACKGROUND_PREFIX + controlState.getBootstrapClass();
        }
    }

    public static String getStateBorderClass(ControlState controlState) {
        if (BaseFunction.isNull(controlState)) {
            return StringUtil.EMPTY;
        } else {
            return BORDER_PREFIX + controlState.getBootstrapClass();
        }
    }

    public static String getBootstrapTextClass(ControlState controlState) {
        if (BaseFunction.isNull(controlState)) {
            return StringUtil.EMPTY;
        } else {
            return TEXT_PREFIX + controlState.getBootstrapClass();
        }
    }
}

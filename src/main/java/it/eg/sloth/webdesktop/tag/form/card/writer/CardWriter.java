package it.eg.sloth.webdesktop.tag.form.card.writer;

import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.math.BigDecimal;
import java.text.MessageFormat;


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
 * Collezione di metodi statici per la scrittura delle Card
 *
 * @author Enrico Grillini
 */
public class CardWriter extends HtmlWriter {

    public static final String CARD_OPEN = ResourceUtil.normalizedResourceAsString("snippet/card/card-open.html");
    public static final String CARD_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/card/card-close.html");

    public static final String FIELDS_CARD_TITLE = ResourceUtil.normalizedResourceAsString("snippet/card/fields-card-title.html");
    public static final String FIELDS_CARD_ROW = ResourceUtil.normalizedResourceAsString("snippet/card/fields-card-row.html");

    private CardWriter() {
        // NOP
    }

    public static final String openCard() {
        return CARD_OPEN;
    }

    public static final String closeCard() {
        return CARD_CLOSE;
    }

    public static final String fieldCardContent(TextField<?> field) {
        StringBuilder result = new StringBuilder()
                .append("   <div class=\"col mr-2\"")
                .append(getAttributeTooltip(field.getTooltip()))
                .append(">\n")
                .append("    <div class=\"text-xs font-weight-bold text-primary text-uppercase mb-1\">" + field.getHtmlDescription() + CLOSE_DIV + "\n")
                .append("    <div class=\"row no-gutters align-items-center\">\n")
                .append("     <div class=\"col-auto\">\n")
                .append("      <div class=\"h5 mb-0 mr-3 font-weight-bold text-gray-800\">" + field.escapeHtmlText() + CLOSE_DIV + "\n")
                .append("     </div>\n")
                .append("    </div>\n")
                .append("   </div>\n");

        if (!BaseFunction.isBlank(field.getBaseLink())) {
            result.append("   <a href=\"" + field.getBaseLink() + field.escapeHtmlValue() + "\" class=\"stretched-link\"></a>\n");
        }

        return result.toString();
    }

    public static final String fieldsCardOpen(Fields<?> fields) throws FrameworkException {
        StringBuilder result = new StringBuilder()
                .append(MessageFormat.format(CARD_OPEN, Casting.getHtml(fields.getDescription())))
                .append(MessageFormat.format(FIELDS_CARD_TITLE, Casting.getHtml(fields.getDescription())));

        double min = getMinValue(fields);
        double max = getMaxValue(fields);
        double delta = max - min;
        for (DataField<?> dataField : fields.getDataFieldList()) {
            if (BaseFunction.in(dataField.getDataType(), DataTypes.DECIMAL, DataTypes.INTEGER, DataTypes.CURRENCY, DataTypes.PERC, DataTypes.NUMBER)) {
                if (delta == 0 || dataField.getValue() == null) {
                    result.append(MessageFormat.format(FIELDS_CARD_ROW, dataField.getHtmlDescription(), "", "0"));
                } else {
                    BigDecimal value = (BigDecimal) dataField.getValue();
                    double valuePerc = Math.round((value.doubleValue() - min) / delta * 100);
                    result.append(MessageFormat.format(FIELDS_CARD_ROW, dataField.getHtmlDescription(), dataField.escapeHtmlText(), String.valueOf(valuePerc)));
                }
            }
        }

        return result
                .append("   </div>")
                .toString();
    }

    private static double getMinValue(Fields<?> fields) throws FrameworkException {
        double min = 0;
        for (DataField<?> dataField : fields.getDataFieldList()) {
            if (BaseFunction.in(dataField.getDataType(), DataTypes.DECIMAL, DataTypes.INTEGER, DataTypes.CURRENCY, DataTypes.PERC, DataTypes.NUMBER)) {
                BigDecimal value = (BigDecimal) dataField.getValue();
                if (value != null && min > value.doubleValue()) {
                    min = value.doubleValue();
                }
            }
        }

        return min;
    }

    private static double getMaxValue(Fields<?> fields) throws FrameworkException {
        double max = 0;
        for (DataField<?> dataField : fields.getDataFieldList()) {
            if (BaseFunction.in(dataField.getDataType(), DataTypes.DECIMAL, DataTypes.INTEGER, DataTypes.CURRENCY, DataTypes.PERC, DataTypes.NUMBER)) {
                BigDecimal value = (BigDecimal) dataField.getValue();
                if (value != null && max < value.doubleValue()) {
                    max = value.doubleValue();
                }
            }
        }

        return max;
    }

}

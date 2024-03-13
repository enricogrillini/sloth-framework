package it.eg.sloth.webdesktop.tag.form.card.writer;

import it.eg.sloth.form.ControlState;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.math.BigDecimal;
import java.text.MessageFormat;


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
 * Collezione di metodi statici per la scrittura delle Card
 *
 * @author Enrico Grillini
 */
public class CardWriter extends HtmlWriter {

    public static final String CARD_OPEN = ResourceUtil.normalizedResourceAsString("snippet/card/card-open.html");
    public static final String CARD_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/card/card-close.html");

    public static final String COLLAPSIBLE_HEADER_OPEN = ResourceUtil.normalizedResourceAsString("snippet/card/collapsible-header-open.html");
    public static final String COLLAPSIBLE_HEADER_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/card/collapsible-header-close.html");
    public static final String COLLAPSIBLE_CARD_OPEN = ResourceUtil.normalizedResourceAsString("snippet/card/collapsible-card-open.html");
    public static final String COLLAPSIBLE_CARD_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/card/collapsible-card-close.html");

    public static final String FIELD_CARD_CONTENT = ResourceUtil.normalizedResourceAsString("snippet/card/field-card-content.html");
    public static final String PAIRED_FIELD_CARD_CONTENT = ResourceUtil.normalizedResourceAsString("snippet/card/paired-fields-card-row.html");

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


    public static final String collapsibleCardHeaderOpen(String name, String titolo, boolean collapsed) {
        return MessageFormat.format(COLLAPSIBLE_HEADER_OPEN,
                Casting.getHtml(name),
                Casting.getHtml(titolo),
                collapsed ? " collapsed" : StringUtil.EMPTY,
                collapsed ? " collapse" : StringUtil.EMPTY
        );
    }

    public static final String collapsibleCardHeaderClose() {
        return COLLAPSIBLE_HEADER_CLOSE;
    }

    public static final String collapsibleCardBodyOpen(String name, boolean collapsed) {
        return MessageFormat.format(COLLAPSIBLE_CARD_OPEN,
                Casting.getHtml(name),
                collapsed ? " collapse" : StringUtil.EMPTY
        );
    }

    public static final String collapsibleCardBodyClose() {
        return COLLAPSIBLE_CARD_CLOSE;
    }

    public static final String fieldCardContent(TextField<?> field) {
        StringBuilder result = new StringBuilder()
                .append(MessageFormat.format(FIELD_CARD_CONTENT, getTooltipAttributes(field.getTooltip()), field.getHtmlDescription(), field.escapeHtmlText()));

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
        double delta = max - min == 0 ? 1 : max - min;
        for (DataField<?> dataField : fields.getDataFieldList()) {
            if (BaseFunction.in(dataField.getDataType(), DataTypes.DECIMAL, DataTypes.INTEGER, DataTypes.CURRENCY, DataTypes.PERC, DataTypes.NUMBER)) {
                DataField<BigDecimal> field = (DataField<BigDecimal>) dataField;

                String value1 = field.escapeHtmlText();
                double valuePerc1 = Math.round((BigDecimalUtil.doubleValue(field.getValue()) - min) / delta * 100);
                String bgClass1 = BootStrapClass.getStateBackgroundClass((ControlState) BaseFunction.nvl(field.getState(), ControlState.DEFAULT));

                result.append(MessageFormat.format(FIELDS_CARD_ROW, field.getHtmlDescription(), value1, String.valueOf(valuePerc1), bgClass1));
            }
        }

        return result
                .append("   </div>")
                .toString();
    }

    public static final String pairedFieldsCardOpen(Fields<?> fields) throws FrameworkException {
        StringBuilder result = new StringBuilder()
                .append(MessageFormat.format(CARD_OPEN, Casting.getHtml(fields.getDescription())))
                .append(MessageFormat.format(FIELDS_CARD_TITLE, Casting.getHtml(fields.getDescription())));

        double min = getMinValue(fields);
        double max = getMaxValue(fields);
        double delta = max - min == 0 ? 1 : max - min;

        String value1 = "";
        double valuePerc1 = 0;
        String bgClass1 = "";

        int i = 0;
        for (DataField<?> dataField : fields.getDataFieldList()) {
            if (BaseFunction.in(dataField.getDataType(), DataTypes.DECIMAL, DataTypes.INTEGER, DataTypes.CURRENCY, DataTypes.PERC, DataTypes.NUMBER)) {
                DataField<BigDecimal> field = (DataField<BigDecimal>) dataField;

                if (i % 2 == 1) {
                    String value2 = field.escapeHtmlText();
                    double valuePerc2 = Math.round((BigDecimalUtil.doubleValue(field.getValue()) - min) / delta * 100);
                    String bgClass2 = BootStrapClass.getStateBackgroundClass((ControlState) BaseFunction.nvl(field.getState(), ControlState.DEFAULT));

                    result.append(MessageFormat.format(PAIRED_FIELD_CARD_CONTENT,
                            field.getHtmlDescription(),
                            value1, value2,
                            String.valueOf(valuePerc1), String.valueOf(valuePerc2 - valuePerc1),
                            bgClass1, bgClass2));

                } else {
                    value1 = field.escapeHtmlText();
                    valuePerc1 = Math.round((BigDecimalUtil.doubleValue(field.getValue()) - min) / delta * 100);
                    bgClass1 = BootStrapClass.getStateBackgroundClass((ControlState) BaseFunction.nvl(field.getState(), ControlState.DEFAULT));

                    if (i == fields.getDataFieldList().size() - 1) {
                        result.append(MessageFormat.format(FIELDS_CARD_ROW, field.getHtmlDescription(), value1, String.valueOf(valuePerc1), bgClass1));
                    }
                }
                i++;
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

package it.eg.sloth.framework.utility.xlsx.style;

import org.apache.poi.ss.usermodel.DataFormat;

import it.eg.sloth.framework.common.casting.DataTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BaseExcelType {

    public static final BaseExcelType INTEGER = new BaseExcelType("0");
    public static final BaseExcelType DECIMAL = new BaseExcelType("_-* #,##0.00_-;-* #,##0.00_-;_-* \"-\"??_-;_-@_-");
    public static final BaseExcelType CURRENCY = new BaseExcelType("€_-* #,##0.00_-;€-* #,##0.00_-;_-* \"-\"??_-;_-@_-");
    public static final BaseExcelType DATE = new BaseExcelType("dd/mm/yyyy");
    public static final BaseExcelType MONTH = new BaseExcelType("mm/yyyy");
    public static final BaseExcelType DATETIME = new BaseExcelType("dd/mm/yyyy hh:mm:ss");
    public static final BaseExcelType TIME = new BaseExcelType("hh:mm:ss");
    public static final BaseExcelType HOUR = new BaseExcelType("hh:mm");

    private String format;

    BaseExcelType(String format) {
        this.format = format;
    }

    /**
     * Ritorna il Format POI sulla base delle info disponibili
     *
     * @param dataFormat
     * @return
     */
    public short formatFromDataFormat(DataFormat dataFormat) {
        return dataFormat.getFormat(format);
    }

    public static class Factory {
        private Factory() {
            // NOP
        }

        /**
         * Ritorna il BaseExcelType corrispondente al DataType del framework
         *
         * @return
         */
        public static final BaseExcelType fromDataType(DataTypes dataTypes) {

            switch (dataTypes) {
                case DATE:
                    return DATE;

                case MONTH:
                    return MONTH;

                case DATETIME:
                    return DATETIME;

                case TIME:
                    return TIME;

                case HOUR:
                    return HOUR;

                case INTEGER:
                    return INTEGER;

                case DECIMAL:
                case PERC:
                    return DECIMAL;

                case CURRENCY:
                    return CURRENCY;

                case STRING:
                case MD:
                case MAIL:
                case PARTITA_IVA:
                case CODICE_FISCALE:
                    return null;

                default:
                    return null;
            }
        }

    }
}

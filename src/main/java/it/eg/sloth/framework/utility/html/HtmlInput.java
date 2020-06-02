package it.eg.sloth.framework.utility.html;

public class HtmlInput {
    private HtmlInput() {
        // NOP
    }

    // TimeStamp
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATETIME = "datetime-local";
    public static final String TYPE_TIME = "time";
    public static final String TYPE_HOUR = "time";
    public static final String TYPE_MONTH = "month";

    // BigDecimal
    public static final String TYPE_NUMBER = "text";
    public static final String TYPE_INTEGER = "number";
    public static final String TYPE_DECIMAL = "text";
    public static final String TYPE_CURRENCY = "text";
    public static final String TYPE_PERC = "text";

    // String
    public static final String TYPE_STRING = "text";
    public static final String TYPE_MD = "text";
    public static final String TYPE_MAIL = "text";
    public static final String TYPE_PARTITA_IVA = "text";
    public static final String TYPE_CODICE_FISCALE = "text";
    public static final String TYPE_URL = "text";
    public static final String TYPE_PASSWORD = "password";

}

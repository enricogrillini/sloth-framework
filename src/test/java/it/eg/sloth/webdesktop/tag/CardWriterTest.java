package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.junit.Test;

import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.tag.form.card.writer.CardWriter;

public class CardWriterTest {

    private static final String CONTENT_TEMPLATE =
            "   <div class=\"col mr-2\">\n" +
            "    <div class=\"text-xs font-weight-bold text-primary text-uppercase mb-1\">{0}</div>\n" +
            "    <div class=\"row no-gutters align-items-center\">\n" +
            "     <div class=\"col-auto\">\n" +
            "      <div class=\"h5 mb-0 mr-3 font-weight-bold text-gray-800\">{1}</div>\n" +
            "     </div>\n" +
            "     <!--div class=\"col\">\n" +
            "      <div class=\"progress progress-sm mr-2\">\n" +
            "       <div class=\"progress-bar bg-info\" role=\"progressbar\" style=\"width: 50%\"></div>\n" +
            "      </div>\n" +
            "     </div-->\n" +
            "    </div>\n" +
            "   </div>\n" +
            "{2}";

    @Test
    public void fieldCardContentTest() throws BusinessException {
        Text<BigDecimal> field = new Text<BigDecimal>("name", "description", "tooltip", DataTypes.INTEGER);
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "description", "",""), CardWriter.fieldCardContent(field));

        field.setValue(BigDecimal.valueOf(10));
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "description", "10",""), CardWriter.fieldCardContent(field));

        field.setBaseLink("www?");
        assertEquals(MessageFormat.format(CONTENT_TEMPLATE, "description", "10","   <a href=\"www?10\" class=\"stretched-link\"></a>\n"), CardWriter.fieldCardContent(field));
    }

}

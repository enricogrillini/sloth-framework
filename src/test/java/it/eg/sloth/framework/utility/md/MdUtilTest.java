package it.eg.sloth.framework.utility.md;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.utility.csv.BaseCsvReader;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MdUtilTest {


    private static final String CODE_INJECTION = "<script>alert(1);</script>";
    private static final String CODE_INJECTION_RESULT = "<p>&lt;script&gt;alert(1);&lt;/script&gt;</p>\n";

    private static final String LINK = "[Change log storico](WikiPage.html?codPagina=CHANGE-LOG-HISTORY)";
    private static final String LINK_RESULT = "<p><a href=\"WikiPage.html?codPagina=CHANGE-LOG-HISTORY\">Change log storico</a></p>\n";

    @Test
    void csvReaderTest() {
        assertEquals(CODE_INJECTION_RESULT, MdUtil.getHtml(CODE_INJECTION));
        assertEquals(LINK_RESULT, MdUtil.getHtml(LINK));
    }


}

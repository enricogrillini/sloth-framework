package it.eg.sloth.framework.utility.md;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MdUtil {

    private MdUtil() {
        // NOP
    }

    public static String getHtml(String mdData) {
        if (BaseFunction.isBlank(mdData)) {
            return StringUtil.EMPTY;
        }

        Parser parser = Parser.builder().build();
        Node document = parser.parse(mdData);
        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();

        return renderer.render(document);
    }
}

package it.eg.sloth.webdesktop.tag.form.rollup;

import java.io.IOException;

import it.eg.sloth.db.datasource.DataNode;
import it.eg.sloth.form.dwh.Attribute;
import it.eg.sloth.form.dwh.Level;
import it.eg.sloth.form.dwh.Measure;
import it.eg.sloth.form.dwh.rollup.Rollup;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.rollup.writer.RollupWriter;

public class RollupTag extends BaseElementTag<Rollup> {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_CLASS_NAME = "frRollup";
    public static final String GROUP_CLASS_NAME = "frGroup";
    public static final String SUM_CLASS_NAME = "frSum";
    public static final String TEXT_CLASS_NAME = "frText";

    private void writeHeader() throws IOException {
        writeln(" <tr>");
        writeln("  <th colspan=\"" + (getElement().getLevels().size() + getElement().getAttributes().size() + 1) + "\">&nbsp;</th>");

        for (Measure<?> measure : getElement().getMeasures()) {
            writeln("  <th>" + measure.getHtmlDescription() + "</th>");
        }

        writeln(" </tr>");
    }

    private void writeNode(DataNode node, int levelNumber) throws CloneNotSupportedException, BusinessException, IOException {
        Rollup rollup = getElement();

        if (node == null)
            return;

        if (levelNumber <= rollup.getLevels().size()) {
            int headerSize = getElement().getLevels().size() + getElement().getAttributes().size() + 1;

            writeln(" <tr class=\"" + GROUP_CLASS_NAME + levelNumber + "\">");
            if (levelNumber == 0) {
                writeln("  <td colspan=\"" + headerSize + "\">" + getElement().getHtmlDescription() + "</td>");
            } else {
                Level<?> levelClone = (Level<?>) getElement().getLevels().get(levelNumber - 1).clone();
                levelClone.copyFromDataSource(node);

                writeln("  <td colspan=\"" + levelNumber + "\"></td>");
                writeln("  <td colspan=\"" + (headerSize - levelNumber) + "\">" + levelClone.escapeHtmlText() + "</td>");
            }

            // Measure
            for (Measure<?> measure : rollup.getMeasures()) {
                Measure<?> measureClone = (Measure<?>) measure.clone();
                measureClone.copyFromDataSource(node);
                writeln("  <td style=\"text-align:right\">" + RollupWriter.writeMeasure(measureClone) + "</td>");
            }
            writeln(" </tr>");

            // Scrivo i figli
            for (DataNode child : node.getChilds()) {
                writeNode(child, levelNumber + 1);
            }

        } else {
            int levelSize = rollup.getLevels().size();
            int attributesSize = rollup.getAttributes().size();

            if (levelNumber < rollup.getLevels().size()) {
                writeln(" <tr class=\"" + GROUP_CLASS_NAME + levelNumber + "\">");

                for (int i = 0; i < levelNumber; i++) {
                    writeln("  <td class=\"span\">&nbsp;</td>");
                }

                int colspan = levelSize + attributesSize - levelNumber;
                Level<?> levelClone = (Level<?>) (rollup.getLevels().get(levelNumber)).clone();
                levelClone.copyFromDataSource(node);

                writeln("  <td colspan=\"" + colspan + "\">" + RollupWriter.writeLevel(levelClone) + "</td>");
                writeSum(node, levelNumber);

                writeln(" </tr>");

                writeNode(node, levelNumber + 1);

            } else {
                writeln(" <tr class=\"" + TEXT_CLASS_NAME + "\">");

                for (int i = 0; i < levelNumber; i++) {
                    writeln("  <td class=\"span\">&nbsp;</td>");
                }

                // Attribute
                for (Attribute<?> attribute : rollup.getAttributes()) {
                    Attribute<?> attributeClone = (Attribute<?>) attribute.clone();
                    attributeClone.copyFromDataSource(node);
                    writeln("  <td>" + RollupWriter.writeAttribute(attributeClone) + "</td>");
                }

                // Measure
                for (Measure<?> measure : rollup.getMeasures()) {
                    Measure<?> measureClone = (Measure<?>) measure.clone();
                    measureClone.copyFromDataSource(node);

                    writeln("  <td style=\"text-align:right\">" + RollupWriter.writeMeasure(measureClone) + "</td>");
                }

                writeln(" </tr>");

            }
        }

    }

    private void writeSum(DataNode node, int levelNumber) throws CloneNotSupportedException {
        Rollup rollup = getElement();

        if (node == null)
            return;

        if (rollup.getMeasures().size() > 0) {
            // writeln(" <tr class=\"" + SUM_CLASS_NAME + levelNumber + "\">");

            // // Level
            // if (rollup.getLevels().size() > 0)
            // writeln(" <td colspan=\"" + rollup.getLevels().size() +
            // "\">&nbsp;</td>");
            //
            // // Text
            // for (int i = 0; i < rollup.getAttributes().size(); i++) {
            // writeln(" <td>&nbsp;</td>");
            // }

            // writeln(" </tr>");
        }

    }

    protected int startTag() throws Throwable {
        writeln("<table class=\"frRollup\" cellspacing=\"0\">");

        writeHeader();
        writeNode(getElement().getDataNode(), 0);

        writeln("</table>");
        return SKIP_BODY;
    }

    protected void endTag() throws Throwable {
    }
}

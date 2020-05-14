package it.eg.sloth.framework.utility.mail.mailcomposer;

import java.io.IOException;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import it.eg.sloth.framework.utility.mail.mailcomposer.elements.EmbeddedImage;
import it.eg.sloth.framework.utility.mail.mailcomposer.elements.Table;
import it.eg.sloth.framework.utility.mail.mailcomposer.elements.TableCell;

/**
 * 
 * @author Enrico Grillini
 * 
 */
public class PrettyMailComposer extends MailComposer {

  

  private static final String BORDER_STYLE = "1px solid #003366";

  public PrettyMailComposer(String title) {
    super(title);
  }

  @Override
  protected void openMail(StringBuilder buffer) {
    buffer.append("<html>\n");
    buffer.append(" <meta http-equiv=\"content-type\" content=\"text/html; charset=iso-8859-1\">\n");
    buffer.append("<head><title>" + getTitle() + "</title></head>\n");
    buffer.append("<body>\n");
  }

  @Override
  protected void closeMail(StringBuilder buffer) {
    buffer.append("</body>\n");
    buffer.append("</html>\n");
  }

  @Override
  protected void openLine(StringBuilder buffer) {
    buffer.append("<div><font face=\"Arial, Helvetica, sans-serif\" color=\"#000000\" size=\"2\">\n");
  }

  @Override
  protected void closeLine(StringBuilder buffer) {
    buffer.append("</font></div>\n");
  }

  @Override
  protected void openBulletList(StringBuilder buffer) {
    buffer.append("<div><ul>\n");
  }

  @Override
  protected void closeBulletList(StringBuilder buffer) {
    buffer.append("</ul></div>\n");
  }

  @Override
  protected void openBullet(StringBuilder buffer) {
    buffer.append(" <li><font face=\"Arial, Helvetica, sans-serif\" color=\"#000000\" size=\"2\">\n");
  }

  @Override
  protected void closeBullet(StringBuilder buffer) {
    buffer.append(" </font></li>\n");
  }

  @Override
  protected void openTable(Table table, StringBuilder buffer) {
    String borderTop = table.getBorder()[0] ? "border-top: " + BORDER_STYLE + ";" : "";
    String borderRight = table.getBorder()[1] ? "border-right: " + BORDER_STYLE + ";" : "";
    String borderBottom = table.getBorder()[2] ? "border-bottom: " + BORDER_STYLE + ";" : "";
    String borderLeft = table.getBorder()[3] ? "border-left: " + BORDER_STYLE + ";" : "";

    buffer.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" style=\"" + borderTop + borderRight + borderBottom + borderLeft + "\">\n");
  }

  @Override
  protected void closeTable(StringBuilder buffer) {
    buffer.append("</table>\n");
  }

  @Override
  protected void openTableRow(StringBuilder buffer) {
    buffer.append(" <tr>\n");
  }

  @Override
  protected void closeTableRow(StringBuilder buffer) {
    buffer.append(" </tr>\n");
  }

  @Override
  protected void openTableCell(TableCell tableCell, StringBuilder buffer) {
    String colspan = tableCell.getColspan() == 1 ? "" : " colspan=\"" + tableCell.getColspan() + "\"";
    String borderTop = tableCell.getBorder()[0] ? "border-top: " + BORDER_STYLE + ";" : "";
    String borderRight = tableCell.getBorder()[1] ? "border-right: " + BORDER_STYLE + ";" : "";
    String borderBottom = tableCell.getBorder()[2] ? "border-bottom: " + BORDER_STYLE + ";" : "";
    String borderLeft = tableCell.getBorder()[3] ? "border-left: " + BORDER_STYLE + ";" : "";
    String alignRight = tableCell.isAlignRight() ? "text-align: right;" : "";
    
    buffer.append("  <td" + colspan + " style=\"" + borderTop + borderRight + borderBottom + borderLeft + alignRight + "\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=2 color=\"#003366\">\n");
  }

  @Override
  protected void closeTableCell(StringBuilder buffer) {
    buffer.append("</font></td>\n");
  }

  @Override
  protected void writeEmbededImage(EmbeddedImage embeddedImage, StringBuilder buffer, HtmlEmail htmlEmail) throws IOException, EmailException {
    DataSource dataSource = new ByteArrayDataSource(embeddedImage.getImage(), embeddedImage.getType());
    String cid = htmlEmail.embed(dataSource, embeddedImage.getDescription());

    if (embeddedImage.getLink() != null) {
      buffer.append("<a href=\"" + embeddedImage.getLink() + "\">");
    }
    buffer.append("<img src=\"cid:" + cid + "\">");
    if (embeddedImage.getLink() != null) {
      buffer.append("</a>");
    }
  }

}

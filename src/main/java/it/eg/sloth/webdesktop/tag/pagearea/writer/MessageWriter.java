package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.message.Level;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;

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
 *
 * @author Enrico Grillini
 */
public class MessageWriter {

    private static final String OPEN_DIALOG = "<div class=\"modal fade\" id=\"messageModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
            " <div class=\"modal-dialog modal-xl\" role=\"document\">\n" +
            "  <div class=\"modal-content\">\n" +
            "   <div class=\"modal-header\">\n" +
            "    <h5 class=\"modal-title\" id=\"exampleModalLabel\">{0}</h5>\n" +
            "    <button class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
            "     <span aria-hidden=\"true\">×</span>\n" +
            "    </button>\n" +
            "   </div>\n" +
            "   <div class=\"modal-body\">\n";

    private static final String CLOSE_DIALOG = "   </div>\n" +
            "   <div class=\"modal-footer\"><button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Ok</button></div>\n" +
            "  </div>\n" +
            " </div>\n" +
            "</div>";

    private MessageWriter() {
        // NOP
    }

    private static String getBootstrapClass(Level level) {
        switch (level) {
            case INFO:
                return "alert-primary";
            case SUCCESS:
                return "alert-success";
            case WARN:
                return "alert-warning";
            case ERROR:
                return "alert-danger";
            default:
                return StringUtil.EMPTY;
        }
    }

    private static String getTitle(Level level) {
        switch (level) {
            case INFO:
                return "Informazione!";
            case SUCCESS:
                return "Ok!";
            case WARN:
                return "Attenzione!";
            case ERROR:
                return "Errore!";
            default:
                return StringUtil.EMPTY;
        }
    }

    public static String openDialog(MessageList messageList) {
        String title = getTitle(messageList.getMaxSeverity());

        return MessageFormat.format(OPEN_DIALOG, title);
    }

    public static String closeDialog() {
        return CLOSE_DIALOG;
    }

    public static String writeMessages(MessageList messageList, boolean writeSeverity) {
        StringBuilder result = new StringBuilder();

        for (Message message : messageList.getList()) {
            result
                    .append("\n")
                    .append("    <div class=\"alert alert-dismissible " + getBootstrapClass(message.getSeverity()) + "\">\n");

            if (writeSeverity) {
                result.append("     <h4 class=\"alert-heading\">" + getTitle(message.getSeverity()) + "</h4>\n");
            }

            result.append("     <p class=\"mb-0  font-weight-bold\">" + Casting.getHtml(message.getDescription()) + "</p>\n");

            if (!BaseFunction.isBlank(message.getSubDescription())) {
                result.append("     <p class=\"mb-0\">" + Casting.getHtml(message.getSubDescription()) + "</p>\n");
            }

            result.append("    </div>\n");
        }

        return result.toString();
    }

}

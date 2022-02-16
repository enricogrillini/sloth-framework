package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.form.fields.field.impl.MultipleFile;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class FileWriter extends HtmlWriter {

    private static final String PDF_VIEW_EXISTS = "<embed src=\"./{0}?{1}=true\" class=\"h-100 w-100\"></embed>";
    private static final String PDF_VIEW_NOT_EXISTS = "<div class=\"alert alert-dismissible alert-info\">Documento non presente.</div>";

    private static final String FILE_OPEN_LIST = "<ul class=\"list-group small\">\n";

    private static final String FILE =
            " <li class=\"list-group-item d-flex justify-content-between align-items-center p-2\">\n" +
                    "  <span><button id=\"{1}\" name=\"{1}\" {3} class=\"btn btn-link text-primary btn-sm\"><i class=\"fas fa-download\"></i></button> {0}</span>\n" +
                    "  <button id=\"{2}\" name=\"{2}\" {3} class=\"btn btn-link text-danger btn-sm\" onclick=\"buttonConfirm(this);\" data-title=\"Conferma\" data-description=\"Confermi eliminazione?\"><i class=\"far fa-trash-alt\"></i></button>\n" +
                    " </li>\n";

    private static final String FILE_CLOSE_LIST = "</ul>\n" +
            "<br>";

    public static String writeFile(File file, ViewModality pageViewModality, String lastController) {
        if (file.isHidden()) {
            return StringUtil.EMPTY;
        }

        ViewModality viewModality = file.getViewModality() == ViewModality.AUTO ? pageViewModality : file.getViewModality();
        if (viewModality == ViewModality.VIEW) {
            switch (file.getHtmlFileType()) {
                case PDF:
                    if (file.exists()) {
                        return MessageFormat.format(PDF_VIEW_EXISTS, lastController, NavigationConst.navStr(NavigationConst.BUTTON, file.getName()));
                    } else {
                        return PDF_VIEW_NOT_EXISTS;
                    }

                case GENERIC:
                default:
                    return FormControlWriter.writeFile(file, pageViewModality);
            }
        } else {
            return FormControlWriter.writeFile(file, pageViewModality);
        }
    }

    public static String writeMultipleFile(MultipleFile multimpleFile, ViewModality pageViewModality) throws FrameworkException {
        if (multimpleFile.isHidden()) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder();
        ViewModality viewModality = multimpleFile.getViewModality() == ViewModality.AUTO ? pageViewModality : multimpleFile.getViewModality();

        if (multimpleFile.getValue().isEmpty()) {
            result.append(PDF_VIEW_NOT_EXISTS);
        } else {
            result.append(FILE_OPEN_LIST);
            int i = 0;
            for (String fileName : multimpleFile.getValue()) {
                result.append(MessageFormat.format(FILE,
                        fileName,
                        NavigationConst.navStr(NavigationConst.BUTTON, multimpleFile.getName() + "Download", "" + i),
                        NavigationConst.navStr(NavigationConst.BUTTON, multimpleFile.getName() + "Delete", "" + i),
                        viewModality == ViewModality.VIEW ? "" : "disabled=\"disabled\""
                ));

                i++;
            }
            result.append(FILE_CLOSE_LIST);
        }

        if (viewModality == ViewModality.EDIT) {
            result.append(FormControlWriter.writeMultipleFile(multimpleFile, pageViewModality));
        }

        return result.toString();
    }


}

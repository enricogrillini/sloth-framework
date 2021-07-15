package it.eg.sloth.webdesktop.tag.form.field.writer;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.fields.field.impl.File;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class FileWriter extends HtmlWriter {

    private static final String PDF_VIEW_EXISTS = "<embed src=\"./{0}?{1}=true\" class=\"h-100 w-100\"></embed>";
    private static final String PDF_VIEW_NOT_EXISTS = "<div class=\"alert alert-dismissible alert-info\">Documento non presente.</div>";

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

}

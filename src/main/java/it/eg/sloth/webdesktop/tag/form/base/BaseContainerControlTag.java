package it.eg.sloth.webdesktop.tag.form.base;

import java.io.IOException;
import java.text.ParseException;

import it.eg.sloth.form.fields.field.base.AbstractSimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseContainerControlTag extends BaseControlTag {

    private static final long serialVersionUID = 1L;

    String controlWidth;
    String controlStyle;
    String controlClassname;
    String labelWidth;
    String labelStyle;
    String labelClassname;

    public void writeLabelContainer(AbstractSimpleField field) throws ParseException, BusinessException, IOException {
        if (field != null && FormControlWriter.hasLabel(field)) {
            write(GroupWriter.openCell(null, null, getLabelWidth()));
            write(FormControlWriter.writeLabel(field, getViewModality(), getControlClassname(), getControlStyle()));
            write(GroupWriter.closeCell());
        } else {
            write(GroupWriter.openCell(null, null, getLabelWidth()));
            write("<span class=\"form-control border-bottom-danger\">Campo " + getName() + " non trovato</span>");
            write(GroupWriter.closeCell());
        }
    }

    public void writeControlContainer(AbstractSimpleField field) throws BusinessException, IOException {
        write(GroupWriter.openCell(null, null, getControlWidth()));
        if (field != null) {

            ViewModality viewModality = getViewModality();
            if (field instanceof InputField) {
                InputField<?> inputField = (InputField<?>) field;
                viewModality = inputField.getViewModality() == ViewModality.VIEW_AUTO ? viewModality : inputField.getViewModality();
            }

            if (ViewModality.VIEW_VISUALIZZAZIONE == viewModality) {
                switch (field.getFieldType()) {

                    default:
                        write(FormControlWriter.writeControl(field, getParentElement(), getWebDesktopDto().getLastController(), getViewModality(), getControlClassname(), getControlStyle()));
                        break;
                }
            } else {
                write(FormControlWriter.writeControl(field, getParentElement(), getWebDesktopDto().getLastController(), getViewModality(), getControlClassname(), getControlStyle()));
            }

        } else {
            write("<span class=\"form-control border-bottom-danger\">Campo " + getName() + " non trovato</span>");
        }
        write(GroupWriter.closeCell());
    }

}

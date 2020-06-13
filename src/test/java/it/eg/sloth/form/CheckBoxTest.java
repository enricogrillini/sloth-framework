package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.CheckBox;
import it.eg.sloth.framework.common.casting.DataTypes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckBoxTest {

    @Test
    public void buttonTest() {
        CheckBox<String> button = new CheckBox<String>("name", "description", DataTypes.STRING);
        assertEquals(CheckBox.DEFAULT_VAL_CHECKED, button.getValChecked());
        assertEquals(CheckBox.DEFAULT_VAL_UN_CHECKED, button.getValUnChecked());

        button.setValChecked("1");
        button.setValUnChecked("2");
        assertEquals("1", button.getValChecked());
        assertEquals("2", button.getValUnChecked());
    }


}

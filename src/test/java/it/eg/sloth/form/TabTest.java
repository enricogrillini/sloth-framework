package it.eg.sloth.form;

import it.eg.sloth.form.tabsheet.Tab;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TabTest {

    @Test
    public void tabTest() {
        Tab tab = new Tab("name", "description", false, false);
        assertFalse(tab.isHidden());
        assertFalse(tab.isDisabled());

        tab = new Tab("name", "description", true, true);
        assertTrue(tab.isHidden());
        assertTrue(tab.isDisabled());

        tab = new Tab("name", "description", null, null);
        assertFalse(tab.isHidden());
        assertFalse(tab.isDisabled());
    }
}

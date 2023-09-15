package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
class ButtonTest {


    private Button field;

    @BeforeEach
    void init() {
        field = Button.builder()
                .name("Name")
                .description("description")
                .build();
    }

    @Test
    void buttonTest() {
        Button button = new Button("name", "description");
        assertEquals("name", button.getName());
        assertEquals(Locale.getDefault(), button.getLocale());
        assertFalse(button.isHidden());
        assertFalse(button.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY, button.getButtonType());
    }

    @Test
    void builderDefault() {
        assertEquals("name", field.getName());
        assertEquals("description", field.getDescription());
        assertEquals(Locale.getDefault(), field.getLocale());
        assertFalse(field.isHidden());
        assertFalse(field.isDisabled());
        assertFalse(field.isLoading());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY, field.getButtonType());
    }


    @Test
    void builderExplicitFalse() {
        // False
        Button button = Button.builder()
                .name("Name")
                .locale(Locale.ITALY)
                .description("description")
                .tooltip("tooltip")
                .hidden(false)
                .disabled(false)
                .loading(false)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", button.getName());
        assertEquals(Locale.ITALY, button.getLocale());
        assertFalse(button.isHidden());
        assertFalse(button.isDisabled());
        assertFalse(button.isLoading());
        assertEquals(ButtonType.BTN_INFO, button.getButtonType());
    }

    @Test
    void builderExplicitTrue() {
        Button button = Button.builder()
                .name("Name")
                .locale(Locale.ITALY)
                .description("description")
                .tooltip("tooltip")
                .hidden(true)
                .disabled(true)
                .loading(true)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", button.getName());
        assertEquals(Locale.ITALY, button.getLocale());
        assertTrue(button.isHidden());
        assertTrue(button.isDisabled());
        assertTrue(button.isLoading());
        assertEquals(ButtonType.BTN_INFO, button.getButtonType());
    }

    @Test
    void newInstance() {
        Button button2 = field.newInstance();
        assertNotSame(field, button2);
        assertEquals(field.getName(), button2.getName());
    }
}

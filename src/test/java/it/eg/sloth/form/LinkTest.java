package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.Link;
import it.eg.sloth.jaxb.form.ButtonType;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

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
 *
 * @author Enrico Grillini
 */
class LinkTest {

    @Test
    void linkTest() {
        Link link = new Link("name", "description", "href");
        assertEquals("name", link.getName());
        assertEquals(Locale.getDefault(), link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY, link.getButtonType());
    }

    @Test
    void linkBuilderTest() {
        Link link = Link.builder()
                .name("Name")
                .description("description")
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.getDefault(), link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertEquals(ButtonType.BTN_OUTLINE_PRIMARY, link.getButtonType());

        link = Link.builder()
                .name("Name")
                .description("description")
                .locale(Locale.ITALY)
                .tooltip("tooltip")
                .hidden(false)
                .disabled(false)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.ITALY, link.getLocale());
        assertFalse(link.isHidden());
        assertFalse(link.isDisabled());
        assertEquals(ButtonType.BTN_INFO, link.getButtonType());

        link = Link.builder()
                .name("Name")
                .description("description")
                .locale(Locale.ITALY)
                .tooltip("tooltip")
                .hidden(true)
                .disabled(true)
                .buttonType(ButtonType.BTN_INFO)
                .build();

        assertEquals("name", link.getName());
        assertEquals(Locale.ITALY, link.getLocale());
        assertTrue(link.isHidden());
        assertTrue(link.isDisabled());
        assertEquals(ButtonType.BTN_INFO, link.getButtonType());

        // NewInstance
        Link link2 = link.newInstance();
        assertNotSame(link, link2);
        assertEquals(link.getName(), link2.getName());
    }
}

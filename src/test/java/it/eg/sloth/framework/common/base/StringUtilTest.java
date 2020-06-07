package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
public class StringUtilTest {

    @Test
    public void rtrimTest() throws ParseException {
        assertEquals(StringUtil.EMPTY, StringUtil.rtrim(""));
        assertEquals(StringUtil.EMPTY, StringUtil.rtrim(null));

        assertEquals("aaa", StringUtil.rtrim("aaa    "));
        assertEquals("   aaa", StringUtil.rtrim("   aaa    "));
    }

    @Test
    public void ltrimTest() throws ParseException {
        assertEquals(StringUtil.EMPTY, StringUtil.ltrim(""));
        assertEquals(StringUtil.EMPTY, StringUtil.ltrim(null));

        assertEquals("aaa", StringUtil.ltrim("    aaa"));
        assertEquals("aaa    ", StringUtil.ltrim("   aaa    "));
    }

    @Test
    public void toFileNameTest() throws ParseException {
        assertEquals("aaa-bbb", StringUtil.toFileName("aaa/\\ bbb"));
    }

    @Test
    public void replaceTest() {
        assertEquals(StringUtil.EMPTY, StringUtil.replace(null, "\"", "\\\""));
        assertEquals(StringUtil.EMPTY, StringUtil.replace("", "\"", "\\\""));
        assertEquals(">>\\\"<<", StringUtil.replace(">>\"<<", "\"", "\\\""));
    }

    @Test
    public void bigDecimalFormatValueTest() throws FrameworkException {
        List<String> words = Arrays.asList("Bob", "Alice");

        assertTrue(StringUtil.containsAllWords("Bob and Alice", words));
        assertTrue(StringUtil.containsAllWords("Alice and Bob", words));
        assertFalse(StringUtil.containsAllWords("Only Bob ", words));
        assertFalse(StringUtil.containsAllWords("", words));
    }

    @Test
    public void partitaMailTestOk() throws FrameworkException {
        assertEquals(null, StringUtil.parseMail(null));
        assertEquals(null, StringUtil.parseMail(""));

        assertEquals("prova@mail.com", StringUtil.parseMail("prova@mail.com"));
    }

    @Test
    public void partitaMailTestKo() throws FrameworkException {
        FrameworkException frameworkException;

        // Mail non valida
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseMail("@@");
        });
        assertEquals("Impossibile validare il valore passato - Indirizzo email errato", frameworkException.getMessage());

        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseMail("mail.com");
        });
        assertEquals("Impossibile validare il valore passato - Indirizzo email errato", frameworkException.getMessage());
    }

    @Test
    public void codiceFiscaleTestOk() throws FrameworkException {
        // Empty
        assertEquals(null, StringUtil.parseCodiceFiscale(null));
        assertEquals(null, StringUtil.parseCodiceFiscale(""));

        // Valid
        assertEquals("RSSMRA80A01A944I", StringUtil.parseCodiceFiscale("RSSMRA80A01A944I"));
        assertEquals("BNCMRA65A01A944P", StringUtil.parseCodiceFiscale("BNCMRA65A01A944P"));
    }

    @Test
    public void codiceFiscaleTestKo() throws FrameworkException {
        FrameworkException frameworkException;

        // Lunghezza codice fiscale errata
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseCodiceFiscale("@@@");
        });
        assertEquals("Impossibile validare il valore passato - Lunghezza codice fiscale errata", frameworkException.getMessage());

        // Lunghezza codice fiscale errata
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseCodiceFiscale("RSSMRA--A01A944H");
        });
        assertEquals("Impossibile validare il valore passato - Trovati caratteri non validi", frameworkException.getMessage());


        // Lunghezza codice fiscale errata
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseCodiceFiscale("RSSMRA80A01A944H");
        });
        assertEquals("Impossibile validare il valore passato - Codice di controllo non valido", frameworkException.getMessage());

    }


    @Test
    public void partitaIvaTestOk() throws FrameworkException {
        // Empty
        assertEquals(null, StringUtil.parsePartitaIva(null));
        assertEquals(null, StringUtil.parsePartitaIva(""));

        // Valid
        assertEquals("00000000000", StringUtil.parsePartitaIva("00000000000"));
        assertEquals("44444444440", StringUtil.parsePartitaIva("44444444440"));
        assertEquals("12345678903", StringUtil.parsePartitaIva("12345678903"));
        assertEquals("74700694370", StringUtil.parsePartitaIva("74700694370"));
        assertEquals("57636564049", StringUtil.parsePartitaIva("57636564049"));
        assertEquals("19258897628", StringUtil.parsePartitaIva("19258897628"));
        assertEquals("08882740981", StringUtil.parsePartitaIva("08882740981"));
        assertEquals("47309842806", StringUtil.parsePartitaIva("4730 9842  806"));
    }

    @Test
    public void partitaIvaTestKo() throws FrameworkException {
        FrameworkException frameworkException;

        // Lunghezza partita iva errata
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parsePartitaIva("@@@");
        });
        assertEquals("Impossibile validare il valore passato - Lunghezza partita iva errata", frameworkException.getMessage());


        // Caratteri non validi
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parsePartitaIva("00000+00000");
        });
        assertEquals("Impossibile validare il valore passato - Trovati caratteri non validi", frameworkException.getMessage());

        // Codice di controllo non valido
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parsePartitaIva("00000000001");
        });
        assertEquals("Impossibile validare il valore passato - Codice di controllo non valido", frameworkException.getMessage());
    }

}

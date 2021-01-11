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
        assertEquals("Dal_01-01-2020_al_31-01-2020", StringUtil.toFileName("Dal 01/01/2020 al 31/01/2020"));
        assertEquals("01-01-2020_31-01-2020", StringUtil.toFileName("01/01/2020 31/01/2020"));
        assertEquals("01-01-2020_31-01-2020", StringUtil.toFileName("01/01/2020 - 31/01/2020"));
        assertEquals("aaa_bbb", StringUtil.toFileName("aaa/\\ bbb"));
    }

    @Test
    public void replaceTest() {
        assertEquals(StringUtil.EMPTY, StringUtil.replace(null, "\"", "\\\""));
        assertEquals(StringUtil.EMPTY, StringUtil.replace("", "\"", "\\\""));
        assertEquals(">>\\\"<<", StringUtil.replace(">>\"<<", "\"", "\\\""));
    }

    @Test
    public void containsAllWordsTest() throws FrameworkException {
        List<String> words = Arrays.asList("Bob", "Alice");

        assertTrue(StringUtil.containsAllWords("Bob and Alice", words));
        assertTrue(StringUtil.containsAllWords("Alice and Bob", words));
        assertFalse(StringUtil.containsAllWords("Only Bob ", words));
        assertFalse(StringUtil.containsAllWords("", words));
    }

    @Test
    public void toJavaConstantNameTest() {
        assertEquals(StringUtil.EMPTY, StringUtil.toJavaConstantName(null));
        assertEquals("PROVA", StringUtil.toJavaConstantName("prova"));
        assertEquals("PROVA_PROVA", StringUtil.toJavaConstantName("prova_prova"));
        assertEquals("PROVA_PROVA", StringUtil.toJavaConstantName("provaProva"));
    }

    @Test
    public void toFileName() {
        assertEquals(StringUtil.EMPTY, StringUtil.toFileName(null));
        assertEquals("prova", StringUtil.toFileName("prova"));
        assertEquals("prova-prova", StringUtil.toFileName("prova/prova"));
        assertEquals("provaProva", StringUtil.toFileName("provaProva"));
    }

    @Test
    public void parseStringTest() throws FrameworkException {
        assertEquals(null, StringUtil.parseString(null));
        assertEquals(null, StringUtil.parseString(""));
        assertEquals("aaaa", StringUtil.parseString("aaaa"));
    }

    @Test
    public void parseMailOkTest() throws FrameworkException {
        assertEquals(null, StringUtil.parseMail(null));
        assertEquals(null, StringUtil.parseMail(""));

        assertEquals("alice@example.com", StringUtil.parseMail("alice@example.com"));
        assertEquals("alice.bob@example.co.in", StringUtil.parseMail("alice.bob@example.co.in"));
        assertEquals("alice#@example.me.org", StringUtil.parseMail("alice#@example.me.org"));
    }

    @Test
    public void parseMailKoTest() throws FrameworkException {
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

        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseMail("alice.example.com");
        });
        assertEquals("Impossibile validare il valore passato - Indirizzo email errato", frameworkException.getMessage());

        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseMail("@example.me.org");
        });
        assertEquals("Impossibile validare il valore passato - Indirizzo email errato", frameworkException.getMessage());
    }

    @Test
    public void codiceFiscaleOkTest() throws FrameworkException {
        // Empty
        assertEquals(null, StringUtil.parseCodiceFiscale(null));
        assertEquals(null, StringUtil.parseCodiceFiscale(""));

        // Valid
        assertEquals("PPRPLN80A01A944I", StringUtil.parseCodiceFiscale("PPRPLN80A01A944I"));
        assertEquals("BNCMRA65A01A944P", StringUtil.parseCodiceFiscale("BNCMRA65A01A944P"));
    }

    @Test
    public void codiceFiscaleKoTest() throws FrameworkException {
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


        // Codice di controllo non valido
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseCodiceFiscale("RSSMRA80A01A944H");
        });
        assertEquals("Impossibile validare il valore passato - Codice di controllo non valido", frameworkException.getMessage());

        // Casi standard di invalidità
        frameworkException = assertThrows(FrameworkException.class, () -> {
            StringUtil.parseCodiceFiscale("XXXXXXXXXXXXXXXX");
        });
        assertEquals("Impossibile validare il valore passato - Trovati caratteri non validi", frameworkException.getMessage());
    }


    @Test
    public void partitaIvaOkTest() throws FrameworkException {
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
    public void partitaIvaKoTest()  {
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

    @Test
    public void tokenizeTest () {

        assertEquals(4 , StringUtil.split(" aaa , bbb ,ccc,ddd", ",").length);
        assertEquals("aaa" , StringUtil.split(" aaa , bbb ,ccc,ddd", ",")[0]);
        assertEquals("bbb" , StringUtil.split(" aaa , bbb ,ccc,ddd", ",")[1]);
        assertEquals("ccc" , StringUtil.split(" aaa , bbb ,ccc,ddd", ",")[2]);
        assertEquals("ddd" , StringUtil.split(" aaa , bbb ,ccc,ddd", ",")[3]);

    }

}

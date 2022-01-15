package it.eg.sloth.db;

import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.SearchType;
import it.eg.sloth.db.decodemap.map.BaseDecodeMap;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.db.decodemap.value.BaseDecodeValue;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecodeMapTest {

    @Test
    void decodeMapTest() {
        StringDecodeMap decodeMap = new StringDecodeMap("A,Prova testo 1;B,Prova testo 2;C,Prova testo 3;D,Prova testo test 4");

        // Exact Search
        assertEquals(0, decodeMap.performSearch("prova", SearchType.EXACT, 10).size());
        assertEquals(0, decodeMap.performSearch("Prova testo 1   ", SearchType.EXACT, 10).size());
        assertEquals(1, decodeMap.performSearch("Prova testo 1", SearchType.EXACT, 10).size());

        // Flexible Search
        assertEquals(0, decodeMap.performSearch("prova", SearchType.IGNORE_CASE, 10).size());
        assertEquals(1, decodeMap.performSearch("    prova testo 1     ", SearchType.IGNORE_CASE, 10).size());
        assertEquals(1, decodeMap.performSearch("Prova testo 1", SearchType.IGNORE_CASE, 10).size());

        // Match - Contains all Words
        assertEquals(4, decodeMap.performSearch("prova", SearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("    prova testo 1     ", SearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("Prova testo 1", SearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("testo 1 Prova", SearchType.MATCH, 10).size());
        assertEquals(2, decodeMap.performSearch("prova", SearchType.MATCH, 2).size());

        // Match - Pattern match
        assertEquals(4, decodeMap.performSearch("*test*test*", SearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("* 1", SearchType.MATCH, 10).size());

        // Empty
        assertEquals(0, decodeMap.performSearch("", SearchType.MATCH, 10).size());
    }

    @Test
    void decodeMapOrderTest() {
        StringDecodeMap decodeMap = new StringDecodeMap("A,XXX match YYY;B,match YYY;C,match;");

        Iterator<BaseDecodeValue<String>> iterator = decodeMap.performSearch("match", SearchType.MATCH, 10).iterator();
        assertEquals("B", iterator.next().getCode());
        assertEquals("C", iterator.next().getCode());
        assertEquals("A", iterator.next().getCode());

        iterator = decodeMap.performSearch("*match", SearchType.MATCH, 10).iterator();
        assertEquals("C", iterator.next().getCode());
        assertEquals("A", iterator.next().getCode());
        assertEquals("B", iterator.next().getCode());
    }


    @Test
    void decodeMapOrderValidTest() {
        BaseDecodeMap<String> decodeMap = new BaseDecodeMap<>();
        decodeMap.put("aaa-1", "aaa-1", false);
        decodeMap.put("aaa-2", "aaa-2", false);
        decodeMap.put("aaa-3", "aaa-3", true);

        Iterator<BaseDecodeValue<String>> iterator = decodeMap.performSearch("aaa", SearchType.MATCH, 10).iterator();
        assertEquals("aaa-3", iterator.next().getCode());
        assertEquals("aaa-1", iterator.next().getCode());
        assertEquals("aaa-2", iterator.next().getCode());
    }
}



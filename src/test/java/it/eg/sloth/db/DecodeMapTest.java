package it.eg.sloth.db;

import it.eg.sloth.db.decodemap.SearchType;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import org.junit.jupiter.api.Test;

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
        assertEquals(1, decodeMap.performSearch("*test*test*", SearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("* 1", SearchType.MATCH, 10).size());

        // Empty
        assertEquals(0, decodeMap.performSearch("", SearchType.MATCH, 10).size());
    }
}

package it.eg.sloth.db;

import it.eg.sloth.db.decodemap.MapSearchType;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DecodeMapTest {

    @Test
    public void decodeMapTest() {
        StringDecodeMap decodeMap = new StringDecodeMap("A,Prova testo 1;B,Prova testo 2;C,Prova testo 3");

        // Exact Search
        assertEquals(0, decodeMap.performSearch("prova", MapSearchType.EXACT, 10).size());
        assertEquals(0, decodeMap.performSearch("Prova testo 1   ", MapSearchType.EXACT, 10).size());
        assertEquals(1, decodeMap.performSearch("Prova testo 1", MapSearchType.EXACT, 10).size());

        // Flexible Search
        assertEquals(0, decodeMap.performSearch("prova", MapSearchType.FLEXIBLE, 10).size());
        assertEquals(1, decodeMap.performSearch("    prova testo 1     ", MapSearchType.FLEXIBLE, 10).size());
        assertEquals(1, decodeMap.performSearch("Prova testo 1", MapSearchType.FLEXIBLE, 10).size());

        // Match
        assertEquals(3, decodeMap.performSearch("prova", MapSearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("    prova testo 1     ", MapSearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("Prova testo 1", MapSearchType.MATCH, 10).size());
        assertEquals(1, decodeMap.performSearch("testo 1 Prova", MapSearchType.MATCH, 10).size());
    }
}

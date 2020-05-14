package it.eg.sloth.db.decodemap.map;

import it.eg.sloth.db.decodemap.value.BaseDecodeValue;

/**
 * @author Enrico Grillini
 * 
 */
public class StringDecodeMap extends BaseDecodeMap<String> {

  

  public StringDecodeMap(String string) {
    super();
    if (string == null || string.equals("")) {
      return;
    }

    String array[] = string.split(";");
    for (int i = 0; i < array.length; i++) {
      String array2[] = array[i].split(",");

      put(new BaseDecodeValue<String>(array2[0], array2[1]));
    }

  }
}

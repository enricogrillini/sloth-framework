package it.eg.sloth.db.decodemap;

public interface DecodeValue<T extends Object>  {
  
  public static String DEFAULT_CODE_NAME = "codice";
  public static String DEFAULT_DESCRIPTION_NAME = "descrizione";
  public static String DEFAULT_VALID_NAME = "flagvalido";

  public T getCode();
  
  public String getDescription();
  
  public boolean isValid();

  public boolean match(String matchString);
  
}

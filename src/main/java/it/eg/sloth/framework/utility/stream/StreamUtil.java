package it.eg.sloth.framework.utility.stream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

/**
 * 
 * @author Enrico Grillini
 *
 */
public class StreamUtil {

  public static int DEFAUL_BUFFER_SIZE = 4096;

  /**
   * Ritorna il BufferedReader dall' InputStream
   * 
   * @param inputStream
   * @return
   */
  public static BufferedReader inputStreamToReader(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  /**
   * Travasa il byte array passato nell'outputStream
   * 
   * @param buffer
   * @param outputStream
   * @throws IOException
   */
  public static final void byteArrayToOutputStream(byte[] buffer, OutputStream outputStream) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(buffer);
    try {
      StreamUtil.inputStreamToOutputStream(inputStream, outputStream);
    } finally {
      inputStream.close();
    }
  }

  /**
   * Legge l'InputStream e lo copia sull'OutputStream
   * 
   * @param inputStream
   * @param outputStream
   * @param bufferSize
   * @throws IOException
   */
  public static void inputStreamToOutputStream(InputStream inputStream, OutputStream outputStream, int bufferSize) throws IOException {
    byte buffer[] = new byte[bufferSize];
    int j;

    while ((j = inputStream.read(buffer)) > 0) {
      outputStream.write(buffer, 0, j);
      outputStream.flush();
    }
  }

  /**
   * Legge l'InputStream e lo copia sull'OutputStream
   * 
   * @param inputStream
   * @param outputStream
   * @throws IOException
   */
  public static void inputStreamToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
    inputStreamToOutputStream(inputStream, outputStream, DEFAUL_BUFFER_SIZE);
  }

  /**
   * Legge l'InputStream e lo copia sull'OutputStream
   * 
   * @param inputStream
   * @param outputStream
   * @throws IOException
   */
  public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      inputStreamToOutputStream(inputStream, outputStream, DEFAUL_BUFFER_SIZE);
      return outputStream.toByteArray();

    } finally {
      outputStream.close();
    }
  }

  /**
   * Legge lo StringBuilder e lo copia sull'OutputStream
   * 
   * @param stringBuilder
   * @param outputStream
   * @throws IOException
   */
  public static void stringBuilderToOutputStream(StringBuilder stringBuilder, OutputStream outputStream) throws IOException {
    stringToOutputStream(stringBuilder.toString(), outputStream);
  }

  /**
   * Legge la Stringa e la copia sull'OutputStream
   * 
   * @param string
   * @param outputStream
   * @throws IOException
   */
  public static void stringToOutputStream(String string, OutputStream outputStream) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(string.getBytes());
    try {
      inputStreamToOutputStream(byteArrayInputStream, outputStream, DEFAUL_BUFFER_SIZE);
    } finally {
      byteArrayInputStream.close();
    }
  }

  public static StringBuilder readerToStringBuilder(Reader reader) throws IOException {
    return readerToStringBuilder(reader, DEFAUL_BUFFER_SIZE);
  }

  public static StringBuilder readerToStringBuilder(Reader reader, int bufferSize) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    char buffer[] = new char[bufferSize];
    int j;

    while ((j = reader.read(buffer)) > 0) {
      stringBuilder.append(buffer, 0, j);
    }

    return stringBuilder;
  }

}

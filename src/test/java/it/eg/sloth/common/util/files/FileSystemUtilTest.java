package it.eg.sloth.common.util.files;

import static org.junit.Assert.assertNotNull;

import java.net.URISyntaxException;

import org.junit.Test;

import it.eg.sloth.framework.utility.files.FileSystemUtil;

public class FileSystemUtilTest {

  @Test
  public void getFileFromContextOk() throws URISyntaxException {
    assertNotNull(FileSystemUtil.getFileFromContext("system.xml"));
  }

  @Test(expected = NullPointerException.class)
  public void getFileFromContextKo() throws URISyntaxException {
    FileSystemUtil.getFileFromContext("aaaa");
  }

}

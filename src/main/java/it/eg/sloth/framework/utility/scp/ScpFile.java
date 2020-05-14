package it.eg.sloth.framework.utility.scp;

public class ScpFile {
  private byte[] buffer;
  private String remoteFile;

  public ScpFile(byte[] buffer, String remoteFile) {
    this.buffer = buffer;
    this.remoteFile = remoteFile;
  }

  public byte[] getBuffer() {
    return buffer;
  }

  public void setBuffer(byte[] buffer) {
    this.buffer = buffer;
  }

  public String getRemoteFile() {
    return remoteFile;
  }

  public void setRemoteFile(String remoteFile) {
    this.remoteFile = remoteFile;
  }

}

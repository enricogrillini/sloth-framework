package it.eg.sloth.framework.utility;

public enum FileType {
  PDF(".pdf", "application/pdf"),
  XLS(".xls", "application/xls"), 
  XLSX(".xlsx", "application/xlsx"), 
  JPEG(".jpeg", "image/jpeg"),

  TXT(".txt", "text/plain"), 
  CSV(".csv", "text/csv"),
  XML(".xml", "text/xml");

  private final String extension;
  private final String attachmentType;

  FileType(String extension, String attachmentType) {
    this.extension = extension;
    this.attachmentType = attachmentType;
  }

  public String getExtension() {
    return this.extension;
  }

  public String getContentType() {
    return this.attachmentType;
  }

}

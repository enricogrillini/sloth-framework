package it.eg.sloth.framework.utility;

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
 * <p>
 *
 * @author Enrico Grillini
 */
public enum FileType {
  PDF(".pdf", "application/pdf"),
  XLS(".xls", "application/xls"),
  XLSX(".xlsx", "application/xlsx"), 
  JPEG(".jpeg", "image/jpeg"),

  TXT(".txt", "text/plain"), 
  CSV(".csv", "application/csv"),
  XML(".xml", "text/xml");

  private final String extension;
  private final String contentType;

  FileType(String extension, String attachmentType) {
    this.extension = extension;
    this.contentType = attachmentType;
  }

  public String getExtension() {
    return this.extension;
  }

  public String getContentType() {
    return this.contentType;
  }

}

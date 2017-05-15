package com.github.fanfever.fever.upload.storage;

/**
 * DOC("doc", "application/msword"), <br/>
 * DOCX("docx", "application/msword"),  <br/>
 * XLS("xls", "application/vnd.ms-excel"),  <br/>
 * XLSX("xlsx", "application/vnd.ms-excel"),  <br/>
 * PPT("ppt", "application/vnd.ms-powerpoint"), <br/>
 * PPTX("pptx", "application/vnd.ms-powerpoint"), <br/>
 * PDF("pdf", "application/pdf"), <br/>
 * XML("xml", "application/xml"), <br/>
 * IMAGE_GIF("gif", "image/gif"), <br/>
 * IMAGE_JPEG("jpeg", "image/jpeg"), <br/>
 * IMAGE_PNG("png", "image/png");
 *
 * @author scott.he
 * @date 2017/4/8
 */
public enum AttachmentExtension {
  DOC("doc", "application/msword"),
  DOCX("docx", "application/msword"),
  XLS("xls", "application/vnd.ms-excel"),
  XLSX("xlsx", "application/vnd.ms-excel"),
  PPT("ppt", "application/vnd.ms-powerpoint"),
  PPTX("pptx", "application/vnd.ms-powerpoint"),
  PDF("pdf", "application/pdf"),
  XML("xml", "application/xml"),
  IMAGE_GIF("gif", "image/gif"),
  IMAGE_JPEG("jpeg", "image/jpeg"),
  IMAGE_PNG("png", "image/png");

  private String desc;
  private String media;
  AttachmentExtension(String desc, String media) {
    this.desc = desc;
    this.media = media;
  }
  public String getDesc() {return this.desc;}
  public String getMedia() {return this.media;}
}

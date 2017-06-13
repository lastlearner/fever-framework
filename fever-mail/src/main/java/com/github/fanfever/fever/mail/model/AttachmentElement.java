package com.github.fanfever.fever.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.activation.DataSource;
import java.io.InputStream;

/**
 * 附件
 * @author scott he
 * @date 2017/6/10
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentElement {
  private String attachmentId;
  private String attachmentName;
  private String originalAttachmentName;
  private String attachmentPath;
  private InputStream inputStream;
  private DataSource dataSource;
  private int size;
  private boolean isEmbeddedResources;
}

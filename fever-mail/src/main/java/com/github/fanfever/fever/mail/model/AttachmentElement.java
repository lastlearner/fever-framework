package com.github.fanfever.fever.mail.model;

import lombok.*;

import javax.activation.DataSource;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 附件
 * @author scott he
 * @date 2017/6/10
 * @since 0.0.2
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentElement implements Serializable {
  private String attachmentId;
  private String name;
  private String originalFileName;
  private String attachmentPath;
  private InputStream inputStream;
  private DataSource dataSource;
  private long size;
  private boolean empty;
  private byte[] bytes;
  private boolean isEmbeddedResources;
}

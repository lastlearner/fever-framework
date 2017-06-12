package com.github.fanfever.fever.upload.model;

import lombok.*;

/**
 * @author scott.he
 * @date 2017/6/10
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadRequest {
  /**
   * 存储桶
   */
  private String bucketName;
  /**
   * 存储键名
   */
  private String keyName;
}

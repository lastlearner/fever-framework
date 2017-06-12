package com.github.fanfever.fever.upload.model;

import lombok.*;

import java.io.InputStream;
import java.util.Map;

/**
 * @author scott.he
 * @date 2017/6/10
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {
  /**
   * 存储桶
   */
  private String bucketName;
  /**
   * 存储键名
   */
  private String keyName;
  /**
   * 文件流
   */
  private InputStream inputStream;

  /**
   * 元数据(用户自定义)
   */
  private Map<String, String> userMetadata;
}

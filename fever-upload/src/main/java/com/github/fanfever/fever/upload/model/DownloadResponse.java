package com.github.fanfever.fever.upload.model;

import lombok.*;

import java.io.InputStream;
import java.util.Map;

/**
 * 下载响应
 * @author scott.he
 * @date 2017/6/10
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadResponse {
  /**
   * 存储桶
   */
  private String bucketName;
  /**
   * 存储键名
   */
  private String keyName;
  /**
   * 指定桶下指定文件是否存在
   */
  private boolean exists;
  /**
   * 文件流
   */
  private InputStream inputStream;
  /**
   * 元数据(用户自定义)
   */
  private Map<String, String> userMetadata;
  /**
   * 元数据
   */
  private Map<String, Object> metadata;
  /**
   * 状态 1成功 0失败
   */
  private DownStatus status;

  public DownloadResponse withExists(boolean exists) {
    this.exists = exists;
    return this;
  }

  public enum DownStatus {
    SUCCESS(1), FAILED(0);
    DownStatus(int value) {
      this.value = value;
    }
    private int value;
    public int getValue() {
      return this.value;
    }
  }
}

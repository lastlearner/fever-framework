package com.github.fanfever.fever.upload.model;

import lombok.*;

/**
 * 上传响应
 * @author scott.he
 * @date 2017/6/10
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
  /**
   * 存储桶
   */
  private String bucketName;
  /**
   * 存储键名
   */
  private String keyName;
  /**
   * 状态 1成功 0失败
   */
  private UpStatus status;

  public enum UpStatus {
    SUCCESS(1), FAILED(0);
    UpStatus(int value) {
      this.value = value;
    }
    private int value;
    public int getValue() {
      return this.value;
    }
  }
}

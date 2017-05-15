package com.github.fanfever.fever.upload.model;

import lombok.*;

import java.io.InputStream;
import java.util.Map;

/**
 * @author scott.he
 * @date 2017/4/8
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
  /**
   * 下载/预览 -> 文件流
   * 上传 -> 空
   */
  private InputStream inputStream;
  /**
   * 指定桶下指定文件是否存在
   * 下载/预览 -> true/false
   * 上传 -> false
   */
  private boolean exists;
  /**
   * 文件元数据(用户自定义)
   */
  private Map<String, String> userMetadata;
  /**
   * 元数据
   */
  private Map<String, Object> metadata;
  /**
   * 操作方式
   * {@link Operation}
   */
  private Operation operation;

  public ResponseModel(boolean exists, Operation operation) {
    this.exists = exists;
    this.operation = operation;
  }

  public enum Operation {
    UPLOAD,
    DOWNLOAD;
  }
}

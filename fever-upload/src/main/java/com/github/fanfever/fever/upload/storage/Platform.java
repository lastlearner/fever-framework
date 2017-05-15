package com.github.fanfever.fever.upload.storage;

/**
 * 存储平台.
 * @author scott.he
 * @date 2017/4/7
 */
public enum Platform {
  /**
   * aliyun oss
   */
  OSS("aliyun.oss", 1),
  /**
   * aws s3
   */
  S3("aws.s3", 2);

  private String description;
  private int value;
  Platform(String description, int value) {
    this.description = description;
    this.value = value;
  }
  public String getDescription() {return description;}
  public int getValue() {return value;}
}

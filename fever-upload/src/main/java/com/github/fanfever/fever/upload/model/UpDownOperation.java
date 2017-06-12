package com.github.fanfever.fever.upload.model;

/**
 * @author scott he
 * @date 2017/6/10
 */
public enum UpDownOperation {
  /**
   * 上传
   */
  UP(1),
  /**
   * 下载
   */
  DOWN(2);

  private int value;
  UpDownOperation(int value) {
    this.value = value;
  }
  public int getValue() {
    return this.value;
  }
}

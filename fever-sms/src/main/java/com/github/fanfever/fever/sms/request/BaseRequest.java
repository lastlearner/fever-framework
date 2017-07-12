package com.github.fanfever.fever.sms.request;

import lombok.Builder;

/**
 * @author scott he
 * @date 2017/7/11
 */
@Builder
public class BaseRequest {
  /**
   * request url
   */
  protected String serverUrl;
  /**
   * appKey
   */
  protected String appKey;
  /**
   * appSecret
   */
  protected String appSecret;
  /**
   * 响应格式: xml、json。可为空，默认xml
   */
  protected String format;
  /**
   * 签名方法: hmac、md5
   */
  protected String signMethod;
  /**
   * API协议版本，可选值: 2.0
   */
  protected String v;
  /**
   * 时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2015-01-01 12:00:00。淘宝API服务端允许客户端请求最大时间误差为10分钟
   */
  protected String timestamp;

  /**
   * 响应格式
   */
  public enum ResponseFormatter {
    XML(1, "xml"), JSON(2, "json");

    private int value;
    private String format;

    ResponseFormatter(int value, String format) {
      this.value = value;
      this.format = format;
    }

    public int getValue() {
      return value;
    }

    public String getFormat() {
      return format;
    }
  }

  /**
   * 签名方法
   */
  public enum SignMethod {
    HMAC(1, "hmac"), MD5(2, "md5");

    private int value;
    private String method;

    SignMethod(int value, String method) {
      this.value = value;
      this.method = method;
    }

    public int getValue() {
      return value;
    }

    public String getMethod() {
      return method;
    }

  }

  /**
   * 请求方法：
   *
   * 推荐使用POST
   *
   * 若 参数名与参数值拼接后URL长度小于1024个字符， 且参数类型不含有byte[]类型，可以选择用GET
   */
  public enum HttpMethod {
    POST, GET;
  }



}

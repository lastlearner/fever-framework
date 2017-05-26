package com.github.fanfever.fever.mail.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author scott he
 * @date 2017/4/27
 */
public class CipherUtil {
  private final static Logger logger = LoggerFactory.getLogger(CipherUtil.class);

  /**
   * 解析指定文本.
   * 可以识别编码的解析后返回, 识别不了编码的返回原始文本
   * @param text
   * @return
   *
   * @see MimeUtility#decodeText(String)
   */
  public static String decodeText(String text) {
    try {
      return MimeUtility.decodeText(text);
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage(), e);
      return new String(text);
    }
  }

  /**
   * 解析base64加密的串
   * @param text 加密串
   * @return
   */
  public static String decodeUsingBase64(String text) {
    return decodeUsingBase64(text, CharsetEncoder.UTF_8.value());
  }

  /**
   * 解析base64加密的串
   * @param text 加密串
   * @param charset 编码
   * @return
   */
  public static String decodeUsingBase64(String text, String charset) {
    if(StringUtils.isEmpty(charset))
      charset = CharsetEncoder.UTF_8.value();
    BASE64Decoder base64Decoder = new BASE64Decoder();
    try {
      return new String(base64Decoder.decodeBuffer(text), charset);
    } catch (IOException e) {
      logger.error(String.format("decode text failed: %s", e.getMessage()), e);
      return null;
    }
  }

  public enum CharsetEncoder {
    UTF_8("UTF-8"),
    GB_2312("GB2312"),
    GB_18030("GB18030");

    private String code;
    CharsetEncoder(String code) {this.code = code;}
    public String value() {return code;}
  }
}

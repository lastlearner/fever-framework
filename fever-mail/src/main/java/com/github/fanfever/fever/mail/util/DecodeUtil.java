package com.github.fanfever.fever.mail.util;

import sun.misc.BASE64Decoder;

import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author scott he
 * @date 2017/4/26
 */
public class DecodeUtil {
  public static String decodeBase64(String origin, String charset, String encryptType) throws
      IOException {
    BASE64Decoder base64Decoder = new BASE64Decoder();
    if(charset == null || "".equals(charset))
      charset = "UTF-8";

    String info = new String(base64Decoder.decodeBuffer(origin), charset);

    return info;
  }

  public static String decodeText(String origin) throws UnsupportedEncodingException {
    return MimeUtility.decodeText(origin);
  }
}

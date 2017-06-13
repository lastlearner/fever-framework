package com.github.fanfever.fever.mail.hook;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 请求来源验证: SendCloud / Postfix, 对 POST 数据的来源进行安全认证.
 * @author scott.he
 * @date 2017/4/19
 */
@Slf4j
public class HookAuthentication {
  private final static String HmacSHA256 = "HmacSHA256";
  private HookAuthentication() {}

  /**
   * SHA256 verify:
   * <br/>appKey、token、timestamp生成签名signature, 与POST数据的signature作比较
   *
   * @deprecated New method instead can be used by
   * {@link HookAuthentication#requestFromSendCloud(String, String, String, String)}
   *
   * @param appKey appKey
   * @param token current webhook POST token
   * @param timestamp current webhook POST timestamp
   * @param signature current webhook POST signature
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  @Deprecated
  public static boolean verify(String appKey, String token, String timestamp,
                               String signature) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac sha256HMAC = Mac.getInstance(HmacSHA256);
    SecretKeySpec secretKeySpec = new SecretKeySpec(appKey.getBytes(), HmacSHA256);
    sha256HMAC.init(secretKeySpec);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(timestamp).append(token);
    String signatureCal = new String(Hex.encodeHex(sha256HMAC.doFinal(stringBuffer.toString().getBytes())));
    return signatureCal.equals(signature);
  }

  /**
   * SHA256 verify:
   * <br/>appKey、token、timestamp生成签名signature, 与POST数据的signature作比较
   *
   * @param appKey appKey
   * @param token current webhook POST token
   * @param timestamp current webhook POST timestamp
   * @param signature current webhook POST signature
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  public static boolean requestFromSendCloud(String appKey, String token, String timestamp,
                               String signature) {
    try {
      Mac sha256HMAC = Mac.getInstance(HmacSHA256);
      SecretKeySpec secretKeySpec = new SecretKeySpec(appKey.getBytes(), HmacSHA256);
      sha256HMAC.init(secretKeySpec);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(timestamp).append(token);
      String signatureCal = new String(Hex.encodeHex(sha256HMAC.doFinal(stringBuilder.toString().getBytes())));
      return signatureCal.equals(signature);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      log.error(e.getMessage(), e);
    }
    return false;
  }

  /**
   * 邮件回复身份验证来源postfix
   *
   * @deprecated New method instead can be used by
   * {@link HookAuthentication#requestFromPostfix(String, String, String, String)}
   *
   * @param secretKey 私钥
   * @param token token
   * @param timestamp 时间戳
   * @param signature 签名
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  @Deprecated
  public static boolean postfix(String secretKey, String token, String timestamp,
                               String signature) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac sha256HMAC = Mac.getInstance(HmacSHA256);
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(),HmacSHA256);
    sha256HMAC.init(secretKeySpec);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(token).append(timestamp);
    String signatureCalc = new String(Hex.encodeHex(sha256HMAC.doFinal(stringBuffer.toString()
        .getBytes())));
    return signatureCalc.equals(signature);
  }

  /**
   * 邮件回复身份验证来源postfix
   * @param secretKey 私钥
   * @param token token
   * @param timestamp 时间戳
   * @param signature 签名
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  public static boolean requestFromPostfix(String secretKey, String token, String timestamp,
                                String signature) {
    try {
      Mac sha256HMAC = Mac.getInstance(HmacSHA256);
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HmacSHA256);
      sha256HMAC.init(secretKeySpec);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(token).append(timestamp);
      String signatureCalc = new String(Hex.encodeHex(sha256HMAC.doFinal(stringBuilder.toString()
          .getBytes())));
      return signatureCalc.equals(signature);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      log.error(e.getMessage(), e);
    }
    return false;
  }

}

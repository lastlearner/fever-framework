package com.github.fanfever.fever.mail.hook;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 确保消息的来源是 SendCloud, 对 POST 数据的来源进行安全认证.
 * @author scott.he
 * @date 2017/4/19
 */
public class HookAuthentication {
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
  public static boolean verify(String appKey, String token, String timestamp,
                               String signature) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac sha256HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKeySpec = new SecretKeySpec(appKey.getBytes(),"HmacSHA256");
    sha256HMAC.init(secretKeySpec);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(timestamp).append(token);
    String signatureCal = new String(Hex.encodeHex(sha256HMAC.doFinal(stringBuffer.toString().getBytes())));
    return signatureCal.equals(signature);
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
  public static boolean postfix(String secretKey, String token, String timestamp,
                               String signature) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac sha256HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(),"HmacSHA256");
    sha256HMAC.init(secretKeySpec);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(token).append(timestamp);
    String signatureCalc = new String(Hex.encodeHex(sha256HMAC.doFinal(stringBuffer.toString()
        .getBytes())));
    return signatureCalc.equals(signature);
  }

}

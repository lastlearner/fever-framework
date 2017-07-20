package com.github.fanfever.fever.sms.config;

import com.github.fanfever.fever.sms.client.SmsClient;
import com.github.fanfever.fever.sms.client.SmsContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author scott he
 * @date 2017/7/11
 */
//@Configuration
public class SmsReferConfiguration {
 /* @Bean
  public SmsClient smsClient(@Value("${sms.url}") String url, @Value("${sms.appKey}") String appKey, @Value("${sms.secretKey}") String secretKey, @Value("${sms.messageUrl}") String messageUrl) {
    SmsContext smsContext = new SmsContext();
    smsContext.setUrl(url);
    smsContext.setAppKey(appKey);
    smsContext.setSecretKey(secretKey);
    smsContext.setMessageUrl(messageUrl);
    SmsClient smsClient = new SmsClient();
    smsClient.setSmsContext(smsContext);
    return smsClient;
  }*/

}

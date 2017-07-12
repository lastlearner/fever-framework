package com.github.fanfever.fever.sms.config;

import com.github.fanfever.fever.sms.client.SmsContext;
import com.github.fanfever.fever.sms.client.SmsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author scott he
 * @date 2017/7/11
 */
//@Configuration
public class SmsReferConfiguration {
  /*@Bean
  public SmsTemplate smsTemplate(@Value("${sms.url}") String url, @Value("${sms.appKey}") String appKey, @Value("${sms.secretKey}") String secretKey, @Value("${sms.messageUrl}") String messageUrl) {
    SmsContext smsContext = new SmsContext();
    smsContext.setUrl(url);
    smsContext.setAppKey(appKey);
    smsContext.setSecretKey(secretKey);
    smsContext.setMessageUrl(messageUrl);
    SmsTemplate smsTemplate = new SmsTemplate();
    smsTemplate.setSmsContext(smsContext);
    return smsTemplate;
  }*/

}

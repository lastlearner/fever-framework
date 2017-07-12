package com.github.fanfever.fever.sms.client;

import lombok.Getter;
import lombok.Setter;

/**
 * @author scott he
 * @date 2017/7/11
 */
public @Setter
@Getter
class SmsContext {
  private String appKey;
  private String secretKey;
  private String url;
  private String messageUrl;
}

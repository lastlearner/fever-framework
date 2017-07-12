package com.github.fanfever.fever.sms.client;

import com.github.fanfever.fever.sms.request.SmsSendRequest;
import com.github.fanfever.fever.sms.response.SmsSendResponse;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author scott he
 * @date 2017/7/5
 */
@Slf4j
public class SmsTemplate {
  private @Setter
  @Getter
  SmsContext smsContext;

  private TaobaoClient taobaoClient() {
    Objects.requireNonNull(smsContext, "smsContext is null");
    return new DefaultTaobaoClient(smsContext.getUrl(), smsContext.getAppKey(), smsContext
        .getSecretKey());
  }

  public SmsSendResponse send(SmsSendRequest smsSendRequest) {
    return execute(taobaoClient -> {
      try {
        AlibabaAliqinFcSmsNumSendRequest alibabaAliqinFcSmsNumSendRequest = smsSendRequest
            .convert();
        AlibabaAliqinFcSmsNumSendResponse alibabaAliqinFcSmsNumSendResponse = taobaoClient.execute
            (alibabaAliqinFcSmsNumSendRequest);
        return SmsSendResponse.builder().build().convert(alibabaAliqinFcSmsNumSendResponse);
      } catch (ApiException e) {
        log.error(e.getMessage(), e);
        throw new RuntimeException(e);
      }
    });
  }

  public <T> T execute(SmsCallback<T> smsCallback) {
    Objects.requireNonNull(smsCallback);
    return smsCallback.execute(taobaoClient());
  }


}

package com.github.fanfever.fever.sms.client;

import com.taobao.api.TaobaoClient;

/**
 * @author scott.he
 * @date 2017/4/7
 */
public interface SmsCallback<T> {
  T execute(TaobaoClient taobaoClient);
}

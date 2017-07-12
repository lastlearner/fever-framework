package com.github.fanfever.fever.sms.web.controller;

import com.github.fanfever.fever.sms.client.SmsTemplate;
import com.github.fanfever.fever.sms.request.SmsSendRequest;
import com.github.fanfever.fever.sms.response.SmsSendResponse;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author scott he
 * @date 2017/7/11
 */
@Slf4j
@RestController
@RequestMapping("/example/sms")
public class SmsExampleController {
/*
  @Autowired
  SmsTemplate smsTemplate;

  @PostMapping(value = "/send")
  public ResponseEntity<String> send() {
    SmsSendRequest smsSendRequest = SmsSendRequest.builder().build();

    smsSendRequest.setExtend("123456");
    smsSendRequest.setSmsType("normal");
    smsSendRequest.setSmsFreeSignName("身份验证");
    smsSendRequest.setSmsParam("{\"content\":\"Tes_info_1234567\"}");
    smsSendRequest.setRecNum("18600000000");
    smsSendRequest.setSmsTemplateCode("SMS_72060007");

    SmsSendResponse smsSendResponse = smsTemplate.send(smsSendRequest);
    log.info(smsSendResponse.getBody());

    return new ResponseEntity<>("", HttpStatus.OK);
  }

  @PostMapping(value = "/consume")
  public ResponseEntity<String> consume() {
    TmcClient client = new TmcClient(smsTemplate.getSmsContext().getAppKey(),
        smsTemplate.getSmsContext().getSecretKey(), "default");

    client.setMessageHandler((message, status) -> {
      try {
        *//**
         * {@link Message#getRaw()} 包含dataid的key, 键值等同于 {@link SmsSendResponse#getResult()}的model
         *//*
        log.info(message.getContent());
        log.info(message.getTopic());

        //business 逻辑处理
        //更新短信发送状态

      } catch (Exception e) {
        log.error(e.getMessage(), e);
        status.fail(); // 消息处理失败回滚，服务端需要重发
        // 重试注意：不是所有的异常都需要系统重试。
        // 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发
        // 对于，由于网络问题，权限问题导致的失败，可重试。
        // 重试时间 5分钟不等，不要滥用，否则会引起雪崩
      }
    });

    try {
      client.connect(smsTemplate.getSmsContext().getUrl());
    } catch (LinkException e ) {
      log.error(e.getMessage(), e);
    }

    return new ResponseEntity<>("", HttpStatus.OK);
  }*/

}

package com.github.fanfever.fever.mail.web.controller;

import com.github.fanfever.fever.mail.config.MailSenderConfiguration;
import com.github.fanfever.fever.mail.util.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author scott.he
 * @date 2017/4/17
 */
@RestController
@RequestMapping(value = "/mail")
public class MailSenderController {
  private final static Logger logger = LoggerFactory.getLogger(MailSenderController.class);

  @Autowired
  MailSenderConfiguration mailSender;

  @RequestMapping(value = "/send", method = RequestMethod.POST)
  public ResponseEntity<String> send() {
    //from/fromName/subject/to/cc/bcc/html
    Map<String, String> param = new HashMap<String, String>();
    param.put("from", "scott.he@foxmail.com");
    param.put("fromName", "Scott He");
    param.put("subject", "会议");
    param.put("to", "scott.he@foxmail.com");
    param.put("cc", "");
    param.put("bcc", "");
    param.put("replyTo", "reply@wokh829i4p5aTXyw5w2SmPkHGdKgTqNY.sendcloud.org");
    param.put("respEmailId", "true");
    param.put("html", "材料已发送，收到请及时回复，有问题及时沟通，谢谢. ");

    String json = JsonHelper.serialize.convert(param);
    logger.info(json);
    mailSender.send(param);
    return new ResponseEntity<String>(json, HttpStatus.OK);
  }

  @RequestMapping(value = "/attach/send", method = RequestMethod.POST)
  public ResponseEntity<String> attachSend(String attach) {
    //from/fromName/subject/to/cc/bcc/html
    Map<String, String> param = new HashMap<String, String>();
    param.put("from", "scott.he@foxmail.com");
    param.put("fromName", "Scott He");
    param.put("subject", "测试");
    param.put("to", "scott.he@foxmail.com");
    param.put("cc", "");
    param.put("bcc", "");
    param.put("html", "This is a test mail, please do not reply. ");

    String json = JsonHelper.serialize.convert(param);
    logger.info(json);
    //mailSender.send(param);
    return new ResponseEntity<String>(json, HttpStatus.OK);
  }
}

package com.github.fanfever.fever.mail.web.controller;

import com.github.fanfever.fever.mail.config.MailSenderConfiguration;
import com.github.fanfever.fever.mail.model.AttachmentElement;
import com.github.fanfever.fever.mail.model.SimpleEmail;
import com.github.fanfever.fever.mail.util.JsonHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author scott.he
 * @date 2017/4/17
 */
@Slf4j
@RestController
@RequestMapping(value = "/mail")
public class MailSenderController {

  @Autowired
  MailSenderConfiguration mailSender;

  @RequestMapping(value = "/attach/send", method = RequestMethod.POST)
  public ResponseEntity<String> attachSend(
      @RequestParam(value = "file", required = false) MultipartFile[] fileList) {

    SimpleEmail simpleEmail = new SimpleEmail();
    simpleEmail.setFrom("scott.he@foxmail.com");
    simpleEmail.setFromName("Scott He");
    simpleEmail.setSubject("产品会议");
    simpleEmail.addRecipient("scott.he@foxmail.com", Message.RecipientType.TO);
    simpleEmail.addRecipient("scott.he@foxmail.com", Message.RecipientType.CC);
    simpleEmail.addRecipient("hedongfang@udesk.cn", Message.RecipientType.BCC);
    simpleEmail.setTextHtml("测试邮件 - \n 各位好，现定于明天下午三点，在西五楼302开会，请安排好时间准时参加。收到请回复，谢谢！");
    //处理附件
    if (ArrayUtils.isNotEmpty(fileList)) {
      for(MultipartFile file : fileList) {
        try {
          simpleEmail.addAttachmentElement(
              AttachmentElement.builder()
                  .inputStream(file.getInputStream())
                  .name(file.getName())
                  .originalFileName(file.getOriginalFilename())
                  .bytes(file.getBytes())
                  .size(file.getSize())
                  .build()
          );
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    simpleEmail.setContainAttachment(true);

    Map<String, List<String>> xsmtpapi = new HashMap<>();
    List<String> addressList = Lists.newArrayList(
        "scott.he@foxmail.com",
        "zhangfan@udesk.cn",
        "yangxi@udesk.cn",
        "hedongfang@udesk.cn");
    xsmtpapi.put("to", addressList);
    simpleEmail.addAdditionalInformation("xsmtpapi", JsonHelper.serialize.convert(xsmtpapi));

    /*Object object = mailSender.send(simpleEmail);
    return new ResponseEntity<>(object.toString(), HttpStatus.OK);*/

    //to
    for (int i = 0; i < 150; i ++) {
      simpleEmail.addRecipient("scott.he-" + (i + 1) + "@foxmail.com", Message.RecipientType.TO);
    }
    //cc
    for (int i = 0; i < 150; i ++) {
      simpleEmail.addRecipient("scott.he-" + (i + 1) + "@foxmail.com", Message.RecipientType.CC);
    }
    //bcc
    for (int i = 0; i < 150; i ++) {
      simpleEmail.addRecipient("scott.he-" + (i + 1) + "@foxmail.com", Message.RecipientType.BCC);
    }
    log.info("rebuild start");
    simpleEmail.rebuildRecipientList(100);
    log.info("rebuild end");

    return new ResponseEntity<>("", HttpStatus.OK);
  }
}

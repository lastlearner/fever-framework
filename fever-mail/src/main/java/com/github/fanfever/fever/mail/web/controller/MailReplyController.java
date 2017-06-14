package com.github.fanfever.fever.mail.web.controller;

import com.github.fanfever.fever.mail.model.SimpleEmail;
import com.github.fanfever.fever.mail.parse.MessageParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import java.io.IOException;

/**
 * @author scott he
 * @date 2017/4/28
 */
@Slf4j
@RestController
@RequestMapping(value = "/mail")
public class MailReplyController {

  @RequestMapping(value = "/test/reply", method = RequestMethod.POST)
  public ResponseEntity<String> reply(@RequestParam(value = "file") MultipartFile file) {
    if(file != null) {
      log.info("reply request start...");
      log.info(String.format("mailPath: %s", file.getOriginalFilename()));

      try {
        MessageParser messageParser = new MessageParser(file.getInputStream());
        SimpleEmail simpleEmail = messageParser.parse();
        log.info("from: {}", simpleEmail.getFrom());
        log.info("fromName: {}", simpleEmail.getFromName());
        log.info("subject: {}", simpleEmail.getSubject());
        log.info("to: {}", simpleEmail.splitRecipients(Message.RecipientType.TO));
        log.info("headers: {}", simpleEmail.getHeaders());
        log.info("sendDate: {}", simpleEmail.getSendDate());
        log.info("receivedDate: {}", simpleEmail.getReceivedDate());
        log.info("disposition: {}", simpleEmail.getDisposition());
        log.info("description: {}", simpleEmail.getDescription());
        log.info("messageId: {}", simpleEmail.getMessageId());
        log.info("contentType: {}", simpleEmail.getContentType());
        log.info("encoding: {}", simpleEmail.getEncoding());
        log.info("content: {}", simpleEmail.getTextHtml());
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
    return new ResponseEntity<>("{status: success}", HttpStatus.OK);
  }
}

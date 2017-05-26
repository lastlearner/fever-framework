package com.github.fanfever.fever.mail.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author scott he
 * @date 2017/4/28
 */
@RestController
@RequestMapping(value = "/mail")
public class MailReplyController {

  /*@RequestMapping(value = "/reply2", method = RequestMethod.POST)
  public ResponseEntity<String> reply2(@RequestParam(value = "file") MultipartFile file) {
    if(file != null) {
      logger.info("reply request start...");
      logger.info(String.format("mailPath: %s", file.getOriginalFilename()));

      try {
        MessageParser messageParser = new MessageParser(file.getInputStream());

        System.out.println("from: " + messageParser.from());
        System.out.println("fromName: " + messageParser.fromName());
        System.out.println("subject: " + messageParser.subject());
        System.out.println("to: " + messageParser.allRecipients());
        System.out.println("headers: " + messageParser.headers());
        System.out.println("sendDate: " + messageParser.sendDate());
        System.out.println("receivedDate: " + messageParser.receivedDate());
        System.out.println("disposition: " + messageParser.disposition());
        System.out.println("description: " + messageParser.description());
        System.out.println("messageId: " + messageParser.messageId());
        System.out.println("contentType: " + messageParser.contentType());
        System.out.println("encoding: " + messageParser.encoding());
        System.out.println("content: " + messageParser.content());
      } catch (IOException e) {

      }

    }
    return new ResponseEntity<String>("{status: success}", HttpStatus.OK);
  }*/
}

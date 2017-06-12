package com.github.fanfever.fever.mail.parse;

import javax.mail.Session;
import java.util.Properties;

/**
 * @author scott he
 * @date 2017/4/28
 */
public class MessageContext {
  private MessageContext() {}
  public static Properties initializeProperties() {
    Properties properties = new Properties();
    properties.put("mail.host", "smtp.udesk.cn");
    properties.put("mail.transport.protocol", "smtp");
    return properties;
  }

  public static Session initializeSession() {
    return Session.getDefaultInstance(initializeProperties());
  }
}

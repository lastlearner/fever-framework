package com.github.fanfever.fever.mail.exception;

/**
 * @author scott.he
 * @date 2017/4/17
 */
public class MailException extends RuntimeException {
  public MailException(String message) {
    super(message);
  }
  public String message() {
    //other message process
    return super.getMessage();
  }
}

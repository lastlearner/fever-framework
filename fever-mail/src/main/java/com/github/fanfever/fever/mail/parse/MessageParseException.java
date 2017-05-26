package com.github.fanfever.fever.mail.parse;

/**
 * @author scott he
 * @date 2017/4/28
 */
public class MessageParseException extends RuntimeException {
  public MessageParseException() {
    super();
  }
  public MessageParseException(String message) {
    super(message);
  }
  public MessageParseException(String message, Throwable cause) {
    super(message, cause);
  }
}

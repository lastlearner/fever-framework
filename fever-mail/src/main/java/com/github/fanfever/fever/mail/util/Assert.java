package com.github.fanfever.fever.mail.util;

/**
 * @author scott.he
 * @date 2017/4/17
 */
public class Assert {
  public static <T> T notNull(T object, String name) {
    if(object == null)
      throw new IllegalStateException(name + "is null");
    return object;
  }
}

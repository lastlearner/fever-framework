package com.github.fanfever.fever.upload.util;

/**
 * @author scott.he
 * @date 2017/4/7
 */
public class PreCheck {
  public static void notNull(Object... args) {
    for(Object obj : args) {
      notNull(obj, "参数不能为空");
      break;
    }
  }

  public static void notNull(Object o, String message) {
    if(o == null)
      throw new IllegalArgumentException(message);
  }
}

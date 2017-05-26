package com.github.fanfever.fever.mail.util;

/**
 * @author scott.he
 * @date 2017/4/14
 */
public enum Contact {
  MAIL("email service", 1),
  SMS("short message service", 2);

  private String description;
  private int type;
  Contact(String description, int type) {
    this.description = description;
    this.type = type;
  }
  public String getDescription() {return this.description;}
  public int getType() {return this.type;}
}

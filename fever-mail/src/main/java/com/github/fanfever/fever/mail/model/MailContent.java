package com.github.fanfever.fever.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author scott.he
 * @date 2017/4/17
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailContent {
  /**
   * 内容
   */
  private String text;
}

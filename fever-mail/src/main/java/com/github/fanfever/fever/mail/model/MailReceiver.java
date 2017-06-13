package com.github.fanfever.fever.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MailReceiver.
 * <p> 优先使用{@link SimpleEmail}
 * @author scott.he
 * @date 2017/4/15
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailReceiver implements Serializable {
  /**
   * 收件人
   */
  private List<String> to = new ArrayList<String>();
  /**
   * 抄送人
   */
  private List<String> cc = new ArrayList<String>();
  /**
   * 密送
   */
  private List<String> bcc = new ArrayList<String>();
}

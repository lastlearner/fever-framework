package com.github.fanfever.fever.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * MailBody.
 * <p> 优先使用{@link SimpleEmail}
 * @author scott.he
 * @date 2017/4/14
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailBody implements Serializable {
  /**
   * 发件人地址
   */
  private String from;
  /**
   * 发件人名称
   */
  private String fromName;
  /**
   * 邮件主题
   */
  private String subject;
  /**
   * 回复邮件地址, 多个时使用';'分隔, 总个数不能超过三个
   * 不设置或为空时默认发件人地址
   */
  private String replyTo;
  /**
   * 附件
   */
  private List<Object> attachments;
}

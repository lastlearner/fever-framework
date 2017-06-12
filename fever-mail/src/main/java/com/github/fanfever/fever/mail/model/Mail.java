package com.github.fanfever.fever.mail.model;

import com.github.fanfever.fever.mail.exception.MailException;
import com.github.fanfever.fever.mail.util.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Mail model.
 * <p> 优先使用{@link SimpleEmail}
 * @author scott.he
 * @date 2017/4/17
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
  private MailBody mailBody;
  private MailContent mailContent;
  private MailReceiver mailReceiver;

  public boolean validate() {
    Assert.notNull(mailBody, "mailBody");
    if(StringUtils.isBlank(mailBody.getFrom()))
      throw new MailException("发件人为空");
    if(StringUtils.isBlank(mailBody.getSubject()))
      throw new MailException("邮件主题为空");

    Assert.notNull(mailContent, "mailContent");
    if(StringUtils.isBlank(mailContent.getText()))
      throw new MailException("邮件内容为空");

    Assert.notNull(mailReceiver, "mailReceiver");
    if(CollectionUtils.isEmpty(mailReceiver.getTo()))
      throw new MailException("收件人为空");
    return true;
  }
}

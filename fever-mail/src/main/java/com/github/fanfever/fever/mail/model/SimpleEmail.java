package com.github.fanfever.fever.mail.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.Message;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SimpleEmail
 * @author scott he
 * @date 2017/4/27
 */
@Setter
@Getter
@NoArgsConstructor
public class SimpleEmail implements Serializable {
  /**
   * 发件人地址: 发送邮件时不能为空
   */
  private String from;
  /**
   * 发件人名称
   */
  private String fromName;
  /**
   * 收件人、抄送人、密送人 列表: 发送邮件时 收件人 不能为空
   */
  private List<Recipient> recipients = new ArrayList<>();
  /**
   * 邮件主题: 发送邮件时不能为空
   */
  private String subject;
  /**
   * 邮件回复地址列表: 已发送邮件，目标收件人在回复时，默认回复地址为此列表地址，发送邮件时不能为空
   */
  private List<String> replyTo = new ArrayList<>();
  /**
   * 邮件正文: 优先使用textHtml，textHtml为空时使用此字段，text / textHtml不能同时为空
   */
  private String text;
  /**
   * 邮件正文: 优先使用textHtml，textHtml为空时使用此字段，text / textHtml不能同时为空
   */
  private String textHtml;
  /**
   * 邮件头信息: 发送邮件时可以为空
   */
  private Map<String, String> headers = new ConcurrentHashMap<>();
  /**
   * 邮件嵌入资源: 发送邮件时可以为空
   */
  private List<EmbeddedImage> embeddedImages = new ArrayList<>();
  /**
   * 发送日期: 发送邮件时可以为空
   */
  private String sendDate;
  /**
   * 接收日期: 发送邮件时可以为空
   */
  private String receivedDate;
  /**
   * 邮件messageId，一般自动生成，发送邮件时可以为空
   */
  private String messageId;
  /**
   * 报文contentType，发送邮件时可以为空
   */
  private String contentType;
  /**
   * 报文编码，发送邮件时可以为空
   */
  private String encoding;
  /**
   * 邮件disposition，发送邮件时可以为空
   */
  private String disposition;
  /**
   * 邮件描述，发送邮件时可以为空
   */
  private String description;
  /**
   * 已读回执，发送邮件时可以为空
   */
  private boolean replySign;
  /**
   * 是否包含附件，发送邮件时可以为空
   */
  private boolean containAttachment;
  /**
   * 自定义键值对: 如指定当前邮件发送时使用的apiUser / apiKey ，or other message
   */
  private Map<String, String> additionalInformation = new HashMap<>();

  public void addRecipient(Recipient... recipient) {
    Collections.addAll(recipients, recipient);
  }

  public List<Recipient> getRecipients() {
    return Collections.unmodifiableList(recipients);
  }

  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  public Map<String, String> getHeaders() {
    return Collections.unmodifiableMap(headers);
  }

  public void addEmbeddedImage(EmbeddedImage... embeddedImage) { Collections.addAll(embeddedImages, embeddedImage); }

  public List<EmbeddedImage> getEmbeddedImages() { return Collections.unmodifiableList(embeddedImages); }

  /**
   * 分隔 收件人、抄送人、密送人
   * @param type {@link Message.RecipientType}
   * @return {@link List<String>}
   */
  public List<String> splitRecipients(Message.RecipientType type) {
    List<String> rec = Lists.newArrayList();
    if (CollectionUtils.isNotEmpty(this.getRecipients())) {
      getRecipients().forEach(r -> {
        if (r.getRecipientType().toString().equals(type.toString())) {
          rec.add(r.getAddress());
        }
      });
    }
    return rec;
  }

  public void validate() {
    Preconditions.checkArgument(StringUtils.isNotBlank(from), "发件人地址不能为空!");
    Preconditions.checkArgument(StringUtils.isNotBlank(subject), "邮件主题不能为空!");
    Preconditions.checkArgument(!splitRecipients(Message.RecipientType.TO).isEmpty(),
        "收件人至少一个!");
    Preconditions.checkArgument(!replyTo.isEmpty(), "确保正常收到回复，replyTo不能为空!");
    Preconditions.checkArgument(StringUtils.isNotBlank("".concat(text).concat(textHtml)),
        "邮件正文不能为空!");
  }

}

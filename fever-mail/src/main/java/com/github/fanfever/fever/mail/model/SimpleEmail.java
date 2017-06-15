package com.github.fanfever.fever.mail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.fanfever.fever.mail.util.JsonHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.Message;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * SimpleEmail
 * @author scott he
 * @date 2017/4/27
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
  private List<Recipient> recipients = Lists.newArrayList();
  /**
   * 邮件主题: 发送邮件时不能为空
   */
  private String subject;
  /**
   * 是否需要回复: true -> {@link SimpleEmail#replyTo}不能为空
   */
  private boolean needReply = false;
  /**
   * 邮件回复地址列表: 最多3个
   * note: 已发送邮件，目标收件人在回复时，默认回复地址为此列表地址，若确认当前邮件需要回复，可通过此字段配置回复地址，为空时默认回复到from
   */
  private List<String> replyTo = Lists.newArrayList();
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
  private List<EmbeddedImage> embeddedImages = Lists.newArrayList();
  /**
   * 邮件附件
   */
  private List<AttachmentElement> attachmentElements = Lists.newArrayList();
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
  private boolean containAttachment = false;
  /**
   * 自定义键值对: 如指定当前邮件发送时使用的apiUser / apiKey ，or other message
   */
  private Map<String, String> additionalInformation = new HashMap<>();

  public void addRecipient(Recipient... recipient) {
    Collections.addAll(recipients, recipient);
  }

  public List<Recipient> getRecipients() {
    return recipients;
  }

  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void addEmbeddedImage(EmbeddedImage... embeddedImage) { Collections.addAll(embeddedImages, embeddedImage); }

  public List<EmbeddedImage> getEmbeddedImages() { return embeddedImages; }

  public void addAttachmentElement(AttachmentElement... attachmentElement) { Collections.addAll(attachmentElements, attachmentElement); }

  public List<AttachmentElement> getAttachmentElements() { return attachmentElements; }

  public void addAdditionalInformation(String key, String value) {
    additionalInformation.put(key, value);
  }

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

  /**
   * 添加 收件人、抄送人、密送人
   * @param strings 收件人、抄送人、密送人 字符串
   * @param recipientType 类型
   */
  public void addRecipient(String strings, Message.RecipientType recipientType) {
    addRecipient(strings, recipientType, ',');
  }

  /**
   * 添加 收件人、抄送人、密送人
   * @param strings 收件人、抄送人、密送人 字符串
   * @param recipientType 类型
   * @param separator 分隔符
   */
  public void addRecipient(String strings, Message.RecipientType recipientType, char separator) {
    if (StringUtils.isNotBlank(strings)) {
      addRecipient(Splitter.on(separator).trimResults().splitToList(strings), recipientType);
    }
  }

  /**
   * 添加 收件人、抄送人、密送人
   * @param stringList 收件人、抄送人、密送人 List
   * @param recipientType 类型
   */
  public void addRecipient(List<String> stringList, Message.RecipientType recipientType) {
    if (CollectionUtils.isNotEmpty(stringList)) {
      stringList.forEach(entry ->
          recipients.add(new Recipient(entry.substring(0, entry.lastIndexOf('@')), entry,
              recipientType))
      );
    }
  }

  /**
   * revert simpleEmail to map
   * @return {@link Map<String, String>}
   */
  public Map<String, String> getParameters() {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("from", from);
    parameters.put("fromName", fromName);
    parameters.put("to", splitRecipients(Message.RecipientType.TO).stream().collect(Collectors
        .joining(",")));
    parameters.put("cc", splitRecipients(Message.RecipientType.CC).stream().collect(Collectors
        .joining(",")));
    parameters.put("bcc", splitRecipients(Message.RecipientType.BCC).stream().collect(Collectors
        .joining(",")));
    parameters.put("subject", subject);
    parameters.put("html", StringUtils.isBlank(textHtml) ? text : textHtml);
    parameters.put("replyTo", replyTo.stream().collect(Collectors.joining(",")));
    parameters.put("headers", JsonHelper.serialize.convert(headers));
    parameters.putAll(additionalInformation);
    return parameters;
  }

  /**
   * <p>重建收件人、抄送人、密送人.
   * 若收件人、抄送人、密送人个数大于指定数量，按顺序忽略指定数目以后的地址
   *
   * @param num 限定收件人、抄送人、密送人的数量
   */
  public void rebuildRecipientList(int num) {
    List<String> toList = splitRecipients(Message.RecipientType.TO);
    List<String> ccList = splitRecipients(Message.RecipientType.CC);
    List<String> bccList = splitRecipients(Message.RecipientType.BCC);

    if (toList.size() > num || ccList.size() > num || bccList.size() > num) {
      List<Recipient> list = Lists.newArrayList();
      if (toList.size() > num) {
        toList = toList.subList(0, num);
      }
      toList.forEach(to -> {
        list.add(Recipient.builder()
            .name(to.substring(0, to.lastIndexOf('@')))
            .address(to)
            .recipientType(Message.RecipientType.TO)
            .build()
        );
      });
      if (ccList.size() > num) {
        ccList = ccList.subList(0, num);
      }
      ccList.forEach(cc -> {
        list.add(Recipient.builder()
            .name(cc.substring(0, cc.lastIndexOf('@')))
            .address(cc)
            .recipientType(Message.RecipientType.CC)
            .build()
        );
      });
      if (bccList.size() > num) {
        bccList = bccList.subList(0, num);
      }
      bccList.forEach(bcc -> {
        list.add(Recipient.builder()
            .name(bcc.substring(0, bcc.lastIndexOf('@')))
            .address(bcc)
            .recipientType(Message.RecipientType.BCC)
            .build()
        );
      });
      setRecipients(list);
    }
  }

  public void validate() {
    Preconditions.checkArgument(StringUtils.isNotBlank(getFrom()), "发件人地址不能为空!");
    Preconditions.checkArgument(StringUtils.isNotBlank(getSubject()), "邮件主题不能为空!");
    Preconditions.checkArgument(!splitRecipients(Message.RecipientType.TO).isEmpty(),
        "收件人至少一个!");
    if (isNeedReply())
      Preconditions.checkArgument(!getReplyTo().isEmpty(), "确保正常收到回复，replyTo不能为空!");
    Preconditions.checkArgument(
        StringUtils.isNotBlank(
            "".concat(getText() == null ? "" : getText())
            .concat(getTextHtml() == null ? "" : getTextHtml())
        ), "邮件正文不能为空!");
  }

}

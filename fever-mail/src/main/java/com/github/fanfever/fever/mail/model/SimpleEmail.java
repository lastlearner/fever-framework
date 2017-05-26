package com.github.fanfever.fever.mail.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author scott he
 * @date 2017/4/27
 */
@Setter
@Getter
@NoArgsConstructor
public class SimpleEmail implements Serializable {
  private String from;
  private String fromName;
  private List<Recipient> recipients = new ArrayList<Recipient>();
  private String subject;
  private List<String> replyTo = new ArrayList<String>();
  private String text;
  private String textHtml;
  private Map<String, String> headers = new ConcurrentHashMap<String, String>();
  private List<EmbeddedImage> embeddedImages = new ArrayList<EmbeddedImage>();
  private String sendDate;
  private String receivedDate;
  private String messageId;
  private String contentType;
  private String encoding;
  private String disposition;
  private String description;
  private boolean replySign;
  private boolean containAttachment;

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
}

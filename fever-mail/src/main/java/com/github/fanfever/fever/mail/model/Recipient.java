package com.github.fanfever.fever.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.mail.Message;
import java.io.Serializable;
import java.util.Objects;

/**
 * 收件人、抄送人、密送人
 * @author scott he
 * @date 2017/4/27
 */
@Setter
@Getter
@AllArgsConstructor
public class Recipient implements Serializable {
  private String name;
  private String address;
  private Message.RecipientType recipientType;

  @Override
  public String toString() {
    return String.format("{name: %s, address: %s, recipientType: %s}", name, address, recipientType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, address, recipientType);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null)
      return false;
    if(this == obj)
      return true;
    if(obj instanceof Recipient) {
      Recipient recipient = (Recipient) obj;
      return (this.name == recipient.getName()
          && this.address == recipient.getAddress()
          && this.recipientType == recipient.getRecipientType());
    }
    return false;
  }
}

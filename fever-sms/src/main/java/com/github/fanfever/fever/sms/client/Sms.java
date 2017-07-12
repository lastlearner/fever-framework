package com.github.fanfever.fever.sms.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

/**
 * @author scott he
 * @date 2017/7/5
 */
@Builder
public class Sms {
  /**
   * value : [1 ~ 4].
   * refer {@link Type#value}
   */
  private int type;
  /**
   * 是否使用模板
   */
  private boolean usedTemplate;
  /**
   * 模板id
   */
  private int templateId;
  /**
   * 内容
   */
  private String content;

  public enum Type {
    VERIFY_CODE(1, "验证码"), NOTICE(2, "短信通知"), VOICE(3, "语音通知"), GROUP(4, "群发");

    private @Setter
    @Getter
    int value;
    private @Setter
    @Getter
    String description;
    Type(int value, String description) {
      this.value = value;
      this.description = description;
    }

    public static String getDescriptionByValue(int value) {
      Type type = Stream.of(values()).filter(t -> t.value == value).findAny().orElse(null);
      return type == null ? "" : type.getDescription();
    }
  }
}

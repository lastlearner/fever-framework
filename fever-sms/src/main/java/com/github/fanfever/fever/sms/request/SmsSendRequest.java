package com.github.fanfever.fever.sms.request;

import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 短信发送
 *
 * @author scott he
 * @date 2017/7/11
 */
@Builder
@Getter
@Setter
public class SmsSendRequest implements Serializable {
  /**
   * 公共回传参数
   */
  private String extend;
  /**
   * 短信接收号码.
   *
   * 支持单个 或者 多个, 传入号码为11位, 不能加0 或者 +86.
   *
   * 多个号码时, 使用英文逗号分隔 (18600000000,18600000001), 一次最多传入200个
   */
  private String recNum;
  /**
   * 短信签名, 签名必须是大于管理中心已经配置且可用的签名, 如: 身份验证
   */
  private String smsFreeSignName;
  /**
   * 短信模板变量, 规则{"key":"value"}, key的名字须和申请模板中的变量名一致, 多个变量之间以逗号隔开
   *
   * 如模板"验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦!", 传参时需传入{"code":"1234567","product":"udesk"}
   */
  private String smsParam;
  /**
   * 短信模板ID, 传入的模板必须是大于管理中心已经配置且可用的模板
   */
  private String smsTemplateCode;
  /**
   * 短信类型, 传参请填写normal
   */
  private String smsType;

  public AlibabaAliqinFcSmsNumSendRequest convert() {
    AlibabaAliqinFcSmsNumSendRequest request = new AlibabaAliqinFcSmsNumSendRequest();
    request.setExtend(extend);
    if (StringUtils.isBlank(smsType))
      smsType = "normal";
    request.setSmsType(smsType);
    request.setSmsFreeSignName(smsFreeSignName);
    request.setSmsParamString(smsParam);
    request.setRecNum(recNum);
    request.setSmsTemplateCode(smsTemplateCode);
    return request;
  }

}

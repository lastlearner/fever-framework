package com.github.fanfever.fever.sms.response;

import com.taobao.api.domain.BizResult;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 短信发送请求响应
 *
 * 此响应仅能标识短信发送请求API的结果信息, 并不能标识短信是否已经发送成功或者失败的状态
 *
 * 如需要了解每条短信的发送状态, 请主动拉取回执
 *
 * @author scott he
 * @date 2017/7/11
 */
public @Builder @Getter @Setter class SmsSendResponse implements Serializable {

  /**
   * 错误码
   */
  private String errorCode;
  /**
   * 错误信息
   */
  private String msg;
  /**
   * 子错误码, can view
   */
  private String subCode;
  /**
   * 子错误信息
   */
  private String subMsg;
  /**
   * 序列化result
   */
  private String body;
  /**
   * 请求时参数
   */
  private Map<String, String> params;
  /**
   * 响应
   */
  private Result result;

  public SmsSendResponse convert(AlibabaAliqinFcSmsNumSendResponse alibabaAliqinFcSmsNumSendResponse) {
    if (alibabaAliqinFcSmsNumSendResponse == null)
      return SmsSendResponse.builder().build();

    BizResult bizResult = alibabaAliqinFcSmsNumSendResponse.getResult();
    Result result = new Result();
    result.setErrCode(bizResult.getErrCode());
    result.setModel(bizResult.getModel());
    result.setMsg(bizResult.getMsg());
    result.setSuccess(bizResult.getSuccess());
    this.setResult(result);

    this.setErrorCode(alibabaAliqinFcSmsNumSendResponse.getErrorCode());
    this.setMsg(alibabaAliqinFcSmsNumSendResponse.getMsg());
    this.setSubCode(alibabaAliqinFcSmsNumSendResponse.getSubCode());
    this.setSubMsg(alibabaAliqinFcSmsNumSendResponse.getSubMsg());
    this.setBody(alibabaAliqinFcSmsNumSendResponse.getBody());
    this.setParams(alibabaAliqinFcSmsNumSendResponse.getParams());

    return this;
  }

  public @Getter @Setter class Result implements Serializable {
    /**
     * 错误码
     */
    private String errCode;
    /**
     * 返回结果, 短信标识id
     */
    private String model;
    /**
     * 返回信息描述
     */
    private String msg;
    /**
     * 是否成功, 此处的是否成功指的是请求发送API是否成功, 并非短信是否发送成功
     */
    private Boolean success;
  }

}

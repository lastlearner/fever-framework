package com.github.fanfever.fever.mail.config;

import com.github.fanfever.fever.mail.model.Mail;
import com.github.fanfever.fever.mail.util.Assert;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 邮件
 * @author scott.he
 * @date 2017/4/17
 */
@Component
public class MailSenderConfiguration {

  @Value("${mail.sendcloud.sendurl}")
  private String sendUrl;
  @Value("${mail.sendcloud.mailer.apiUser}")
  private String apiUser;
  @Value("${mail.sendcloud.mailer.apiKey}")
  private String apiKey;

  /**
   * 发送邮件.
   *
   * @param mail 邮件相关信息
   * @return
   */
  public Object send(Mail mail) {
    Assert.notNull(mail, "mail");
    mail.validate();
    MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
    postParameters.add("subject", mail.getMailBody().getSubject());
    postParameters.add("to", StringUtils.join(mail.getMailReceiver().getTo().iterator(), ";"));
    postParameters.add("cc", StringUtils.join(mail.getMailReceiver().getCc().iterator(), ";"));
    postParameters.add("bcc", StringUtils.join(mail.getMailReceiver().getBcc().iterator(), ";"));
    postParameters.add("html", mail.getMailContent().getText());
    return send(postParameters);
  }

  /**
   * 发送邮件:
   *
   * <br/>此方法不会作校验，使用之前需确保mail包含发送邮件所需参数:
   * <br/>from: 发件地址
   * <br/>fromName: 发件人名称
   * <br/>subject: 主题
   * <br/>to: 收件人
   * <br/>cc: 抄送人
   * <br/>bcc: 密送
   * <br/>html: 邮件内容
   * <br/><span style="font-weight: bold; color: red;">其中:
   * <br/>to(收件人)、cc(抄送人)、bcc(密送) 如果包含多个, 采用';'分隔
   * </span>
   * @param mail 邮件相关信息
   * @return
   */
  public Object send(Map<String, String> mail) {
    Assert.notNull(mail, "mail");
    MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
    for(Map.Entry<String, String> entry : mail.entrySet()) {
      postParameters.add(entry.getKey(), entry.getValue());
    }
    return send(postParameters);
  }

  /**
   * 发送邮件
   *
   * @param postParameters
   * @return
   */
  public Object send(MultiValueMap<String, String> postParameters) {
    Assert.notNull(postParameters, "postParameters");
    if(!postParameters.containsKey("apiUser"))
      postParameters.add("apiUser", apiUser);
    if(!postParameters.containsKey("apiKey"))
      postParameters.add("apiKey", apiKey);
    //region HttpHeader
    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
    headers.setContentType(type);
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    //endregion

    HttpEntity<MultiValueMap<String, String>> requestEntity  = new HttpEntity<MultiValueMap<String, String>>(postParameters, headers);
    return this.restTemplate().postForObject(sendUrl, requestEntity, Object.class);
  }

  private RestTemplate restTemplate() {
    HttpClient httpClient = HttpClientBuilder
        .create()
        .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
          public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
            return 5 * 1000;
          }
        }).build();
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory(httpClient);
    clientHttpRequestFactory.setConnectTimeout(3000);
    clientHttpRequestFactory.setReadTimeout(5000);
    return new RestTemplate(clientHttpRequestFactory);
  }

}

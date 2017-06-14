package com.github.fanfever.fever.mail.config;

import com.github.fanfever.fever.mail.exception.MailException;
import com.github.fanfever.fever.mail.model.Mail;
import com.github.fanfever.fever.mail.model.SimpleEmail;
import com.github.fanfever.fever.mail.util.Assert;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件
 * @author scott.he
 * @date 2017/4/17
 */
@Slf4j
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
    MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
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
    MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
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
    if(!postParameters.containsKey(API_USER))
      postParameters.add(API_USER, apiUser);
    if(!postParameters.containsKey(API_KEY))
      postParameters.add(API_KEY, apiKey);
    //region HttpHeader
    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
    headers.setContentType(type);
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    //endregion

    HttpEntity<MultiValueMap<String, String>> requestEntity  = new HttpEntity<>(postParameters, headers);
    return this.restTemplate().postForObject(sendUrl, requestEntity, Object.class);
  }

  private RestTemplate restTemplate() {
    HttpClient httpClient = HttpClientBuilder
        .create()
        .setKeepAliveStrategy((httpResponse,httpContext) -> 5l * 1000).build();
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory(httpClient);
    clientHttpRequestFactory.setConnectTimeout(3000);
    clientHttpRequestFactory.setReadTimeout(5000);
    return new RestTemplate(clientHttpRequestFactory);
  }

  private final static String API_USER = "apiUser";
  private final static String API_KEY = "apiKey";
  private final static Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
  private final static ContentType TEXT_PLAIN = ContentType.create("text/plain", CHARSET_UTF_8);
  private final static String ATTACHMENT_KEY = "attachments";

  /**
   * 发送邮件: 新项目优先使用此方法，为统一入口，后期 {@link MailSenderConfiguration#send(Mail)} 与
   * {@link MailSenderConfiguration#send(Map)} 会弃用 {@link Deprecated}
   *
   * @param simpleEmail mail {@link SimpleEmail}
   * @return {@link Object}
   */
  public Object send(SimpleEmail simpleEmail) {
    Preconditions.checkNotNull(simpleEmail, "simpleEmail不能为空").validate();
    return doSend(simpleEmail);
  }

  private Object doSend(SimpleEmail simpleEmail) {
    try {
      Request request = Request.Post(sendUrl).connectTimeout(500).socketTimeout(1000);
      if (simpleEmail.isContainAttachment() || !simpleEmail.getAttachmentElements().isEmpty()) {
        request.body(getEmailHttpEntity(simpleEmail));
      } else {
        request.bodyForm(getEmailForm(simpleEmail).build(), CHARSET_UTF_8);
      }
      return request.execute().returnContent().asString();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new MailException(e);
    }
  }

  private org.apache.http.HttpEntity getEmailHttpEntity(SimpleEmail simpleEmail) {
    MultipartEntityBuilder multipartEntityBuilder = createMultipartEntityBuilder();
    simpleEmail.getAttachmentElements().forEach(attachmentElement ->
      multipartEntityBuilder.addBinaryBody(ATTACHMENT_KEY, attachmentElement.getInputStream(),
          ContentType.MULTIPART_FORM_DATA, attachmentElement.getOriginalFileName())
    );
    for (Map.Entry<String, String> entry : check(simpleEmail.getParameters()).entrySet()) {
      multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue(), TEXT_PLAIN);
    }
    return multipartEntityBuilder.build();
  }

  private Form getEmailForm(SimpleEmail simpleEmail) {
    Form formData = Form.form();
    for (Map.Entry<String, String> entry : check(simpleEmail.getParameters()).entrySet()) {
      formData.add(entry.getKey(), entry.getValue());
    }
    return formData;
  }

  private Map<String, String> check(Map<String, String> params) {
    Map<String, String> retMaps = new HashMap<>(params);
    if(!retMaps.containsKey(API_USER))
      retMaps.put(API_USER, apiUser);
    if(!retMaps.containsKey(API_KEY))
      retMaps.put(API_KEY, apiKey);
    return retMaps;
  }

  private MultipartEntityBuilder createMultipartEntityBuilder() {
    return MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
        .setCharset(CHARSET_UTF_8);
  }
}

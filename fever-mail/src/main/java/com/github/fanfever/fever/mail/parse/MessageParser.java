package com.github.fanfever.fever.mail.parse;

import com.github.fanfever.fever.mail.model.EmbeddedImage;
import com.github.fanfever.fever.mail.model.Recipient;
import com.github.fanfever.fever.mail.model.SimpleEmail;
import com.github.fanfever.fever.mail.util.CipherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 邮件报文解析
 * @author scott he
 * @date 2017/4/27
 */
public class MessageParser {
  private final static Logger logger = LoggerFactory.getLogger(MessageParser.class);
  private MimeMessage mimeMessage;

  public MessageParser(InputStream inputStream) {
    Session session = MessageContext.initializeSession();
    try {
      this.mimeMessage = new MimeMessage(session, inputStream);
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
      throw new MessageParseException(e.getMessage(), e);
    }
  }

  public MessageParser(MimeMessage mimeMessage) {
    this.mimeMessage = mimeMessage;
  }

  public MessageParser(File eml) {
    try {
      InputStream inputStream = new FileInputStream(eml);
      Session session = MessageContext.initializeSession();
      this.mimeMessage = new MimeMessage(session, inputStream);
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage(), e);
      throw new MessageParseException(e.getMessage(), e);
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
      throw new MessageParseException(e.getMessage(), e);
    }
  }

  public String from() {
    try {
      Address[] addresses = mimeMessage.getFrom();
      if(addresses != null && addresses.length > 0) {
        Address address = addresses[0];
        if(address instanceof InternetAddress) {
          InternetAddress internetAddress = (InternetAddress) address;
          return CipherUtil.decodeText(internetAddress.getAddress());
        } else {
          String text = CipherUtil.decodeText(address.toString());
          return text.substring((text.lastIndexOf("<") + 1), (text.lastIndexOf(">") - 1));
        }
      }
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public String fromName() {
    try {
      Address[] addresses = mimeMessage.getFrom();
      if(addresses != null && addresses.length > 0) {
        Address address = addresses[0];
        if(address instanceof InternetAddress) {
          InternetAddress internetAddress = (InternetAddress) address;
          return CipherUtil.decodeText(internetAddress.getPersonal());
        } else {
          String text = CipherUtil.decodeText(address.toString());
          return text.substring(0, (text.lastIndexOf("<") - 1));
        }
      }
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public String subject() {
    try {
      String subject = mimeMessage.getSubject();
      return CipherUtil.decodeText(subject);
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public List<Recipient> allRecipients() {
    List<Recipient> recipients = new ArrayList<Recipient>();
    recipients.addAll(getRecipient(MimeMessage.RecipientType.TO));
    recipients.addAll(getRecipient(MimeMessage.RecipientType.CC));
    recipients.addAll(getRecipient(MimeMessage.RecipientType.BCC));
    return recipients;
  }

  public List<Recipient> getRecipient(Message.RecipientType recipientType) {
    List<Recipient> recipients = new ArrayList<Recipient>();
    try {
      Address[] addresses = mimeMessage.getRecipients(recipientType);
      if(addresses != null) {
        for(Address address : addresses) {
          if(address instanceof InternetAddress) {
            InternetAddress internetAddress = (InternetAddress) address;
            String personal = internetAddress.getPersonal();
            String personalAddress = internetAddress.getAddress();
            if(personal == null) {
              personal = personalAddress.substring(0, personalAddress.lastIndexOf("@"));
            }
            recipients.add(new Recipient(CipherUtil.decodeText(personal),
                CipherUtil.decodeText(personalAddress), recipientType));
          } else {
            String text = CipherUtil.decodeText(address.toString());
            recipients.add(new Recipient(text.substring(0, (text.lastIndexOf("<") - 1)),
                text.substring((text.lastIndexOf("<") + 1), (text.lastIndexOf(">") - 1)),
                recipientType));
          }
        }
      }
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return recipients;
  }

  public Message.RecipientType recipientType(String type) {
    if("to".equalsIgnoreCase(type)) {
      return Message.RecipientType.TO;
    } else if("bcc".equalsIgnoreCase(type)) {
      return Message.RecipientType.BCC;
    } else if("cc".equalsIgnoreCase(type)) {
      return Message.RecipientType.CC;
    }
    return null;
  }

  private SimpleDateFormat simpleDateFormat;
  public void setSimpleDateFormat(String pattern) {
    this.simpleDateFormat = new SimpleDateFormat(pattern);
  }
  public SimpleDateFormat getSimpleDateFormat() {
    if(this.simpleDateFormat == null)
      this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return this.simpleDateFormat;
  }

  public String sendDate() {
    try {
      Date date = mimeMessage.getSentDate();
      if(date == null)
        return null;
      return this.getSimpleDateFormat().format(date);
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public String receivedDate() {
    try {
      Date date = mimeMessage.getReceivedDate();
      if(date == null)
        return null;
      return this.getSimpleDateFormat().format(date);
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public Map<String, String> headers() {
    Map<String, String> headers = new ConcurrentHashMap<String, String>();
    try {
      Enumeration enumerations = mimeMessage.getAllHeaders();
      if(enumerations != null) {
        while(enumerations.hasMoreElements()) {
          Object object = enumerations.nextElement();
          if(object instanceof Header) {
            Header header = (Header) object;
            headers.put(header.getName(), CipherUtil.decodeText(header.getValue()));
          }
        }
      }
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return headers;
  }

  public String textHtml() {
    return content();
  }

  /**
   * 邮件内容
   * @return
   */
  public String content() {
    StringBuffer body = new StringBuffer();
    try {
      Object object = mimeMessage.getContent();
      if(mimeMessage.isMimeType("text/plain")) {
        body.append(object);
      } else if(mimeMessage.isMimeType("text/html")) {
        body.append(object);
      } else if(mimeMessage.isMimeType("multipart/*")) {
        MimeMultipart mimeMultipart = (MimeMultipart) object;
        int mimeMultipartCount = mimeMultipart.getCount();
        for(int i=0; i < mimeMultipartCount; i++) {
          bodyRecursive(mimeMultipart.getBodyPart(i));
        }
        body.append(bodyBuffer.toString());
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    String bodyInfo = body.toString();
    /*if(this.embeddedImages.size() > 0) {
      for(EmbeddedImage image : this.embeddedImages) {
        bodyInfo = bodyInfo.replace(image.getEmbeddedId(), image.getImagePath());
      }
    }*/
    return bodyInfo;
  }

  private StringBuffer bodyBuffer = new StringBuffer();
  public void clearBodyBuffer() {
    if(bodyBuffer.length() > 0)
      bodyBuffer.setLength(0);
  }
  public String bodyRecursive(Part part) {
    try {
      String contentType = part.getContentType();
      int nameIndex = contentType.indexOf("name");
      boolean conName = false;
      if (nameIndex != -1)
        conName = true;
      logger.info("contentType: {}", contentType);
      if (part.isMimeType("text/plain") && !conName) {
        //bodyBuffer.append((String) part.getContent());
      } else if (part.isMimeType("text/html") && !conName) {
        this.clearBodyBuffer();
        bodyBuffer.append((String) part.getContent());
      } else if(part.isMimeType("image/*")) {
        this.embeddedImageDataHandler(part);
      } else if(part.isMimeType("application/octet-stream")) {
        //Attach
      } else if (part.isMimeType("multipart/*")) {
        Multipart multipart = (Multipart) part.getContent();
        int counts = multipart.getCount();
        for (int i = 0; i < counts; i++) {
          bodyRecursive(multipart.getBodyPart(i));
        }
      } else if (part.isMimeType("message/rfc822")) {
        bodyRecursive((Part) part.getContent());
      }
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    return bodyBuffer.toString();
  }

  public void embeddedImageDataHandler(Part part) {
    try {
      EmbeddedImage image = new EmbeddedImage();
      DataHandler dataHandler = part.getDataHandler();
      String name = dataHandler.getName();
      image.setOriginalImageName(name);
      String suffix = name.substring(name.lastIndexOf("."), name.length());
      String[] contentIds = part.getHeader("Content-ID");
      if (contentIds != null) {
        String contentId = contentIds[0];
        if(contentId.startsWith("<"))
          contentId = contentId.substring(1, contentId.length());
        if(contentId.endsWith(">"))
          contentId = contentId.substring(0, contentId.length() - 1);
        image.setEmbeddedId("cid:" + contentId);
        image.setImageName(contentId + suffix);
      } else {
        image.setEmbeddedId(image.getOriginalImageName());
        image.setImageName(image.getOriginalImageName());
      }
      image.setDataSource(dataHandler.getDataSource());
      image.setInputStream(dataHandler.getInputStream());
      //image.setImagePath(new ResourcesManager().upload(image.getInputStream(), image.getImageName()));
      this.embeddedImages.add(image);
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  List<EmbeddedImage> embeddedImages = new ArrayList<EmbeddedImage>();
  public List<EmbeddedImage> embeddedImageList() {
    return this.embeddedImages;
  }
  public void clearEmbeddedImages() {this.embeddedImages.clear();}

  public String messageId() {
    try {
      return mimeMessage.getMessageID();
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public String disposition() {
    try {
      return mimeMessage.getDisposition();
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public String description() {
    try {
      return mimeMessage.getDescription();
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public List<String> replyTo() {
    List<String> replyTos = new ArrayList<String>();
    try {
      Address[] addresses = mimeMessage.getReplyTo();
      if(addresses != null) {
        for(Address address : addresses) {
          replyTos.add(CipherUtil.decodeText(address.toString()));
        }
      }
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return replyTos;
  }

  public String contentType() {
    try {
      return mimeMessage.getContentType();
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public String encoding() {
    try {
      return mimeMessage.getEncoding();
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public boolean replySign() {
    try {
      String notification[] = mimeMessage.getHeader("Disposition-Notification-To");
      return notification != null ? true : false;
    } catch (MessagingException e) {
      logger.error(e.getMessage(), e);
    }
    return false;
  }

  public boolean containAttachment() {
    return false;
  }

  public SimpleEmail parse() {
    SimpleEmail simpleEmail = new SimpleEmail();
    try {
      simpleEmail.setFrom(this.from());
      simpleEmail.setFromName(this.fromName());
      simpleEmail.setSubject(this.subject());
      simpleEmail.setRecipients(this.allRecipients());
      simpleEmail.setReplyTo(this.replyTo());
      simpleEmail.setHeaders(this.headers());
      simpleEmail.setTextHtml(this.textHtml());
      simpleEmail.setEmbeddedImages(this.embeddedImageList());
      simpleEmail.setSendDate(this.sendDate());
      simpleEmail.setReceivedDate(this.receivedDate());
      simpleEmail.setMessageId(this.messageId());
      simpleEmail.setContentType(this.contentType());
      simpleEmail.setDescription(this.description());
      simpleEmail.setDisposition(this.disposition());
      simpleEmail.setEncoding(this.encoding());
      simpleEmail.setReplySign(this.replySign());
      simpleEmail.setContainAttachment(this.containAttachment());
      return simpleEmail;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }


}

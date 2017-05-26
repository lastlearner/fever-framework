package com.github.fanfever.fever.mail.parse;

import com.github.fanfever.fever.mail.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author scott he
 * @date 2017/4/28
 */
public class ResourcesManager {
  private final static Logger logger = LoggerFactory.getLogger(ResourcesManager.class);
  private final static boolean IS_LINUX;
  static {
    /*仅验证os: Linux / Windows*/
    IS_LINUX = !System.getProperty("os.name").toUpperCase().contains("WINDOWS");
  }
  public String baseDir() {
    if(IS_LINUX) {
      return "/upload";
    }
    return "F:\\upload";
  }
  public String dateString() {
    return new SimpleDateFormat("yyyyMMdd").format(new Date());
  }
  public String mkdir() {
    return mkdir(baseDir(), dateString());
  }
  public String mkdir(String base, String path) {
    if(base == null)
      base = baseDir();
    if(path == null)
      path = dateString();
    String uploadPathPrefix = base + File.separator + path;
    File basic = new File(uploadPathPrefix);
    if(!basic.exists())
      basic.mkdirs();
    return uploadPathPrefix;
  }

  public String upload(InputStream inputStream, String fileName) {
    String absolutelyPath = mkdir() + File.separator + fileName;
    try {
      File.createTempFile(absolutelyPath, "");
      FileUtils.write(inputStream, absolutelyPath);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    return absolutelyPath;
  }

  public enum Resources {
    IMAGE,
    DOCUMENT;
  }
}

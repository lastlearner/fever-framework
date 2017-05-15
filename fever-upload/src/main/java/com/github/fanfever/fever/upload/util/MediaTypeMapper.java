package com.github.fanfever.fever.upload.util;

import com.github.fanfever.fever.upload.storage.AttachmentExtension;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;

/**
 * @author scott.he
 * @date 2017/4/8
 */
public class MediaTypeMapper {

  /**
   * 指定文件名匹配contentType. <br/>
   *
   * 默认: application/octet-stream, 其它详见{@link AttachmentExtension}
   * @param fileName 文件名
   * @return
   */
  public static MediaType parse(String fileName) {
    return parse(fileName, "application/octet-stream");
  }

  public static MediaType parse(String fileName, String defaultType) {
    if(!StringUtils.isEmpty(fileName) && fileName.indexOf(".") != -1) {
      String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
      for(AttachmentExtension ext : AttachmentExtension.values()) {
        if(ext.getDesc().equalsIgnoreCase(type))
          return MediaType.parseMediaType(ext.getMedia());
      }
    }
    return MediaType.parseMediaType(defaultType);
  }

}

package com.github.fanfever.fever.upload.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

/**
 * @author scott.he
 * @date 2017/4/8
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {
  private String name;
  private String originalFilename;
  private String contentType;
  private boolean empty;
  private long size;
  private byte[] bytes;
  private InputStream inputStream;

}

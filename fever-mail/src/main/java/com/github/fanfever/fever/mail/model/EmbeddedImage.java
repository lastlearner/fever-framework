package com.github.fanfever.fever.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.activation.DataSource;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author scott he
 * @date 2017/4/28
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedImage implements Serializable {
  private String embeddedId;
  private String imageName;
  private String originalImageName;
  private String imagePath;
  private DataSource dataSource;
  private InputStream inputStream;
}

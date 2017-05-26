package com.github.fanfever.fever.mail.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author scott.he
 * @date 2017/4/14
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData implements Serializable {
  private boolean result;
  private String status;
  private String message;
  private String info;
}

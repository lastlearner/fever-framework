package com.github.fanfever.fever.upload.validation;

import org.springframework.context.support.ResourceBundleMessageSource;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author scott.he
 * @date 2017/4/8
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {

  @Resource
  private ResourceBundleMessageSource resourceBundleMessageSource;
  private NotBlank notBlank;
  public void initialize(NotBlank notBlank) {
    this.notBlank = notBlank;
  }

  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    boolean isValid = false;
    if(value != null && value.trim().length() > 0)
      isValid = true;
    if(!isValid) {
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext.buildConstraintViolationWithTemplate(notBlank.message())
          .addConstraintViolation();
    }
    return isValid;
  }
}

package com.github.fanfever.fever.upload.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author scott.he
 * @date 2017/4/8
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotBlankValidator.class})
public @interface NotBlank {
  String message() default "universal.validator.constraints.NotBlank.message";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

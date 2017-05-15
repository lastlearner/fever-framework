package com.github.fanfever.fever.web;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.fanfever.fever.exception.BadRequestException;
import com.github.fanfever.fever.exception.InternalServerErrorException;
import com.github.fanfever.fever.exception.BaseException.ExceptionType;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@ControllerAdvice
public class ControllerValidationHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BadRequestException processValidationError(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
		return processFieldError(fieldErrorList);
	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public Object exceptionHandler(IOException e, HttpServletRequest request) {
		if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")) {
			return null;
		} else {
			return new HttpEntity<>(e.getMessage());
		}
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public InternalServerErrorException internalServerError(RuntimeException ex) {
		return new InternalServerErrorException(ExceptionType.ERROR, ex);
	}

	private BadRequestException processFieldError(List<FieldError> fieldErrorList) {
		BadRequestException badRequestExcetion = null;
		if (CollectionUtils.isNotEmpty(fieldErrorList)) {
//			Locale locale = LocaleContextHolder.getLocale();
//			List<String> messageList = fieldErrorList.stream().map(i -> messageSource.getMessage(i.getDefaultMessage(), null, locale))
//					.collect(Collectors.toList());
//			badRequestExcetion = new BadRequestException(ExceptionType.ERROR, messageList);
			return null;
		}
		return badRequestExcetion;
	}
}


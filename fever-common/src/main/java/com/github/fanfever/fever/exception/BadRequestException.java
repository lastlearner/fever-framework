package com.github.fanfever.fever.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@JsonIgnoreProperties({ "cause", "stackTrace", "localizedMessage", "suppressed", "i18nMessage" })
public @Data @EqualsAndHashCode(callSuper = false)
class BadRequestException extends BaseException {

	private static final long serialVersionUID = 1L;

	@JsonProperty("message")
	private List<String> messageList;

	private Map<String,Object> data;

	private transient I18nMessage i18nMessage;

	public BadRequestException(String code, String... params) {
		super(ExceptionType.ERROR);
		i18nMessage = new I18nMessage(code, params);
		this.messageList = Lists.newArrayListWithCapacity(1);
		this.messageList.add(code);
	}

	public BadRequestException(Map<String,Object> data,String code, String... params) {
		super(ExceptionType.ERROR);
		i18nMessage = new I18nMessage(code, params);
		this.messageList = Lists.newArrayListWithCapacity(1);
		this.messageList.add(code);
		this.setData(data);
	}

	public BadRequestException(List<String> messageList) {
		super(ExceptionType.ERROR);
		this.messageList = messageList;
	}

	public BadRequestException(ExceptionType exceptionType, List<String> messageList) {
		super(exceptionType);
		this.messageList = messageList;
	}
}

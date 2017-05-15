package com.github.fanfever.fever.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public @Getter @Setter class BadRequestException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("message")
	private List<String> messageList;

	public BadRequestException(String message) {
		this.type = ExceptionType.ERROR;
		this.messageList = Lists.newArrayList();
		this.messageList.add(message);
	}

	public BadRequestException(ExceptionType exceptionType, List<String> messageList) {
		this.type = exceptionType;
		this.messageList = messageList;
	}

}

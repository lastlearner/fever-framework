package com.github.fanfever.fever.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月11日
 */
public @Getter @Setter class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ExceptionType type;

	protected BaseException() {
	}

	public enum ExceptionType {
		INFO, WARNING, ERROR
	}
}

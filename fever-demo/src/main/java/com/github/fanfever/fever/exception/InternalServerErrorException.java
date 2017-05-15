package com.github.fanfever.fever.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月11日
 */
public @Getter @Setter class InternalServerErrorException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ExceptionType type;
	@JsonProperty("cause")
	private String causes;
	@JsonProperty("stackTrace")
	private List<StackTraceElement> stackTraceList;

	public InternalServerErrorException(ExceptionType type, RuntimeException ex) {
		this.type = type;
		this.causes = ex.getClass().getName();
		// this.stackTraceList = Arrays.asList(ex.getStackTrace()).stream()
		// .filter(i ->
		// i.getClassName().startsWith("com.fever.")).collect(Collectors.toList());
		logger.error(null, ex);
	}

}

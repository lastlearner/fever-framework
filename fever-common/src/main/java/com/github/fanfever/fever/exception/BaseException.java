package com.github.fanfever.fever.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException {

    private ExceptionType type;

    protected BaseException(ExceptionType type) {
        this.type = type;
    }

    public enum ExceptionType {
        INFO, WARNING, ERROR
    }
}

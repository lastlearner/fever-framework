package com.github.fanfever.fever.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月11日
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"logger"})
public class InternalServerErrorException extends BaseException {

    @JsonProperty("message")
    private final String message;
    @JsonProperty("cause")
    private final String causes;
    @JsonProperty("stackTrace")
    private final List<StackTraceElement> stackTraceList;

    public InternalServerErrorException(ExceptionType type, Exception ex) {
        super(type);
        this.message = ex.getMessage();
        this.causes = ex.getClass().getName();
        this.stackTraceList = Arrays.stream(ex.getStackTrace())
                .filter(i -> i.getClassName().startsWith("com.github.fanfever.fever.") || i.getClassName().startsWith("cn.udesk."))
                .collect(Collectors.toList());
        log.error(causes, ex);
    }

}

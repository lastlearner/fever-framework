package com.github.fanfever.fever.exception;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jirui on 2017/4/25.
 */
@Data
public class I18nMessage {
    private String code;
    private List<String> paramList;

    public I18nMessage(String code, String... params) {
        this.code = code;
        paramList = Lists.newArrayListWithCapacity(params.length);
        Arrays.stream(params).forEach(arg ->
                paramList.add(arg)
        );
    }
}

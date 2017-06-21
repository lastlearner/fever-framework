package com.github.fanfever.fever.condition.request;

import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.type.ValueType;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by fanfever on 2017/5/10.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Data
@AllArgsConstructor
public abstract class BaseConditionRequest {

    private String fieldName;
    private ValueType valueType;
    private Operator operator;
    private Object value;


    public String getValueStr() {
        return String.valueOf(value).trim();
    }

    @SuppressWarnings("unchecked")
    public List<String> getValueArray() {
//        return null == value ? new String[]{} : String.valueOf(value).split(",");
        if (null == value) {
            return Collections.emptyList();
        }
        if (value instanceof List) {
            return (List) value;
        }
        if (value instanceof Set) {
            return Lists.newArrayList((Set) value);
        }
        if (value instanceof Object[]) {
            return Lists.newArrayList((String[]) value);
        }
        if (value instanceof String) {
            return Lists.newArrayList(String.valueOf(value).split(","));
        }
        throw new AssertionError();
    }

}

package com.github.fanfever.fever.condition.request;

import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.type.ValueType;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

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

    public List<String> getValueArray() {
//        return null == value ? new String[]{} : String.valueOf(value).split(",");
        return null == value ? Collections.emptyList() : Lists.newArrayList(String.valueOf(value).split(","));
    }

}

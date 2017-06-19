package com.github.fanfever.fever.condition.request;

import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.type.ValueType;
import lombok.Data;

/**
 * Created by fanfever on 2017/6/20.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Data
public class MemoryConditionRequest extends BaseConditionRequest{

    private Object memoryObject;

    public MemoryConditionRequest(Object memoryObject, String fieldName, ValueType valueType, Operator operator, Object value) {
        super(fieldName, valueType, operator, value);
        this.memoryObject = memoryObject;
    }
}

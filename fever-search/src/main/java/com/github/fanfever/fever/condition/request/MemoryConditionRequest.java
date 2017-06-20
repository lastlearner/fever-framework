package com.github.fanfever.fever.condition.request;

import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.type.ValueType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Created by fanfever on 2017/6/20.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemoryConditionRequest extends BaseConditionRequest {

    private Object memoryObject;

    private MemoryConditionRequest(Object memoryObject, String fieldName, ValueType valueType, Operator operator, Object value) {
        super(fieldName, valueType, operator, value);
        this.memoryObject = memoryObject;
    }

    public static MemoryConditionRequest of(@NonNull Object memoryObject, @NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value) {
        return new MemoryConditionRequest(memoryObject, fieldName, valueType, operator, value);
    }
}

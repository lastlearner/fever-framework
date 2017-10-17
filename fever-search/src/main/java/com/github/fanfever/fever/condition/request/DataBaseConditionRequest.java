package com.github.fanfever.fever.condition.request;

import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.type.ValueType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;

/**
 * Created by fanfever on 2017/6/20.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataBaseConditionRequest extends BaseConditionRequest {

    /**
     * mysql使用
     */
    private String alias;

    private DataBaseConditionRequest(String alias, String fieldName, ValueType valueType, Operator operator, Object value) {
        super(fieldName, valueType, operator, value);
        this.alias = alias;
    }

    private DataBaseConditionRequest(Boolean result){
        super(result);
    }

    public static DataBaseConditionRequest of(@NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value, String alias) {
        return new DataBaseConditionRequest(alias, fieldName, valueType, operator, value);
    }

    public static DataBaseConditionRequest of(Boolean result){
        return new DataBaseConditionRequest(result);
    }
}

package com.github.fanfever.fever.condition.request;

import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.type.ValueType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by fanfever on 2017/6/20.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Data
public class DataBaseConditionRequest extends BaseConditionRequest {

    private String tableAlias;

    public DataBaseConditionRequest(String fieldName, ValueType valueType, Operator operator, Object value) {
        super(fieldName, valueType, operator, value);
    }

    public DataBaseConditionRequest(String tableAlias, String fieldName, ValueType valueType, Operator operator, Object value) {
        this(fieldName, valueType, operator, value);
        this.tableAlias = tableAlias;
    }
}

package com.github.fanfever.fever.condition.operator;

import com.github.fanfever.fever.condition.type.ValueType;
import com.github.fanfever.fever.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

/**
 * Created by fanfever on 2017/5/6.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */

public enum Operator {

    /**
     * 等于
     */
    IS("is", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.ARRAY}),


    /**
     * 不等于
     */
    NOT("not", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.ARRAY}),

    /**
     * 开头等于
     */
    PREFIX_CONTAINS("prefix_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),

    /**
     * 开头不等于
     */
    PREFIX_NOT_CONTAINS("prefix_not_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),

    /**
     * 结尾等于
     */
    SUFFIX_CONTAINS("suffix_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),

    /**
     * 结尾不等于
     */
    SUFFIX_NOT_CONTAINS("suffix_not_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),

    /**
     * 包含
     */
    CONTAINS("contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),

    /**
     * 不包含
     */
    NOT_CONTAINS("not_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),

    /**
     * 包含任意
     */
    CONTAINS_ANY("contains_any", new ValueType[]{ValueType.ARRAY}),

    /**
     * 不包含任意
     */
    NOT_CONTAINS_ANY("not_contains_any", new ValueType[]{ValueType.ARRAY}),

    /**
     * 为空
     */
    IS_NULL("is_null", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.ARRAY}),

    /**
     * 不为空
     */
    IS_NOT_NULL("is_not_null", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.ARRAY}),

    /**
     * 大于
     */
    GREATER_THAN("greater_than", new ValueType[]{ValueType.TIME, ValueType.NUMERIC}),

    /**
     * 大于等于
     */
    GREATER_THAN_EQ("greater_than_eq", new ValueType[]{ValueType.TIME, ValueType.NUMERIC}),

    /**
     * 小于
     */
    LESS_THAN("less_than", new ValueType[]{ValueType.TIME, ValueType.NUMERIC}),

    /**
     * 小于等于
     */
    LESS_THAN_EQ("less_than_eq", new ValueType[]{ValueType.TIME, ValueType.NUMERIC}),

    /**
     * 今天
     */
    TODAY("today", new ValueType[]{ValueType.TIME}),

    /**
     * 昨天
     */
    YESTERDAY("yesterday", new ValueType[]{ValueType.TIME}),

    /**
     * 明天
     */
    TOMORROW("tomorrow", new ValueType[]{ValueType.TIME}),

    /**
     * 今后7天
     */
    NEXT_SEVEN_DAY("next_seven_day", new ValueType[]{ValueType.TIME}),

    /**
     * 最近7天
     */
    LAST_SEVEN_DAY("last_seven_day", new ValueType[]{ValueType.TIME}),

    /**
     * 早于（包含当天）
     */
    BEFORE("before", new ValueType[]{ValueType.TIME}),

    /**
     * 晚于（包含当天）
     */
    AFTER("after", new ValueType[]{ValueType.TIME}),

    /**
     * 本周
     */
    THIS_WEEK("this_week", new ValueType[]{ValueType.TIME}),

    /**
     * 上周
     */
    LAST_WEEK("last_week", new ValueType[]{ValueType.TIME}),

    /**
     * 下周
     */
    NEXT_WEEK("next_week", new ValueType[]{ValueType.TIME}),

    /**
     * 本月
     */
    THIS_MONTH("this_month", new ValueType[]{ValueType.TIME}),

    /**
     * 上月
     */
    LAST_MONTH("last_month", new ValueType[]{ValueType.TIME}),

    /**
     * 下月
     */
    NEXT_MONTH("next_month", new ValueType[]{ValueType.TIME}),

    /**
     * 今年
     */
    THIS_YEAR("this_year", new ValueType[]{ValueType.TIME}),

    /**
     * 去年
     */
    LAST_YEAR("last_year", new ValueType[]{ValueType.TIME}),

    /**
     * 明年
     */
    NEXT_YEAR("next_year", new ValueType[]{ValueType.TIME}),

    /**
     * 任意等于
     */
    IS_ANY("is_any", new ValueType[]{ValueType.ARRAY}),

    /**
     * 任意不等于
     */
    NOT_ANY("not_any", new ValueType[]{ValueType.ARRAY}),

    /**
     * 任意开头等于
     */
    PREFIX_CONTAINS_ANY("prefix_contains_any", new ValueType[]{ValueType.ARRAY}),

    /**
     * 任意开头不等于
     */
    PREFIX_NOT_CONTAINS_ANY("prefix_not_contains_any", new ValueType[]{ValueType.ARRAY}),

    /**
     * 任意结尾等于
     */
    SUFFIX_CONTAINS_ANY("suffix_contains_any", new ValueType[]{ValueType.ARRAY}),

    /**
     * 任意结尾不等于
     */
    SUFFIX_NOT_CONTAINS_ANY("suffix_not_contains_any", new ValueType[]{ValueType.ARRAY});

    @Getter
    private final String value;

    private ValueType[] valueType = null;

    Operator(@NonNull final String value) {
        this.value = value;
    }

    Operator(@NonNull final String value, final ValueType[] valueTypes) {
        this.value = value;
        this.valueType = valueTypes;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Operator getEnumByValue(@NonNull final String value){
        return Arrays.stream(Operator.values()).filter(i -> i.value.equals(value)).findFirst().orElseThrow(() -> new AssertionError("not support operator type! "));
    }

}

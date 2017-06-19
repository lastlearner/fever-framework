package com.github.fanfever.fever.condition.operator;

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
    IS("is"),


    /**
     * 不等于
     */
    NOT("not"),

    /**
     * 开头等于
     */
    PREFIX_CONTAINS("prefix_contains"),

    /**
     * 开头不等于
     */
    PREFIX_NOT_CONTAINS("prefix_not_contains"),

    /**
     * 结尾等于
     */
    SUFFIX_CONTAINS("suffix_contains"),

    /**
     * 结尾不等于
     */
    SUFFIX_NOT_CONTAINS("suffix_not_contains"),

    /**
     * 包含
     */
    CONTAINS("contains"),

    /**
     * 不包含
     */
    NOT_CONTAINS("not_contains"),

    /**
     * 包含任意
     */
    CONTAINS_ANY("contains_any"),

    /**
     * 不包含任意
     */
    NOT_CONTAINS_ANY("not_contains_any"),

    /**
     * 为空
     */
    IS_NULL("is_null"),

    /**
     * 不为空
     */
    IS_NOT_NULL("is_not_null"),

    /**
     * 大于
     */
    GREATER_THAN("greater_than"),

    /**
     * 大于等于
     */
    GREATER_THAN_EQ("greater_than_eq"),

    /**
     * 小于
     */
    LESS_THAN("less_than"),

    /**
     * 小于等于
     */
    LESS_THAN_EQ("less_than_eq"),

    /**
     * 今天
     */
    TODAY("today"),

    /**
     * 昨天
     */
    YESTERDAY("yesterday"),

    /**
     * 明天
     */
    TOMORROW("tomorrow"),

    /**
     * 今后7天
     */
    NEXT_SEVEN_DAY("next_seven_day"),

    /**
     * 最近7天
     */
    LAST_SEVEN_DAY("last_seven_day"),

    /**
     * 早于（包含当天）
     */
    BEFORE("before"),

    /**
     * 晚于（包含当天）
     */
    AFTER("after"),

    /**
     * 本周
     */
    THIS_WEEK("this_week"),

    /**
     * 上周
     */
    LAST_WEEK("last_week"),

    /**
     * 下周
     */
    NEXT_WEEK("next_week"),

    /**
     * 本月
     */
    THIS_MONTH("this_month"),

    /**
     * 上月
     */
    LAST_MONTH("last_month"),

    /**
     * 下月
     */
    NEXT_MONTH("next_month"),

    /**
     * 今年
     */
    THIS_YEAR("this_year"),

    /**
     * 去年
     */
    LAST_YEAR("last_year"),

    /**
     * 明年
     */
    NEXT_YEAR("next_year");

    @Getter
    private final String value;

    Operator(@NonNull final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Operator getEnumByValue(@NonNull final String value){
        return Arrays.stream(Operator.values()).filter(i -> i.value.equals(value)).findFirst().orElseThrow(AssertionError::new);
    }

}

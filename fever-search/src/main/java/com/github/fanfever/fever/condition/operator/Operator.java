package com.github.fanfever.fever.condition.operator;

import com.github.fanfever.fever.condition.type.ValueType;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.github.fanfever.fever.condition.type.ValueType.*;

/**
 * Created by fanfever on 2017/5/6.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */

public enum Operator {

    /**
     * 等于
     */
    IS("is", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY),


    /**
     * 不等于
     */
    NOT("not", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY),

    /**
     * 开头等于
     */
    PREFIX_CONTAINS("prefix_contains", TEXT, LONG_TEXT),

    /**
     * 开头不等于
     */
    PREFIX_NOT_CONTAINS("prefix_not_contains", TEXT, LONG_TEXT),

    /**
     * 结尾等于
     */
    SUFFIX_CONTAINS("suffix_contains", TEXT, LONG_TEXT),

    /**
     * 结尾不等于
     */
    SUFFIX_NOT_CONTAINS("suffix_not_contains", TEXT, LONG_TEXT),

    /**
     * 包含
     */
    CONTAINS("contains", TEXT, LONG_TEXT),

    /**
     * 不包含
     */
    NOT_CONTAINS("not_contains", TEXT, LONG_TEXT),

    /**
     * 包含任意
     */
    CONTAINS_ANY("contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 不包含任意
     */
    NOT_CONTAINS_ANY("not_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 为空
     */
    IS_NULL("is_null", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY),

    /**
     * 不为空
     */
    IS_NOT_NULL("is_not_null", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY),

    /**
     * 大于
     */
    GREATER_THAN("greater_than", TIME, NUMERIC,TEXT),

    /**
     * 大于等于
     */
    GREATER_THAN_EQ("greater_than_eq", TIME, NUMERIC,TEXT),

    /**
     * 小于
     */
    LESS_THAN("less_than", TIME, NUMERIC,TEXT),

    /**
     * 小于等于
     */
    LESS_THAN_EQ("less_than_eq", TIME, NUMERIC,TEXT),

    /**
     * 今天
     */
    TODAY("today", TIME),

    /**
     * 昨天
     */
    YESTERDAY("yesterday", TIME),

    /**
     * 明天
     */
    TOMORROW("tomorrow", TIME),

    /**
     * 今后7天
     */
    NEXT_SEVEN_DAY("next_seven_day", TIME),

    /**
     * 最近7天
     */
    LAST_SEVEN_DAY("last_seven_day", TIME),

    /**
     * 早于（包含当天）
     */
    BEFORE("before", TIME),

    /**
     * 晚于（包含当天）
     */
    AFTER("after", TIME),

    /**
     * 本周
     */
    THIS_WEEK("this_week", TIME),

    /**
     * 上周
     */
    LAST_WEEK("last_week", TIME),

    /**
     * 下周
     */
    NEXT_WEEK("next_week", TIME),

    /**
     * 本月
     */
    THIS_MONTH("this_month", TIME),

    /**
     * 上月
     */
    LAST_MONTH("last_month", TIME),

    /**
     * 下月
     */
    NEXT_MONTH("next_month", TIME),

    /**
     * 今年
     */
    THIS_YEAR("this_year", TIME),

    /**
     * 去年
     */
    LAST_YEAR("last_year", TIME),

    /**
     * 明年
     */
    NEXT_YEAR("next_year", TIME),

    /**
     * 任意等于
     */
    IS_ANY("is_any", COMMA_SPLIT, ARRAY, NUMERIC),

    /**
     * 任意不等于
     */
    NOT_ANY("not_any", COMMA_SPLIT, ARRAY, NUMERIC),

    /**
     * 任意开头等于
     */
    PREFIX_CONTAINS_ANY("prefix_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 任意开头不等于
     */
    PREFIX_NOT_CONTAINS_ANY("prefix_not_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 任意结尾等于
     */
    SUFFIX_CONTAINS_ANY("suffix_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 任意结尾不等于
     */
    SUFFIX_NOT_CONTAINS_ANY("suffix_not_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 介于
     */
    BETWEEN("between",TIME),

    /**
     * 不介于
     */
    NOT_BETWEEN("not_between",TIME),

    /**
     * 不在(日期时间）
     */
    NOT_IN_DATE("not_in_date",TIME),

    /**
     * 包含所有
     */
    CONTAINS_ALL("contains_all"),

    /**
     * 不包含所有
     */
    NOT_CONTAINS_ALL("not_contains_all");


    @Getter
    private final String value;

    @Getter
    private final ValueType[] valueTypes;

    Operator(@NonNull final String value, final ValueType... valueTypes) {
        this.value = value;
        this.valueTypes = valueTypes;
    }

    public static Operator getByValue(@NonNull final String value) {
        return Arrays.stream(Operator.values()).filter(i -> i.value.equals(value)).findFirst().orElseThrow(() -> new AssertionError("not support operator! "));
    }

    public static boolean isSupport(@NonNull ValueType valueType, @NonNull Operator operator) {
        return Stream.of(Operator.values()).anyMatch(i -> i.equals(operator) && Stream.of(i.getValueTypes()).anyMatch(j -> j.equals(valueType)));
    }

}

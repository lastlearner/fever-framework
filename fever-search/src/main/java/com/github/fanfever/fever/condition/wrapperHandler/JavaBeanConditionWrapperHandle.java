package com.github.fanfever.fever.condition.wrapperHandler;

import com.github.fanfever.fever.condition.request.BaseConditionRequest;
import com.github.fanfever.fever.condition.request.MemoryConditionRequest;
import com.github.fanfever.fever.util.ReflectionUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.fanfever.fever.condition.type.ValueType.*;

/**
 * Created by fanfever on 2017/5/6.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
public interface JavaBeanConditionWrapperHandle extends ConditionWrapperHandle {

    static Object getValue(BaseConditionRequest condition) {
        return ReflectionUtils.invokeGetter(((MemoryConditionRequest) condition).getMemoryObject(), condition.getFieldName());
    }

    static ConditionWrapperHandle iSHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof BigDecimal) {
                return 0 == ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString()));
            } else if (value instanceof LocalDateTime) {
                return ((LocalDateTime) value).isEqual((LocalDateTime) condition.getValue());
            } else {
                return condition.getValue().equals(value);
            }
        };
    }

    static ConditionWrapperHandle notHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof BigDecimal) {
                return 0 != ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString()));
            } else if (value instanceof LocalDateTime) {
                return !((LocalDateTime) value).isEqual((LocalDateTime) condition.getValue());
            } else {
                return !condition.getValue().equals(value);
            }
        };
    }


    static ConditionWrapperHandle prefixContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return String.valueOf(value).startsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return !String.valueOf(value).startsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return String.valueOf(value).endsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return !String.valueOf(value).endsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (isText(condition.getValueType()) && value instanceof String) {
                return String.valueOf(value).contains(condition.getValueStr());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (isText(condition.getValueType()) && value instanceof String) {
                return !String.valueOf(value).contains(condition.getValueStr());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isAnyHandle() {
        return condition -> {
            List value = Lists.newArrayList(((String) getValue(condition)).split(","));
            if (condition.getValueType().equals(ARRAY)) {
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    if (value.contains(condition.getValueArray().get(i))) {
                        return true;
                    }
                }
                return false;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notAnyHandle() {
        return condition -> {
            List value = Lists.newArrayList(((String) getValue(condition)).split(","));
            if (condition.getValueType().equals(ARRAY)) {
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    if (value.contains(condition.getValueArray().get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAnyHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(ARRAY)) {
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    if (String.valueOf(value).contains(condition.getValueArray().get(i))) {
                        return true;
                    }
                }
                return false;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsAnyHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(ARRAY)) {
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    if (String.valueOf(value).contains(condition.getValueArray().get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (int i = 0; i < value.size(); i++) {
                    if (String.valueOf(value.get(i)).startsWith(condition.getValueStr())) {
                        return true;
                    }
                }
                return false;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (int i = 0; i < value.size(); i++) {
                    if (String.valueOf(value.get(i)).startsWith(condition.getValueStr())) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (int i = 0; i < value.size(); i++) {
                    if (String.valueOf(value.get(i)).endsWith(condition.getValueStr())) {
                        return true;
                    }
                }
                return false;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (int i = 0; i < value.size(); i++) {
                    if (String.valueOf(value.get(i)).endsWith(condition.getValueStr())) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isNullHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof String) {
                return StringUtils.isBlank(String.valueOf(value));
            } else if (value instanceof List) {
                return CollectionUtils.isEmpty((List) value);
            } else {
                return null == value;
            }
        };
    }

    static ConditionWrapperHandle isNotNullHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof String) {
                return !StringUtils.isBlank(String.valueOf(value));
            } else if (value instanceof List) {
                return CollectionUtils.isNotEmpty((List) value);
            } else {
                return null != value;
            }
        };
    }

    static ConditionWrapperHandle greaterThanHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 < ((BigDecimal) value).compareTo((BigDecimal) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 < ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle greaterThanEqHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 < ((BigDecimal) value).compareTo((BigDecimal) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 < ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 > ((BigDecimal) value).compareTo((BigDecimal) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 > ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanEqHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 >= ((BigDecimal) value).compareTo((BigDecimal) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 >= ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle todayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle yesterdayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().minusDays(1L).isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle tomorrowHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().plusDays(1L).isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate date = ((LocalDateTime) getValue(condition)).toLocalDate();
                return date.isAfter(LocalDate.now()) && date.isBefore(LocalDate.now().plusDays(7L));
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate date = ((LocalDateTime) getValue(condition)).toLocalDate();
                return date.isAfter(LocalDate.now().minusDays(7L)) && date.isBefore(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle beforeHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isBefore(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isAfter(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isAfter(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
//                return getJoiner().add("YEAR(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").add("AND").add("MONTH(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("MONTH(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
//                return getJoiner().add("YEAR(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").add("AND").add("MONTH(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("MONTH(NOW() - INTERVAL 1 MONTH)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
//                return getJoiner().add("YEAR(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").add("AND").add("MONTH(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("MONTH(NOW() + INTERVAL 1 MONTH)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
//                return getJoiner().add("YEAR(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
//                return getJoiner().add("YEAR(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("YEAR(NOW() - INTERVAL 1 YEAR)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
//                return getJoiner().add("YEAR(" + condition.getObjectName() + "." + condition.getFieldName() + ")").add("=").add("YEAR(NOW() + INTERVAL 1 YEAR)").toString();
            }
            return notFoundOperation();
        };
    }

    static Void notFoundOperation() {
        throw new AssertionError("opration is not exists!");
    }
}

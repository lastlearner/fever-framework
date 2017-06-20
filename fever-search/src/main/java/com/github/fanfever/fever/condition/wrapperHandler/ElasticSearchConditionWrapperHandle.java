package com.github.fanfever.fever.condition.wrapperHandler;

import org.elasticsearch.index.query.BoolQueryBuilder;

import static com.github.fanfever.fever.condition.type.ValueType.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by fanfever on 2017/5/6.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
public interface ElasticSearchConditionWrapperHandle extends ConditionWrapperHandle {

    String RAW = ".raw";

    String ANALYZER = ".index_analyzer";

    static ConditionWrapperHandle iSHandle() {

        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.must(termQuery(condition.getFieldName() + RAW, i)));
                return tempBoolQuery.toString();
            } else if (isText(condition.getValueType())) {
                return boolQuery().must(termQuery(condition.getFieldName() + RAW, condition.getValueStr())).toString();
            } else {
                return boolQuery().must(termQuery(condition.getFieldName(), condition.getValue())).toString();
            }
        };
    }

    static ConditionWrapperHandle notHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(termQuery(condition.getFieldName() + RAW, i)));
                return tempBoolQuery.toString();
            } else if (isText(condition.getValueType())) {
                return boolQuery().mustNot(termQuery(condition.getFieldName() + RAW, condition.getValueStr())).toString();
            } else {
                return boolQuery().mustNot(termQuery(condition.getFieldName(), (condition.getValue()))).toString();
            }
        };
    }

    static ConditionWrapperHandle prefixContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().must(matchPhrasePrefixQuery(condition.getFieldName() + ANALYZER, condition.getValueStr())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().mustNot(matchPhrasePrefixQuery(condition.getFieldName() + RAW, condition.getValueStr())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().must(wildcardQuery(condition.getFieldName() + RAW, "*" + (condition.getValueStr()))).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().mustNot(wildcardQuery(condition.getFieldName() + RAW, "*" + (condition.getValueStr()))).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().must(matchPhraseQuery(condition.getFieldName() + ANALYZER, condition.getValueStr())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().mustNot(matchPhraseQuery(condition.getFieldName() + ANALYZER, condition.getValueStr())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(termQuery(condition.getFieldName() + RAW, i)));
                return tempBoolQuery.minimumShouldMatch(1).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(termQuery(condition.getFieldName() + RAW, i)));
                return tempBoolQuery.toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(matchPhraseQuery(condition.getFieldName() + ANALYZER, i)));
                return tempBoolQuery.minimumShouldMatch(1).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(matchPhraseQuery(condition.getFieldName() + RAW, i)));
                return tempBoolQuery.toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(matchPhrasePrefixQuery(condition.getFieldName() + ANALYZER, i)));
                return tempBoolQuery.minimumShouldMatch(1).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(matchPhrasePrefixQuery(condition.getFieldName() + ANALYZER, i)));
                return tempBoolQuery.minimumShouldMatch(1).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(termQuery(condition.getFieldName() + RAW, "*" + i)));
                return tempBoolQuery.minimumShouldMatch(1).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsAnyHandle() {
        return condition -> {
            if (condition.getValueType().equals(ARRAY)) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(termQuery(condition.getFieldName() + RAW, "*" + i)));
                return tempBoolQuery.minimumShouldMatch(1).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isNullHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().must(boolQuery().should(termQuery(condition.getFieldName() + RAW, "")).should(boolQuery().mustNot(existsQuery(condition.getFieldName()))).minimumShouldMatch("1")).toString();
            } else {
                boolQuery().mustNot(existsQuery(condition.getFieldName()));
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isNotNullHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return boolQuery().must(existsQuery(condition.getFieldName())).mustNot(termQuery(condition.getFieldName() + RAW, "")).toString();
            } else {
                boolQuery().must(existsQuery(condition.getFieldName()));
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gt(condition.getValue())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanEqHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte(condition.getValue())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).lt(condition.getValue())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanEqHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).lte(condition.getValue())).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle todayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/d").lt("now+1d/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle yesterdayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-1d/d").lt("now/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle tomorrowHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now+1d/d").lt("now+2d/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/d").lt("now+7d/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-7d/d").lt("now/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle beforeHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).lte("now/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/w").lt("now+1w/w")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-1w/w").lt("now/w")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now+1w/w").lt("now+2w/w")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/m").lt("now+1m/m")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-1m/m").lt("now/m")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now+1m/m").lt("now+2m/m")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/y").lt("now+1y/y")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-1y/y").lt("now/y")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now+1y/y").lt("now+2y/y")).toString();
            }
            return notFoundOperation();
        };
    }

    static Void notFoundOperation() {
        throw new AssertionError("opration is not exists!");
    }

}

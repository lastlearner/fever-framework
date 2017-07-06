package com.github.fanfever.fever.condition;

import com.github.fanfever.fever.DataSource;
import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.request.BaseConditionRequest;
import com.github.fanfever.fever.condition.request.DataBaseConditionRequest;
import com.github.fanfever.fever.condition.request.MemoryConditionRequest;
import com.github.fanfever.fever.condition.wrapperHandler.ConditionWrapperHandle;
import com.github.fanfever.fever.condition.wrapperHandler.ElasticSearchConditionWrapperHandle;
import com.github.fanfever.fever.condition.wrapperHandler.JavaBeanConditionWrapperHandle;
import com.github.fanfever.fever.condition.wrapperHandler.MySqlConditionWrapperHandle;
import com.github.fanfever.fever.util.FormulaUtil;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.fanfever.fever.condition.type.ValueType.isMultiValue;
import static com.github.fanfever.fever.condition.type.ValueType.isText;
import static com.google.common.base.Preconditions.checkArgument;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by fanfever on 2017/5/6.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
public class ConditionUtils {

    private ConditionUtils() {

    }

    private static final Map<DataSource, Map<Operator, ConditionWrapperHandle>> conditionWrapperHandleMap = Maps.newEnumMap(DataSource.class);

    static {
        Map<Operator, ConditionWrapperHandle> mysqlConditionWrapperHandleMap = Maps.newEnumMap(Operator.class);
        mysqlConditionWrapperHandleMap.put(Operator.IS, MySqlConditionWrapperHandle.iSHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT, MySqlConditionWrapperHandle.notHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS, MySqlConditionWrapperHandle.prefixContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS, MySqlConditionWrapperHandle.prefixNotContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS, MySqlConditionWrapperHandle.suffixContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS, MySqlConditionWrapperHandle.suffixNotContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.CONTAINS, MySqlConditionWrapperHandle.containsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_CONTAINS, MySqlConditionWrapperHandle.notContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.IS_ANY, MySqlConditionWrapperHandle.isAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_ANY, MySqlConditionWrapperHandle.notAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.CONTAINS_ANY, MySqlConditionWrapperHandle.containsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ANY, MySqlConditionWrapperHandle.notContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS_ANY, MySqlConditionWrapperHandle.prefixContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS_ANY, MySqlConditionWrapperHandle.prefixNotContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS_ANY, MySqlConditionWrapperHandle.suffixContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS_ANY, MySqlConditionWrapperHandle.suffixNotContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.IS_NULL, MySqlConditionWrapperHandle.isNullHandle());
        mysqlConditionWrapperHandleMap.put(Operator.IS_NOT_NULL, MySqlConditionWrapperHandle.isNotNullHandle());
        mysqlConditionWrapperHandleMap.put(Operator.GREATER_THAN, MySqlConditionWrapperHandle.greaterThanHandle());
        mysqlConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ, MySqlConditionWrapperHandle.greaterThanEqHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LESS_THAN, MySqlConditionWrapperHandle.lessThanHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ, MySqlConditionWrapperHandle.lessThanEqHandle());

        mysqlConditionWrapperHandleMap.put(Operator.TODAY, MySqlConditionWrapperHandle.todayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.YESTERDAY, MySqlConditionWrapperHandle.yesterdayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.TOMORROW, MySqlConditionWrapperHandle.tomorrowHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_SEVEN_DAY, MySqlConditionWrapperHandle.nextSevenDayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_SEVEN_DAY, MySqlConditionWrapperHandle.lastSevenDayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.BEFORE, MySqlConditionWrapperHandle.beforeHandle());
        mysqlConditionWrapperHandleMap.put(Operator.AFTER, MySqlConditionWrapperHandle.afterHandle());
        mysqlConditionWrapperHandleMap.put(Operator.THIS_WEEK, MySqlConditionWrapperHandle.thisWeekHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_WEEK, MySqlConditionWrapperHandle.lastWeekHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_WEEK, MySqlConditionWrapperHandle.nextWeekHandle());
        mysqlConditionWrapperHandleMap.put(Operator.THIS_MONTH, MySqlConditionWrapperHandle.thisMonthHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_MONTH, MySqlConditionWrapperHandle.lastMonthHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_MONTH, MySqlConditionWrapperHandle.nextMonthHandle());
        mysqlConditionWrapperHandleMap.put(Operator.THIS_YEAR, MySqlConditionWrapperHandle.thisYearHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_YEAR, MySqlConditionWrapperHandle.lastYearHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_YEAR, MySqlConditionWrapperHandle.nextYearHandle());

        conditionWrapperHandleMap.put(DataSource.MYSQL, mysqlConditionWrapperHandleMap);

        Map<Operator, ConditionWrapperHandle> elasticSearchConditionWrapperHandleMap = Maps.newEnumMap(Operator.class);
        elasticSearchConditionWrapperHandleMap.put(Operator.IS, ElasticSearchConditionWrapperHandle.iSHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT, ElasticSearchConditionWrapperHandle.notHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS, ElasticSearchConditionWrapperHandle.prefixContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS, ElasticSearchConditionWrapperHandle.prefixNotContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS, ElasticSearchConditionWrapperHandle.suffixContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS, ElasticSearchConditionWrapperHandle.suffixNotContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.CONTAINS, ElasticSearchConditionWrapperHandle.containsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_CONTAINS, ElasticSearchConditionWrapperHandle.notContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.prefixContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.prefixNotContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.suffixContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.suffixNotContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.IS_ANY, ElasticSearchConditionWrapperHandle.isAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_ANY, ElasticSearchConditionWrapperHandle.notAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.CONTAINS_ANY, ElasticSearchConditionWrapperHandle.containsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.notContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.IS_NULL, ElasticSearchConditionWrapperHandle.isNullHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.IS_NOT_NULL, ElasticSearchConditionWrapperHandle.isNotNullHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN, ElasticSearchConditionWrapperHandle.greaterThanHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ, ElasticSearchConditionWrapperHandle.greaterThanEqHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN, ElasticSearchConditionWrapperHandle.lessThanHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ, ElasticSearchConditionWrapperHandle.lessThanEqHandle());

        elasticSearchConditionWrapperHandleMap.put(Operator.TODAY, ElasticSearchConditionWrapperHandle.todayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.YESTERDAY, ElasticSearchConditionWrapperHandle.yesterdayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.TOMORROW, ElasticSearchConditionWrapperHandle.tomorrowHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_SEVEN_DAY, ElasticSearchConditionWrapperHandle.nextSevenDayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_SEVEN_DAY, ElasticSearchConditionWrapperHandle.lastSevenDayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.BEFORE, ElasticSearchConditionWrapperHandle.beforeHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.AFTER, ElasticSearchConditionWrapperHandle.afterHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.THIS_WEEK, ElasticSearchConditionWrapperHandle.thisWeekHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_WEEK, ElasticSearchConditionWrapperHandle.lastWeekHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_WEEK, ElasticSearchConditionWrapperHandle.nextWeekHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.THIS_MONTH, ElasticSearchConditionWrapperHandle.thisMonthHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_MONTH, ElasticSearchConditionWrapperHandle.lastMonthHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_MONTH, ElasticSearchConditionWrapperHandle.nextMonthHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.THIS_YEAR, ElasticSearchConditionWrapperHandle.thisYearHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_YEAR, ElasticSearchConditionWrapperHandle.lastYearHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_YEAR, ElasticSearchConditionWrapperHandle.nextYearHandle());

        conditionWrapperHandleMap.put(DataSource.ELASTICSEARCH, elasticSearchConditionWrapperHandleMap);

        Map<Operator, ConditionWrapperHandle> javaBeanConditionWrapperHandleMap = Maps.newEnumMap(Operator.class);
        javaBeanConditionWrapperHandleMap.put(Operator.IS, JavaBeanConditionWrapperHandle.iSHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT, JavaBeanConditionWrapperHandle.notHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS, JavaBeanConditionWrapperHandle.prefixContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS, JavaBeanConditionWrapperHandle.prefixNotContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS, JavaBeanConditionWrapperHandle.suffixContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS, JavaBeanConditionWrapperHandle.suffixNotContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.CONTAINS, JavaBeanConditionWrapperHandle.containsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_CONTAINS, JavaBeanConditionWrapperHandle.notContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS_ANY, JavaBeanConditionWrapperHandle.prefixContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.prefixNotContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS_ANY, JavaBeanConditionWrapperHandle.suffixContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.suffixNotContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.IS_ANY, JavaBeanConditionWrapperHandle.isAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_ANY, JavaBeanConditionWrapperHandle.notAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.CONTAINS_ANY, JavaBeanConditionWrapperHandle.containsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.notContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.IS_NULL, JavaBeanConditionWrapperHandle.isNullHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.IS_NOT_NULL, JavaBeanConditionWrapperHandle.isNotNullHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.GREATER_THAN, JavaBeanConditionWrapperHandle.greaterThanHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ, JavaBeanConditionWrapperHandle.greaterThanEqHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LESS_THAN, JavaBeanConditionWrapperHandle.lessThanHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ, JavaBeanConditionWrapperHandle.lessThanEqHandle());

        javaBeanConditionWrapperHandleMap.put(Operator.TODAY, JavaBeanConditionWrapperHandle.todayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.YESTERDAY, JavaBeanConditionWrapperHandle.yesterdayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.TOMORROW, JavaBeanConditionWrapperHandle.tomorrowHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_SEVEN_DAY, JavaBeanConditionWrapperHandle.nextSevenDayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_SEVEN_DAY, JavaBeanConditionWrapperHandle.lastSevenDayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.BEFORE, JavaBeanConditionWrapperHandle.beforeHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.AFTER, JavaBeanConditionWrapperHandle.afterHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.THIS_WEEK, JavaBeanConditionWrapperHandle.thisWeekHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_WEEK, JavaBeanConditionWrapperHandle.lastWeekHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_WEEK, JavaBeanConditionWrapperHandle.nextWeekHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.THIS_MONTH, JavaBeanConditionWrapperHandle.thisMonthHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_MONTH, JavaBeanConditionWrapperHandle.lastMonthHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_MONTH, JavaBeanConditionWrapperHandle.nextMonthHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.THIS_YEAR, JavaBeanConditionWrapperHandle.thisYearHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_YEAR, JavaBeanConditionWrapperHandle.lastYearHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_YEAR, JavaBeanConditionWrapperHandle.nextYearHandle());

        conditionWrapperHandleMap.put(DataSource.JAVABEAN, javaBeanConditionWrapperHandleMap);
    }

    /**
     * 内存数据校验
     *
     * @param conditionList {@link MemoryConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean memoryValidate(@NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        return memoryValidate(generateFullAndFormula(conditionList.size(), "and"), conditionList);
    }

    /**
     * 内存数据校验
     *
     * @param formula       like 1 and 2 or 3
     * @param conditionList {@link MemoryConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean memoryValidate(@NonNull final String formula, @NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        return memoryValidate(DataSource.JAVABEAN, formula, conditionList);
    }

    /**
     * 内存数据校验
     *
     * @param dataSource    support mysql, elasticsearch, javaBean
     * @param formula       like 1 and 2 or 3
     * @param conditionList {@link MemoryConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean memoryValidate(@NonNull final DataSource dataSource, @NonNull final String formula, @NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.JAVABEAN));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, Boolean> conditionWrapperMap = memorySnippetConditionWrapper(dataSource, conditionList);
        return checkFormula(formula, conditionWrapperMap);
    }

    /**
     * 内存数据条件包装
     *
     * @param dataSource    support javaBean
     * @param conditionList {@link MemoryConditionRequest}
     * @return db like 1->"companyId=1",2->"name like data" memory like 1->true, 2->false
     */
    private static Map<Integer, Boolean> memorySnippetConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.JAVABEAN));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, Boolean> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
        final Map<Operator, ConditionWrapperHandle> handleMap = conditionWrapperHandleMap.get(dataSource);
        int[] idx = {1};
        conditionList.forEach(i -> formulaMap.put(idx[0]++, (boolean) singleConditionWrapper(handleMap, i)));
        return formulaMap;
    }

    private static Object singleConditionWrapper(@NonNull final Map<Operator, ConditionWrapperHandle> handleMap, @NonNull final BaseConditionRequest condition) {
        return handleMap.get(condition.getOperator()).exec(condition);
    }

    /**
     * 数据库条件包装，条件拼接符默认为and
     *
     * @param dataSource    support mysql, elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        Map<Integer, String> snippetConditionMap = databaseSnippetConditionWrapper(dataSource, conditionList);
        return databaseCombineConditionWrapper(dataSource, snippetConditionMap);
    }

    /**
     * ES搜索条件包装
     *
     * @param dataSource    support elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @return like (companyId=1) and (name like data)
     */
    public static String elasticSearchSearchConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.ELASTICSEARCH));
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        conditionList.forEach(i -> boolQueryBuilder.should(matchQuery(i.getFieldName() + ".index_analyzer", i.getValueStr())));
        boolQueryBuilder.minimumShouldMatch(1);
        return boolQuery().must(boolQueryBuilder).toString();
    }

    /**
     * ES包含任意条件包装
     *
     * @param dataSource    support elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @return like (companyId=1) and (name like data)
     */
    public static String elasticSearchMinimumShouldMatchConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.ELASTICSEARCH));
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        conditionList.forEach(i -> {
            if (i.getValue() instanceof List) {
                boolQueryBuilder.should(termsQuery(i.getFieldName(), (List) i.getValue()));
            } else {
                boolQueryBuilder.should(termQuery(i.getFieldName(), i.getValue()));
            }
        });
        boolQueryBuilder.minimumShouldMatch(1);
        return boolQuery().must(boolQueryBuilder).toString();
    }

    /**
     * ES去除评分
     *
     * @param conditionStr    support elasticsearch
     * @return replace boost 1.0 -> 0.0
     */
    public static String elasticSearchRemoveBoost(@NonNull String conditionStr) {
        return StringUtils.replace(conditionStr, "\"boost\" : 1.0", "\"boost\" : 0.0");
    }

    /**
     * 数据库条件包装
     *
     * @param dataSource    support mysql, elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @param formula       like 1 and 2 or 3
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList, @NonNull String formula) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        Map<Integer, String> snippetConditionMap = databaseSnippetConditionWrapper(dataSource, conditionList);
        return databaseCombineConditionWrapper(dataSource, formula, snippetConditionMap);
    }

    /**
     * 数据库条件片段包装
     *
     * @param dataSource    support mysql, elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @return db like 1->"companyId=1",2->"name like data" memory like 1->true, 2->false
     */
    public static Map<Integer, String> databaseSnippetConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, String> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
        final Map<Operator, ConditionWrapperHandle> handleMap = conditionWrapperHandleMap.get(dataSource);
        int[] idx = {1};
        conditionList.forEach(i -> formulaMap.put(idx[0]++, String.valueOf(singleConditionWrapper(handleMap, i))));
        return formulaMap;
    }

    /**
     * 数据库条件合并包装，条件拼接符默认为and
     *
     * @param dataSource          support mysql, elasticsearch
     * @param snippetConditionMap like 1->"companyId=1",2->"name like data"
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseCombineConditionWrapper(@NonNull final DataSource dataSource, Map<Integer, String> snippetConditionMap) {
        String formula = generateFullAndFormula(snippetConditionMap.size(), "and");
        return databaseCombineConditionWrapper(dataSource, formula, snippetConditionMap);
    }

    /**
     * 数据库条件合并包装
     *
     * @param dataSource          support mysql, elasticsearch
     * @param formula             like 1 and 2 or 3
     * @param snippetConditionMap like 1->"companyId=1",2->"name like data"
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseCombineConditionWrapper(@NonNull final DataSource dataSource, @NonNull String formula, Map<Integer, String> snippetConditionMap) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        if (1 == snippetConditionMap.size()) {
            return snippetConditionMap.get(1);
        }
        FormulaUtil evaluator = new FormulaUtil(dataSource);
        return evaluator.doIt(evaluator, formula, snippetConditionMap);
    }

    /**
     * @param tableName table name
     * @param condition like name = 'zs'
     * @param orders    like name asc, age desc
     * @return whole mysql query
     */
    public static String mysqlQueryWrapper(@NonNull String condition, @NonNull String tableName, String orders, int pageNum, int pageSize) {
        String query = "SELECT %s FROM %s WHERE 1=1 AND %s limit %s, %s";
        return String.format(query, "id", tableName, condition, pageNum - 1, pageSize);
    }

    /**
     * @param tableName table name
     * @param condition like name = 'zs'
     * @return whole mysql query
     */
    public static String mysqlQueryCountWrapper(@NonNull String tableName, @NonNull String condition) {
        String query = "SELECT %s FROM %s WHERE 1=1 AND %s";
        return String.format(query, "COUNT(*)", tableName, condition);
    }

    /**
     * 表达式验证
     *
     * @param formula like 1 and 2 or 3
     * @param number  like 3
     * @return true if the execute should be success; otherwise false
     */
    public static boolean checkFormula(@NonNull String formula, final int number) {
        checkArgument(number > 0);
        Map<Integer, Boolean> numberMap = Maps.newLinkedHashMapWithExpectedSize(number);
        for (int i = 1; i < number + 1; i++) {
            numberMap.put(i, true);
        }
        return checkFormula(formula, numberMap);
    }

    /**
     * input a greater than 0 number then return 1 and 2 ... and a string
     *
     * @param number   a greater than 0 number
     * @param joinSign and or
     * @return 1 and 2 ... and a
     */
    public static String generateFullAndFormula(final int number, @NonNull final String joinSign) {
        checkArgument(number > 0);
        checkArgument("and".equals(joinSign) || "or".equals(joinSign));
        if (1 == number) {
            return "1";
        }
        StringJoiner sj = new StringJoiner(" " + joinSign + " ");
        for (int i = 1; i < number + 1; i++) {
            sj.add(String.valueOf(i));
        }
        return sj.toString();
    }

    /**
     * 表达式验证
     *
     * @param formula    like 1 and 2 or 3
     * @param formulaMap like 1->TRUE,2->FALSE
     * @return true if the execute should be success; otherwise false
     */
    private static boolean checkFormula(@NonNull String formula, Map<Integer, Boolean> formulaMap) {
        String formatFormula = handleFormulaBeforeCheck(formula);
        if (!checkFormulaFormat(formatFormula)) {
            return false;
        }
        formatFormula = checkOrderAndReplaceValue(formatFormula, formulaMap);
        if (formatFormula == null) {
            return false;
        }
        ExpressionParser parser = new SpelExpressionParser();

        try {
            return parser.parseExpression(formatFormula).getValue(Boolean.class);
        } catch (SpelParseException | SpelEvaluationException e) {
            return false;
        }
    }

    /**
     * 校验括号是否成对
     *
     * @param formula like 1 and 2 or 3
     * @return true if the execute should be success; otherwise false
     */
    private static boolean checkBracket(@NonNull String formula) {
        Deque<Character> stack = new LinkedList<>();

        char[] array = formula.toCharArray();
        for (char c : array) {
            switch (c) {
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    if (CollectionUtils.isNotEmpty(stack) && stack.peek() == '(')
                        stack.pop();
                    else
                        return false;
                    break;
                default:
                    break;
            }
        }
        return CollectionUtils.isEmpty(stack);
    }

    /**
     * 校验序号是否在并且替换
     *
     * @param formula    like 1 and 2 or 3
     * @param formulaMap like 1->TRUE,2->FALSE
     * @return like 1 and 2 or 3
     */
    private static String checkOrderAndReplaceValue(@NonNull String formula, @NonNull Map<Integer, Boolean> formulaMap) {
        Pattern regex = Pattern.compile("[1-9]\\d*");
        Matcher matcher = regex.matcher(formula);
        StringBuffer sb = new StringBuffer();

        Integer order;
        while (matcher.find()) {
            order = Integer.valueOf(matcher.group(0));
            if (!formulaMap.containsKey(order)) {
                return null;
            }
            matcher.appendReplacement(sb, String.valueOf(formulaMap.get(order)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static boolean checkFormulaFormat(@NonNull String formula) {
        return checkBracket(formula) && formula.matches("\\s*\\(*\\s*([1-9]\\d*)(\\s+([Aa][Nn][Dd]|[Oo][Rr])\\s+\\(*\\s*([1-9]\\d*)\\s*\\)*)*\\s*\\)*\\s*");
    }

    private static String handleFormulaBeforeCheck(@NonNull String formula) {
        return formula.replace('（', '(')
                .replace('）', ')');
    }

    private static String mysqlPermissionsWrapper() {

        return null;
    }

}

package com.github.fanfever.fever.condition;

import com.github.fanfever.fever.DataSource;
import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.request.DataBaseConditionRequest;
import com.github.fanfever.fever.condition.request.MemoryConditionRequest;
import com.github.fanfever.fever.condition.type.ValueType;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by fanfever on 2017/6/20.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 *
 * @see ConditionUtils
 */
public class ConditionUtilsUnitTest {

    @Test
    public void memoryValidateTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().strings("google").bigDecimals(new BigDecimal(1)).build();
        //conditions
        MemoryConditionRequest condition1 = new MemoryConditionRequest(bean, "strings", ValueType.TEXT, Operator.IS_NOT_NULL, null);
        MemoryConditionRequest condition2 = new MemoryConditionRequest(bean, "bigDecimals", ValueType.NUMERIC, Operator.IS, 1);

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(condition1, condition2));
        assertThat(result, is(true));
    }

    @Test
    public void databaseConditionWrapperTest() throws Exception {
        //conditions
        DataBaseConditionRequest condition1 = new DataBaseConditionRequest("id", ValueType.NUMERIC, Operator.IS, 1);
        DataBaseConditionRequest condition2 = new DataBaseConditionRequest("realname", ValueType.TEXT, Operator.IS, "google");
        DataBaseConditionRequest condition3 = new DataBaseConditionRequest("email", ValueType.TEXT, Operator.NOT, "2@test.cn");

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(DataSource.MYSQL, Lists.newArrayList(condition1, condition2, condition3));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(id = '1')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, "(realname = 'google')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(3, "(email <> '2@test.cn')"));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(DataSource.ELASTICSEARCH, Lists.newArrayList(condition1, condition2, condition3));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$.bool"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$.bool"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(3), "$.bool"), notNullValue());
    }

    @Test
    public void queryWrapperTest() throws Exception {
        //conditions
        DataBaseConditionRequest condition1 = new DataBaseConditionRequest("id", ValueType.NUMERIC, Operator.IS, "1");
        DataBaseConditionRequest condition2 = new DataBaseConditionRequest("realname", ValueType.TEXT, Operator.IS, "google");
        DataBaseConditionRequest condition3 = new DataBaseConditionRequest("createTime", ValueType.TIME, Operator.IS, LocalDateTime.now());

        //conditionsWrapper
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(DataSource.MYSQL, Lists.newArrayList(condition1, condition2, condition3));

        //conditionsWrapper
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(DataSource.ELASTICSEARCH, Lists.newArrayList(condition1, condition2, condition3));

    }

    @Test
    public void checkFormulaTest() throws Exception {
        List<FormulaData> formulaList = Lists.newArrayList(
                //不包含字段出现
                new FormulaData("1 ddd 2 or 3", 3, false),
                new FormulaData("(1 and2) and 2 or 3", 3, false),
                new FormulaData("(1 and(2 and 3))", 3, false), //有待商榷
                //排列不正确
                new FormulaData("1 1 and 2", 2, false),
                new FormulaData("1 and or 2 and 3", 3, false),
                //括号不匹配
                new FormulaData("1 and (or 2 and 3", 3, false),

                //空括号
                new FormulaData("1 and (2 and ())", 2, false),
                new FormulaData("() and 2 or 3", 3, false),
                //序号不匹配
                new FormulaData("(1 and or 3 and 7)) and 2 or 3", 4, false),

                //正确匹配
                new FormulaData("1", 1, true),
                new FormulaData("(1)", 1, true),
                new FormulaData("(1 or (2)) and 3", 3, true),
                new FormulaData("1 and (2 and 3)", 3, true),
                new FormulaData("(1 and (2 or 3 and 4)) and 2 or 3", 4, true),
                new FormulaData("(1 aNd 2 OR 3) Or (4 And 1 AND (1 or 3))", 4, true)
        );

        formulaList.forEach(e ->
                assertThat("不匹配的公式：" + e.getFormula(),
                        ConditionUtils.checkFormula(e.getFormula(), e.getMaxOrder()), is(e.isResult())));
    }

    @Data
    @Builder
    private static class WaitValidBean {
        private Integer integers;
        private double doubles;
        private BigDecimal bigDecimals;
        private String strings;
        private LocalDateTime localDateTimes;
    }

    @Data
    @AllArgsConstructor
    private class FormulaData {
        private String formula;
        private int maxOrder;
        private boolean result;
    }

}
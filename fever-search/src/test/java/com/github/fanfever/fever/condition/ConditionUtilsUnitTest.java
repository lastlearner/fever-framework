package com.github.fanfever.fever.condition;

import com.github.fanfever.fever.DataSource;
import com.github.fanfever.fever.condition.operator.Operator;
import com.github.fanfever.fever.condition.request.DataBaseConditionRequest;
import com.github.fanfever.fever.condition.request.MemoryConditionRequest;
import com.github.fanfever.fever.condition.type.ValueType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.fanfever.fever.DataSource.ELASTICSEARCH;
import static com.github.fanfever.fever.DataSource.MYSQL;
import static com.github.fanfever.fever.condition.operator.Operator.*;
import static com.github.fanfever.fever.condition.type.ValueType.*;
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
        WaitValidBean bean = WaitValidBean.builder().strings("fever").bigDecimals(new BigDecimal(1)).localDateTimes(LocalDateTime.of(2017, 1, 1, 1, 1)).build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "strings", TEXT, IS, "fever");
        MemoryConditionRequest bigDecimal = MemoryConditionRequest.of(bean, "bigDecimals", NUMERIC, NOT, 2);
        MemoryConditionRequest localDateTime = MemoryConditionRequest.of(bean, "localDateTimes", TIME, IS, LocalDateTime.of(2017, 1, 1, 1, 1));
        MemoryConditionRequest attachment = MemoryConditionRequest.of(bean, "strings", TEXT, IS, "fever");
        localDateTime.setAttachmentList(Lists.newArrayList(attachment));

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string, bigDecimal, localDateTime));
        assertThat(result, is(true));
    }

    @Test
    public void databaseConditionWrapperTest() throws Exception {
        //conditions

        //is
        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, IS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, NOT, "longText", null);
        DataBaseConditionRequest time = DataBaseConditionRequest.of("time", TIME, IS, "2017-01-01 00:00:00", null);
        DataBaseConditionRequest numeric = DataBaseConditionRequest.of("numeric", NUMERIC, IS, 1, null);
        DataBaseConditionRequest array = DataBaseConditionRequest.of("array", ARRAY, IS, "1,2", null);
        DataBaseConditionRequest attachment = DataBaseConditionRequest.of("numeric", NUMERIC, IS, 1, null);
        DataBaseConditionRequest attachment1 = DataBaseConditionRequest.of("numeric", NUMERIC, IS, 1, null);
        attachment.setAttachmentList(Lists.newArrayList(attachment1));

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText, time, numeric, array, attachment));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text = 'text')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, "(longText <> 'longText')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(3, "(time = '2017-01-01 00:00:00')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(4, "(numeric = '1')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(5, "(array = '1,2')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(6, "(((numeric = '1') AND (numeric = '1')))"));


        attachment.setAttachmentList(Lists.newArrayList(attachment1));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText, time, numeric, array, attachment));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$.bool"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$.bool"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(3), "$.bool"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(4), "$.bool"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(5), "$.bool"), notNullValue());
    }

    @Test
    public void databaseCombineConditionWrapper() throws Exception{

        Map<Integer, String> snippetConditionMap = Maps.newHashMapWithExpectedSize(3);
        snippetConditionMap.put(1, "(text = 'text')");
        snippetConditionMap.put(3, "(time = '2017-01-01 00:00:00')");
        snippetConditionMap.put(5, "(array = '1,2')");


        String result = ConditionUtils.databaseCombineConditionWrapper(MYSQL, "(1 and 3) or 5", snippetConditionMap);
        assertThat(result, is("(((text = 'text') AND (time = '2017-01-01 00:00:00')) OR (array = '1,2'))"));
    }

    @Test
    public void queryWrapperTest() throws Exception {
        //conditions
        DataBaseConditionRequest condition1 = DataBaseConditionRequest.of("id", ValueType.NUMERIC, IS, "1", null);
        DataBaseConditionRequest condition2 = DataBaseConditionRequest.of("realname", TEXT, IS, "google", null);
        DataBaseConditionRequest condition3 = DataBaseConditionRequest.of("createTime", ValueType.TIME, IS, LocalDateTime.now(), null);

        //conditionsWrapper
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(condition1, condition2, condition3));

        //conditionsWrapper
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(condition1, condition2, condition3));

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
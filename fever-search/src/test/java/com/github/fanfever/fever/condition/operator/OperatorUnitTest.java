package com.github.fanfever.fever.condition.operator;

import com.github.fanfever.fever.condition.ConditionUtils;
import com.github.fanfever.fever.condition.request.DataBaseConditionRequest;
import com.github.fanfever.fever.condition.request.MemoryConditionRequest;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
 */
public class OperatorUnitTest {

    @Test
    public void memoryIsTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, IS, "fever");
        MemoryConditionRequest bigDecimal = MemoryConditionRequest.of(bean, "bigDecimal", NUMERIC, IS, 1);
        MemoryConditionRequest localDateTime = MemoryConditionRequest.of(bean, "localDateTime", TIME, IS, LocalDateTime.of(2017, 1, 1, 1, 1));
        MemoryConditionRequest array = MemoryConditionRequest.of(bean, "array", ARRAY, IS, "1,2");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string, bigDecimal, localDateTime, array));
        assertThat(result, is(true));
    }

    @Test
    public void databaseIsTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, IS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, IS, "longText", null);
        DataBaseConditionRequest time = DataBaseConditionRequest.of("time", TIME, IS, "2017-01-01 00:00:00", null);
        DataBaseConditionRequest numeric = DataBaseConditionRequest.of("numeric", NUMERIC, IS, 1, null);
        DataBaseConditionRequest array = DataBaseConditionRequest.of("array", ARRAY, IS, "1,2", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText, time, numeric, array));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text = 'text')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, "(longText = 'longText')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(3, "(time = '2017-01-01 00:00:00')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(4, "(numeric = '1')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(5, "(array = '1,2')"));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText, time, numeric, array));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(3), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(4), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(5), "$..value"), notNullValue());
    }

    @Test
    public void memoryNotTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, NOT, "feve");
        MemoryConditionRequest bigDecimal = MemoryConditionRequest.of(bean, "bigDecimal", NUMERIC, NOT, 2);
        MemoryConditionRequest localDateTime = MemoryConditionRequest.of(bean, "localDateTime", TIME, NOT, LocalDateTime.of(2017, 1, 1, 1, 2));
        MemoryConditionRequest array = MemoryConditionRequest.of(bean, "array", ARRAY, NOT, "1");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string, bigDecimal, localDateTime, array));
        assertThat(result, is(true));
    }

    @Test
    public void databaseNotTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, NOT, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, NOT, "longText", null);
        DataBaseConditionRequest time = DataBaseConditionRequest.of("time", TIME, NOT, "2017-01-01 00:00:00", null);
        DataBaseConditionRequest numeric = DataBaseConditionRequest.of("numeric", NUMERIC, NOT, 1, null);
        DataBaseConditionRequest array = DataBaseConditionRequest.of("array", ARRAY, NOT, "1,2", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText, time, numeric, array));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text <> 'text')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, "(longText <> 'longText')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(3, "(time <> '2017-01-01 00:00:00')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(4, "(numeric <> '1')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(5, "(array <> '1,2')"));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText, time, numeric, array));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(3), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(4), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(5), "$..value"), notNullValue());
    }

    @Test
    public void memoryPrefixContainsTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, PREFIX_CONTAINS, "fe");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databasePrefixContainsTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, PREFIX_CONTAINS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, PREFIX_CONTAINS, "longText", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text LIKE 'text%')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
    }

    @Test
    public void memoryPrefixNotContainsTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, PREFIX_NOT_CONTAINS, "ef");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databasePrefixNotContainsTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, PREFIX_NOT_CONTAINS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, PREFIX_NOT_CONTAINS, "longText", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text NOT LIKE 'text%')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
    }

    @Test
    public void memorySuffixContainsTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, SUFFIX_CONTAINS, "er");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databaseSuffixContainsTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, SUFFIX_CONTAINS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, SUFFIX_CONTAINS, "longText", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text LIKE '%text')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
    }

    @Test
    public void memorySuffixNotContainsTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, SUFFIX_NOT_CONTAINS, "re");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databaseSuffixNotContainsTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, SUFFIX_NOT_CONTAINS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, SUFFIX_NOT_CONTAINS, "longText", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text NOT LIKE '%text')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
    }

    @Test
    public void memoryContainsTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, CONTAINS, "ve");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databaseContainsTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, CONTAINS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, CONTAINS, "longText", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text LIKE '%text%')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
    }

    @Test
    public void memoryNotContainsTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "string", TEXT, NOT_CONTAINS, "re");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databaseNotContainsTest() throws Exception {
        //conditions

        DataBaseConditionRequest text = DataBaseConditionRequest.of("text", TEXT, NOT_CONTAINS, "text", null);
        DataBaseConditionRequest longText = DataBaseConditionRequest.of("longText", LONG_TEXT, NOT_CONTAINS, "longText", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(text, longText));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(text NOT LIKE '%text%')"));
        assertThat(mysqlConditionWrapperMap, hasEntry(2, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(text, longText));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(2), "$..value"), notNullValue());
    }

    @Test
    public void memoryIsAnyTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2,3").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "array", ARRAY, IS_ANY, "1,4");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databaseIsAnyTest() throws Exception {
        //conditions

        DataBaseConditionRequest array = DataBaseConditionRequest.of("array", ARRAY, IS_ANY, "1,2", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(array));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(FIND_IN_SET('1', array) OR FIND_IN_SET('2', array))"));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(array));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
    }

    @Test
    public void memoryNotAnyTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("1,2,3,4").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "array", ARRAY, NOT_ANY, "1,5");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(false));
    }

    @Test
    public void databaseNotAnyTest() throws Exception {
        //conditions

        DataBaseConditionRequest array = DataBaseConditionRequest.of("array", ARRAY, NOT_ANY, "1,2", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(array));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, "(NOT FIND_IN_SET('1', array) AND NOT FIND_IN_SET('2', array))"));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(array));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
    }

    @Test
    public void memoryContainsAnyTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("google,microsoft").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "array", ARRAY, CONTAINS_ANY, "gle,tfo");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(true));
    }

    @Test
    public void databaseContainsAnyTest() throws Exception {
        //conditions

        DataBaseConditionRequest array = DataBaseConditionRequest.of("array", ARRAY, CONTAINS_ANY, "google,microsoft", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(array));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(array));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
    }

    @Test
    public void memoryNotContainsAnyTest() throws Exception {
        //javaBean
        WaitValidBean bean = WaitValidBean.builder().string("fever").bigDecimal(new BigDecimal(1)).localDateTime(LocalDateTime.of(2017, 1, 1, 1, 1)).array("google,microsoft").build();
        //conditions
        MemoryConditionRequest string = MemoryConditionRequest.of(bean, "array", ARRAY, NOT_CONTAINS_ANY, "gle,tfo");

        boolean result = ConditionUtils.memoryValidate(Lists.newArrayList(string));
        assertThat(result, is(false));
    }

    @Test
    public void databaseNotContainsAnyTest() throws Exception {
        //conditions

        DataBaseConditionRequest array = DataBaseConditionRequest.of("array", ARRAY, NOT_CONTAINS_ANY, "google,microsoft", null);

        //mysql
        Map<Integer, String> mysqlConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(MYSQL, Lists.newArrayList(array));
        assertThat(mysqlConditionWrapperMap, hasEntry(1, ""));

        //elasticSearch
        Map<Integer, String> elasticSearchConditionWrapperMap = ConditionUtils.databaseSnippetConditionWrapper(ELASTICSEARCH, Lists.newArrayList(array));
        assertThat(JsonPath.read(elasticSearchConditionWrapperMap.get(1), "$..value"), notNullValue());
    }

    @Data
    @Builder
    private static class WaitValidBean {
        private BigDecimal bigDecimal;
        private String string;
        private LocalDateTime localDateTime;
        private String array;
    }
}

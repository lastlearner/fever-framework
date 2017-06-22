package com.github.fanfever.fever.writer;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by fanfever on 2017/6/23.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 *
 * modified ExecutorType batch to simple
 */
public class MyBatisSimplePagingItemReader<T> extends AbstractPagingItemReader<T> {
    private String queryId;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSessionTemplate sqlSessionTemplate;
    private Map<String, Object> parameterValues;

    public MyBatisSimplePagingItemReader() {
        this.setName(ClassUtils.getShortName(MyBatisPagingItemReader.class));
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public void setParameterValues(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        Assert.notNull(this.sqlSessionFactory, "sqlSessionFactory must not null");
        this.sqlSessionTemplate = new SqlSessionTemplate(this.sqlSessionFactory, ExecutorType.SIMPLE);
        Assert.notNull(this.queryId, "queryId must not null");
    }

    protected void doReadPage() {
        HashMap parameters = new HashMap();
        if(this.parameterValues != null) {
            parameters.putAll(this.parameterValues);
        }

        parameters.put("_page", Integer.valueOf(this.getPage()));
        parameters.put("_pagesize", Integer.valueOf(this.getPageSize()));
        parameters.put("_skiprows", Integer.valueOf(this.getPage() * this.getPageSize()));
        if(this.results == null) {
            this.results = new CopyOnWriteArrayList();
        } else {
            this.results.clear();
        }

        this.results.addAll(this.sqlSessionTemplate.selectList(this.queryId, parameters));
    }

    protected void doJumpToPage(int itemIndex) {
    }
}

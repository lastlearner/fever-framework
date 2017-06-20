package com.github.fanfever.fever.query;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * elasticsearch document query
 * elasticsearch version 5.2.1
 *
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2017年5月3日
 */
@Slf4j
@Component
@AllArgsConstructor
public class DocumentQuery {

    private final Client elasticsearchClient;

    public SearchResponse execute(String index, String type, QueryBuilder queryBuilder) {
        return wrapper(index, type, queryBuilder, 0, 1000, true, false).get();
    }

    public SearchResponse execute(String index, String type, QueryBuilder queryBuilder, int from, int size) {
        return wrapper(index, type, queryBuilder, from, size, false, false).get();
    }

    public SearchResponse execute(String index, String type, QueryBuilder queryBuilder, int from, int size, boolean isExplain) {
        return wrapper(index, type, queryBuilder, from, size, isExplain, false).get();
    }

    public SearchResponse execute(String[] indexArray, String[] typeArray, QueryBuilder queryBuilder, int from, int size, boolean isExplain) {
        return wrapper(indexArray, typeArray, queryBuilder, from, size, isExplain, false).get();
    }

    public MultiSearchResponse execute(List<SearchRequestBuilder> searchRequestBuilderList) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(searchRequestBuilderList));
        MultiSearchRequestBuilder multiSearchRequestBuilder = elasticsearchClient.prepareMultiSearch();
        searchRequestBuilderList.forEach(multiSearchRequestBuilder::add);
        return multiSearchRequestBuilder.get();
    }

    public SearchRequestBuilder wrapper(String index, String type, QueryBuilder queryBuilder, int from, boolean isExplain, boolean isFetchSource){
        return elasticsearchClient.prepareSearch(index).setTypes(type).setQuery(queryBuilder).setFrom(from - 1).setExplain(isExplain).setFetchSource(isFetchSource);
    }

    public SearchRequestBuilder wrapper(String index, String type, QueryBuilder queryBuilder, int from, int size, boolean isExplain, boolean isFetchSource){
        return elasticsearchClient.prepareSearch(index).setTypes(type).setQuery(queryBuilder).setFrom(from - 1).setSize(size).setExplain(isExplain).setFetchSource(isFetchSource);
    }

    public SearchRequestBuilder wrapper(String[] indexArray, String[] typeArray, QueryBuilder queryBuilder, int from, int size, boolean isExplain, boolean isFetchSource){
        return elasticsearchClient.prepareSearch(indexArray).setTypes(typeArray).setQuery(queryBuilder).setFrom(from - 1).setSize(size).setExplain(isExplain).setFetchSource(isFetchSource);
    }

    public long count(QueryBuilder queryBuilder, String index, String type) {
        return wrapper(index, type, queryBuilder, 0, false, false).get().getHits().getTotalHits();
    }

}

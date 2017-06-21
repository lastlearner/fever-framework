package com.github.fanfever.fever.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    private final ObjectMapper objectMapper;
    private final Client elasticSearchClient;

    public <T> T getDocumentById(@NonNull String index, @NonNull String type, int id, @NonNull Class<T> clazz) {

        GetResponse getResponse = getDocumentById(index, type, id);
        if (getResponse != null) {
            if (getResponse.getSourceAsString() != null) {
                try {

                    return objectMapper.readValue(getResponse.getSourceAsString(), clazz);
                } catch (IOException e) {
                    log.error("error:{}", e);
                }
            }
        }
        return null;
    }

    public GetResponse getDocumentById(@NonNull String index, @NonNull String type, int id) {
        return elasticSearchClient.prepareGet(index, type, String.valueOf(id)).get();
    }

    public SearchResponse execute(String index, String type, QueryBuilder queryBuilder) {
        return wrapper(index, type, queryBuilder, 0, 1000, true, false).get();
    }

    public SearchResponse execute(String index, String type, QueryBuilder queryBuilder, boolean isFetchSource) {
        return wrapper(index, type, queryBuilder, 0, 1000, true, isFetchSource).get();
    }

    public SearchResponse execute(String index, String type, QueryBuilder queryBuilder, int from, int size, boolean isFetchSource) {
        return wrapper(index, type, queryBuilder, from, size, false, isFetchSource).get();
    }

    public SearchResponse execute(String index, String type, QueryBuilder queryBuilder, int from, int size, boolean isExplain, boolean isFetchSource) {
        return wrapper(index, type, queryBuilder, from, size, isExplain, isFetchSource).get();
    }

    public SearchResponse execute(String[] indexArray, String[] typeArray, QueryBuilder queryBuilder, int from, int size, boolean isExplain) {
        return wrapper(indexArray, typeArray, queryBuilder, from, size, isExplain, false).get();
    }

    public MultiSearchResponse execute(List<SearchRequestBuilder> searchRequestBuilderList) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(searchRequestBuilderList));
        MultiSearchRequestBuilder multiSearchRequestBuilder = elasticSearchClient.prepareMultiSearch();
        searchRequestBuilderList.forEach(multiSearchRequestBuilder::add);
        return multiSearchRequestBuilder.get();
    }

    public SearchRequestBuilder wrapper(String index, String type, QueryBuilder queryBuilder, int from, boolean isExplain, boolean isFetchSource) {
        return elasticSearchClient.prepareSearch(index).setTypes(type).setQuery(queryBuilder).setFrom(from - 1).setExplain(isExplain).setFetchSource(isFetchSource);
    }

    public SearchRequestBuilder wrapper(String index, String type, QueryBuilder queryBuilder, int from, int size, boolean isExplain, boolean isFetchSource) {
        return elasticSearchClient.prepareSearch(index).setTypes(type).setQuery(queryBuilder).setFrom(from - 1).setSize(size).setExplain(isExplain).setFetchSource(isFetchSource);
    }

    public SearchRequestBuilder wrapper(String[] indexArray, String[] typeArray, QueryBuilder queryBuilder, int from, int size, boolean isExplain, boolean isFetchSource) {
        return elasticSearchClient.prepareSearch(indexArray).setTypes(typeArray).setQuery(queryBuilder).setFrom(from - 1).setSize(size).setExplain(isExplain).setFetchSource(isFetchSource);
    }

    public long count(QueryBuilder queryBuilder, String index, String type) {
        return wrapper(index, type, queryBuilder, 0, false, false).get().getHits().getTotalHits();
    }

}

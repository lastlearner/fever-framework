package com.github.fanfever.fever.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fanfever.fever.command.request.DocumentCommandRequest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequestBuilder;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * elasticsearch document operation
 * elasticsearch version 5.2.1
 *
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2017年5月3日
 */
@Slf4j
@Component
public class DocumentCommand {

    @Autowired
    private Client elasticsearchClient;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 创建更新删除document，默认刷新
     *
     * @param documentCommandRequest {@link DocumentCommandRequest}
     * @return true if the execute should be success; otherwise false
     */
    public boolean execute(final DocumentCommandRequest documentCommandRequest) {
        List<DocumentCommandRequest> documentCommandRequestList = Lists.newArrayListWithCapacity(1);
        documentCommandRequestList.add(documentCommandRequest);
        return execute(documentCommandRequestList);
    }

    /**
     * 批量创建更新删除document，默认刷新
     *
     * @param documentCommandRequestList {@link DocumentCommandRequest}
     * @return true if the execute should be success; otherwise false
     */
    public boolean execute(final List<DocumentCommandRequest> documentCommandRequestList) {
        return execute(documentCommandRequestList, true);
    }

    /**
     * @param documentCommandRequestList {@link DocumentCommandRequest}
     * @param isRefresh 是否刷新
     * @return true if the execute should be success; otherwise false
     */
    public boolean execute(final List<DocumentCommandRequest> documentCommandRequestList, boolean isRefresh) {
        BulkRequestBuilder bulkRequestBuilder = elasticsearchClient.prepareBulk();
        for (int i = 0; i < documentCommandRequestList.size(); i++) {
            DocumentCommandRequest documentCommandRequest = documentCommandRequestList.get(i);
            switch (documentCommandRequest.getCommandType()) {
                case SAVE:
                    bulkRequestBuilder.add(createWrapper(documentCommandRequest));
                    break;
                case UPDATE:
                    bulkRequestBuilder.add(updateWrapper(documentCommandRequest));
                    break;
                case DELETE:
                    bulkRequestBuilder.add(deleteWrapper(documentCommandRequest));
                    break;
                default:
                    throw new AssertionError("operation is not exists!");
            }
        }
        BulkResponse bulkResponse = bulkRequestBuilder.get();
        if (isRefresh) {
            BulkItemResponse[] bulkItemResponseArray = bulkResponse.getItems();
            Set<String> indexSet = Arrays.stream(bulkItemResponseArray).filter(i -> !i.isFailed()).map(BulkItemResponse::getIndex).collect(Collectors.toSet());
            refreshWrapper(indexSet).get();
        }
        return bulkResponse.hasFailures();
    }

    private IndexRequestBuilder createWrapper(final DocumentCommandRequest documentCommandRequest) {
        return elasticsearchClient.prepareIndex(documentCommandRequest.getIndex(), documentCommandRequest.getType(), documentCommandRequest.getId()).setSource(serializeDocument(documentCommandRequest.getDocument()));
    }

    private UpdateRequestBuilder updateWrapper(final DocumentCommandRequest documentCommandRequest) {
        return elasticsearchClient.prepareUpdate(documentCommandRequest.getIndex(), documentCommandRequest.getType(), documentCommandRequest.getId()).setDoc(serializeDocument(documentCommandRequest.getDocument()));
    }

    private DeleteRequestBuilder deleteWrapper(final DocumentCommandRequest documentCommandRequest) {
        return elasticsearchClient.prepareDelete(documentCommandRequest.getIndex(), documentCommandRequest.getType(), documentCommandRequest.getId());
    }

    private RefreshRequestBuilder refreshWrapper(Set<String> indexSet) {
        return elasticsearchClient.admin().indices().prepareRefresh(indexSet.toArray(new String[indexSet.size()]));
    }

    private byte[] serializeDocument(Object document) {
        try {
            log.debug("serializeDocument:{}", objectMapper.writeValueAsString(document));
            return objectMapper.writeValueAsBytes(document);
        } catch (JsonProcessingException e) {
            log.error("serializeDocument fail, document:{}, exception:{}", document, e);
        }
        return new byte[0];
    }

}

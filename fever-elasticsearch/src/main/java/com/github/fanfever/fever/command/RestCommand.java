package com.github.fanfever.fever.command;

import lombok.NonNull;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Collections;

public class RestCommand {

    private final RestClient restClient;

    @Autowired
    public RestCommand(@NonNull RestClient restClient) {
        this.restClient = restClient;
    }

    public void createIndex(String index, String type, int id, String content) throws IOException {
        restClient.performRequest(HttpMethod.PUT.name(), index + "/" + type + "/" + id, Collections.emptyMap(), new NStringEntity(content, ContentType.APPLICATION_JSON));
    }

    public void updateIndex(String index, String type, int id, String content) throws IOException {
        restClient.performRequest(HttpMethod.PUT.name(), index + "/" + type + "/" + id, Collections.emptyMap(), new NStringEntity(content, ContentType.APPLICATION_JSON));
    }

    public void deleteIndex(String index, String type, int id, String content) throws IOException {
        restClient.performRequest(HttpMethod.DELETE.name(), index + "/" + type + "/" + id, Collections.emptyMap(), new NStringEntity(content, ContentType.APPLICATION_JSON));
    }
}

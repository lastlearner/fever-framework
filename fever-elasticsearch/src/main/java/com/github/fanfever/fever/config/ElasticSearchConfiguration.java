package com.github.fanfever.fever.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Slf4j
@Configuration
public class ElasticSearchConfiguration {

    @Bean
    @Lazy
    public Client esClient(@Value("${elasticsearch.host}") String host,
                           @Value("${elasticsearch.clusterName}") String clusterName) {
        Client client = null;
        try {
            client = TransportClient.builder()
                    .settings(Settings.settingsBuilder().put("cluster.name", clusterName).build()).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));
        } catch (UnknownHostException e) {
            log.error("error", e);
        }
        return client;
    }
}

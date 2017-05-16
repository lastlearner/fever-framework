package com.github.fanfever.fever.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
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
    public Client elasticSearchClient(@Value("${elasticsearch.hostname}") String hostname,
                                      @Value("${elasticsearch.clusterName}") String clusterName) {
        try {
            return new PreBuiltTransportClient(Settings.builder().put("client.transport.sniff", true).put("cluster.name", clusterName).build())
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), 9300));
        } catch (UnknownHostException e) {
            log.error("elasticsearchClient exception:{}", e);
            return null;
        }
    }
}

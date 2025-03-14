package com.interview.web.config;

import cn.hutool.core.collection.CollUtil;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ESConfig {
    private static final Logger logger = LoggerFactory.getLogger(ESConfig.class);
    /**
     * 端口号
     */
//    @Value("${elastic.port}")
//    private int esPort;

    @Value("#{'${elastic.port:8080}'.split(',')}")
    private List<String> esPorts = new ArrayList<>();
    /**
     * 集群名称
     */
    @Value("${elastic.clustername}")
    private String esClusterName;
    /**
     * 集群的地址
     */
    @Value("#{'${elastic.hosts:localhost}'.split(',')}")
    private List<String> hosts = new ArrayList<>();

    /**
     * 连接池
     */
    @Value("${elastic.pool}")
    private String poolSize;

    /**
     * 初始化配置
     *
     * @return
     */
    private Settings settings() {
        return Settings.builder()
                .put("cluster.name", esClusterName)
                .put("thread_pool.search.size", Integer.parseInt(poolSize))
                .put("client.transport.sniff", false).build();
    }

    @Bean
    protected Client buildClient() {
        TransportClient transportClient = new PreBuiltTransportClient(settings());

        if (CollUtil.isNotEmpty(hosts)) {
            for(String host:hosts){
                esPorts.forEach(p->{
                    try {
                        transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(host), Integer.parseInt(p)));
                    } catch (UnknownHostException e) {
                        logger.error("Error addTransportAddress,with host:{}.", host);
                    }
                });
            }
        }
        return transportClient;
    }

    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchTemplate(buildClient());
    }


    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client);
    }


}


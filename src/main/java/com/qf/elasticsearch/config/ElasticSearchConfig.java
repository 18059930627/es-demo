package com.qf.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author chenzhongjun
 * @Date 2020/11/24
 * ES配置类
 */

@Configuration
public class ElasticSearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class.getName());


    @Value("#{'${spring.data.elasticsearch.hosts}'.split(',')}")
    private List<String> hosts;

    @Value("${spring.data.elasticsearch.port}")
    private int port;

    @Value("${spring.data.elasticsearch.connectTimeOut}")
    private int connectTimeOut;

    @Value("${spring.data.elasticsearch.socketTimeOut}")
    private int socketTimeOut;

    @Value("${spring.data.elasticsearch.connectRequestTimeOut}")
    private int connectRequestTimeOut;

    @Value("${spring.data.elasticsearch.maxConnectNum}")
    private int maxConnectNum;

    @Value("${spring.data.elasticsearch.maxConnectPerRoute}")
    private int maxConnectPerRoute;


    @Bean
    @ConditionalOnMissingBean(RestHighLevelClient.class)
    public RestHighLevelClient levelClient() {
        HttpHost[] httpHosts = null;
        if (!CollectionUtils.isEmpty(hosts)) {
            httpHosts = new HttpHost[hosts.size()];
            for (int i = 0; i < hosts.size(); i++) {
                HttpHost httpHost = new HttpHost(hosts.get(i), port);
                httpHosts[i] = httpHost;
            }
        }
        RestClientBuilder builder = RestClient.builder(httpHosts);

        setConnectTimeOutConfig(builder); //设置超时操作
        setHttpClientConfig(builder); //设置异步相关操作

        logger.info("create RestHighLevelClient complete.");
        return new RestHighLevelClient(builder);
    }

    /**
     * 连接超时相关配置
     * @param builder
     */
    private void setConnectTimeOutConfig(RestClientBuilder builder){
        builder.setRequestConfigCallback( (clientBuilder) -> {
           clientBuilder.setSocketTimeout(socketTimeOut);
           clientBuilder.setConnectTimeout(connectTimeOut);
           clientBuilder.setConnectionRequestTimeout(connectRequestTimeOut);
           return clientBuilder;
        });
    }

    /**
     * Http配置
     * @param builder
     */
    private void setHttpClientConfig(RestClientBuilder builder){
        builder.setHttpClientConfigCallback( (clientBuilder) -> {
            clientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            clientBuilder.setMaxConnTotal(maxConnectNum);
            return clientBuilder;
        });
    }
}

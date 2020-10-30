package com.heqing.demo.spring.elasticsearch.config;


import com.heqing.demo.spring.elasticsearch.model.Item;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("com.heqing.demo.spring.elasticsearch.*")
@EnableElasticsearchRepositories(basePackages = {"com.heqing.demo.spring.elasticsearch.repository"})
public class SpringEsConfig extends AbstractElasticsearchConfiguration {

    @Autowired
    EsProperty esProperty;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        // 拆分地址
        List<HttpHost> hostLists = new ArrayList<>();
        String[] hostList = esProperty.getAddress().split(",");
        for (String addr : hostList) {
            String host = addr.split(":")[0];
            String port = addr.split(":")[1];
            hostLists.add(new HttpHost(host, Integer.parseInt(port), esProperty.getSchema()));
        }
        // 转换成 HttpHost 数组
        HttpHost[] httpHost = hostLists.toArray(new HttpHost[]{});
        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);
        // 异步连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(esProperty.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(esProperty.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(esProperty.getConnectionRequestTimeout());
            return requestConfigBuilder;
        });
        // 异步连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(esProperty.getMaxConnectNum());
            httpClientBuilder.setMaxConnPerRoute(esProperty.getMaxConnectPerRoute());
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient elasticsearchClient) {
        ElasticsearchRestTemplate elasticsearchTemplate = new ElasticsearchRestTemplate(elasticsearchClient);
        elasticsearchTemplate.putMapping(Item.class);
        return elasticsearchTemplate;
    }
}

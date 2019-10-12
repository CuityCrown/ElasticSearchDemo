package com.lenovo.elastic.config.elastic;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/9/27 18:59
 */
@Configuration
public class ElasticConfiguration {

    @Bean
    @ConfigurationProperties(value = "elastic.search")
    public ElasticConfig initElasticConfig(){
        return new ElasticConfig();
    }

    @Bean
    public RestHighLevelClient initClient(@Autowired ElasticConfig elasticConfig){
        RestClientBuilder builder = RestClient.builder(elasticConfig.getHttpHosts());

        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(elasticConfig.getConnectTimeOut());
            requestConfigBuilder.setSocketTimeout(elasticConfig.getSocketTimeOut());
            requestConfigBuilder.setConnectionRequestTimeout(elasticConfig.getConnectionRequestTimeOut());
            return requestConfigBuilder;
        });

        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(elasticConfig.getMaxConnTotal());
            httpClientBuilder.setMaxConnPerRoute(elasticConfig.getMaxConnectPerRoute());
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }
}

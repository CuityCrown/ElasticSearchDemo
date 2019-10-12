package com.lenovo.elastic.config.elastic;

import lombok.Data;
import org.apache.http.HttpHost;

import java.util.Arrays;

/**
 * description:
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/9/27 20:12
 */
@Data
public class ElasticConfig {

    /**
     * 集群ip
     */
    private String hosts;

    /**
     * 端口号
     */
    private int port;

    /**
     * 通讯协议
     */
    private String schema;

    /**
     * 连接超时时间
     */
    private int connectTimeOut;

    /**
     * 连接超时时间
     */
    private int socketTimeOut;

    /**
     * 获取连接的超时时间
     */
    private int connectionRequestTimeOut;

    /**
     * 最大连接数
     */
    private int maxConnTotal;

    /**
     * 最大路由连接数
     */
    private int maxConnectPerRoute;

    private HttpHost[] httpHosts;

    public void setHosts(String hosts) {
        this.hosts = hosts;
        this.httpHosts = Arrays.stream(hosts.split(","))
                .map(host -> new HttpHost(host, port, schema))
                .toArray(HttpHost[]::new);
    }
}

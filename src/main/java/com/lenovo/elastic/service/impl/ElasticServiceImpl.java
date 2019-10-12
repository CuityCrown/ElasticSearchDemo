package com.lenovo.elastic.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lenovo.elastic.common.exception.BizException;
import com.lenovo.elastic.entity.QueryBean;
import com.lenovo.elastic.service.ElasticServce;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/9/27 21:38
 */
@Service
public class ElasticServiceImpl implements ElasticServce {

    private static final Logger logger = LoggerFactory.getLogger(ElasticServiceImpl.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public <T> List<T> search(QueryBean queryBean, Class<T> t){
        List<T> result = new ArrayList<>();
        logger.info("开始搜索,搜索条件: {}", queryBean);

        if (StringUtils.isEmpty(queryBean.getIndex())){
            throw new BizException("index不能为空");
        }

        SearchRequest searchRequest = new SearchRequest(queryBean.getIndex());
        if (queryBean.getTypes() != null){
            searchRequest.types(queryBean.getTypes());
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBean.getQuery());
        sourceBuilder.from((queryBean.getPageNumber()-1) * queryBean.getPageSize());
        sourceBuilder.size(queryBean.getPageSize());
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            logger.debug("搜索结果: {}", JSON.toJSONString(searchResponse));
            logger.debug("命中结果: {}", JSON.toJSONString(hits));
            logger.info("命中数量: {}", hits.getTotalHits().value);
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                System.out.println(hit.getSourceAsString());
                result.add(JSONObject.parseObject(hit.getSourceAsString(),t));
            }
        } catch (IOException e) {
            logger.error("执行搜索失败");
        }
        return result;
    }

}

package com.lenovo.elastic.entity;

import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * description:
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/9/28 20:32
 */
@Data
public class QueryBean {

    private String index;

    private String[] types;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalSize;

    /**
     * 查询条件
     */
    private QueryBuilder query;

}

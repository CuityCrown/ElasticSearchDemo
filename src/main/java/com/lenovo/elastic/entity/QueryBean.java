package com.lenovo.elastic.entity;

import lombok.Data;

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
     * 查询条件key value形式
     */
    private String condition;

    private String[] fieldNames;

}

package com.lenovo.elastic.service;

import com.lenovo.elastic.entity.QueryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/9/27 21:37
 */
public interface ElasticServce {

    /**
     * @param queryBean
     * @param t
     * @return
     */
    <T> List<T> search(QueryBean queryBean, Class<T> t);

}

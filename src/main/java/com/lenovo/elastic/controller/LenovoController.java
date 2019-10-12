package com.lenovo.elastic.controller;

import com.lenovo.elastic.entity.LenovoBean;
import com.lenovo.elastic.entity.QueryBean;
import com.lenovo.elastic.service.ElasticServce;
import com.lenovo.elastic.utils.ExecutorPoolUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description:
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/9/29 17:07
 */
@Controller
@RequestMapping("/elastic")
public class LenovoController {

    private static final Logger logger = LoggerFactory.getLogger(LenovoController.class);

    @Autowired
    private ElasticServce elasticService;

    @RequestMapping("/search")
    @ResponseBody
    public Object search() {

        long start = System.currentTimeMillis();
        List<LenovoBean> search = Collections.synchronizedList(new ArrayList<>());

        QueryBean queryBean1 = new QueryBean();
        queryBean1.setIndex("sc_bom_fact_rt_cml_1");
        queryBean1.setPageSize(6000);
        queryBean1.setQuery(QueryBuilders.matchQuery("meins","EA"));
        queryBean1.setPageNumber(1);
        List<LenovoBean> search1 = elasticService.search(queryBean1, LenovoBean.class);

        CountDownLatch countDownLatch = new CountDownLatch(search1.size());
        AtomicInteger atomicInteger = new AtomicInteger(1);
        System.out.println(search.size()+"开启数量");
        for (LenovoBean lenovoBean : search1) {
            ExecutorPoolUtils.execute(() -> {
                try {
                    QueryBean queryBean = new QueryBean();
                    queryBean.setIndex("sc_bom_fact_rt_cml_1");
                    queryBean.setPageSize(1);
                    queryBean1.setQuery(QueryBuilders.matchQuery("idnrk",lenovoBean.getIdnrk()));
                    queryBean.setPageNumber(atomicInteger.getAndIncrement());
                    search.addAll(elasticService.search(queryBean, LenovoBean.class));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("es查询任务执行失败");
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间为" + ((end - start)));
        return search;
    }

    @RequestMapping("/search2")
    @ResponseBody
    public Object search2() {
        long start = System.currentTimeMillis();
        QueryBean queryBean = new QueryBean();
        queryBean.setIndex("sc_bom_fact_rt_cml_1");
        queryBean.setPageSize(7000);
        queryBean.setPageNumber(1);
        List<LenovoBean> search = elasticService.search(queryBean, LenovoBean.class);
        long end = System.currentTimeMillis();
        System.out.println("执行时间为" + ((end - start) / 1000));
        return search;
    }


}

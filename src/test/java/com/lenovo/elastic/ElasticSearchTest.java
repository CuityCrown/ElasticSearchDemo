package com.lenovo.elastic;

import com.lenovo.elastic.entity.LenovoBean;
import com.lenovo.elastic.entity.QueryBean;
import com.lenovo.elastic.service.impl.ElasticServiceImpl;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author 刘一博
 * @version V1.0
 * @date 2019/9/27 21:50
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {

    @Autowired
    private ElasticServiceImpl elasticServiceImpl;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testSearch(){
        QueryBean queryBean = new QueryBean();
        queryBean.setIndex("sc_bom_fact_rt_cml_1");
        queryBean.setPageSize(1000);
        queryBean.setPageNumber(1);
        List<LenovoBean> search = elasticServiceImpl.search(queryBean, LenovoBean.class);
        System.out.println(search);
    }

    @Test
    public void testSearch2() throws IOException {
        GetRequest getRequest = new GetRequest("sc_bom_fact_rt_cml_1", "_doc", "0000000AM267L07080000037L75582015050199991231");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> resultMap = getResponse.getSource();
        System.out.println(resultMap);
    }

}

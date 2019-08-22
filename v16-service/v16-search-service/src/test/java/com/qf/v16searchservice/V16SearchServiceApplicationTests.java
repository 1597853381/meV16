package com.qf.v16searchservice;

import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.service.ISearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V16SearchServiceApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Test
    public void testSynAllData() {
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getData());
    }

    @Test
    public void testQueryByKeywords(){
        ResultBean resultBean = searchService.queryByKeywords("商品");
        System.out.println(resultBean);
    }


    @Test
    public void updateByIdTest(){
        ResultBean resultBean = searchService.updateById(14L);
        System.out.println(resultBean.getData());

    }

}

package com.qf.springbootsolr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootSolrApplicationTests {

    @Autowired
    private SolrClient solrClient;
    //添加一条数据到默认的collection
    @Test
    public void addOrUpdateTest() throws IOException, SolrServerException {
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id", "903");
        document.addField("product_name", "mate42");
        document.addField("product_price", "66666");
        document.addField("product_sale_point", "我是商品描述");
        document.addField("product_images", "图片啊");
        solrClient.add(document);
        solrClient.commit();
    }

    //添加一条数据到指定的collection
    @Test
    public void addOrUpdateTest2() throws IOException, SolrServerException {
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id", "905");
        document.addField("product_name", "中国");
        document.addField("product_price", "66666");
        document.addField("product_sale_point", "我是商品描述");
        document.addField("product_images", "图片啊");
        solrClient.add("collection2", document);
        solrClient.commit("collection2");
    }

    @Test
    public void queryTest() throws IOException, SolrServerException {
        SolrQuery query=new SolrQuery();
        query.setQuery("product_name:中国");
        //QueryResponse response = solrClient.query(query);
        QueryResponse response = solrClient.query("collection2", query);
        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.getFieldValue("product_name"));
            System.out.println(result.getFieldValue("product_price"));
        }
    }

    @Test
    public void delTest() throws IOException, SolrServerException {
        solrClient.deleteByQuery("collection2","product_name:中国");
        solrClient.deleteByQuery("collection2", "id:904");
        solrClient.commit("collection2");
    }


}

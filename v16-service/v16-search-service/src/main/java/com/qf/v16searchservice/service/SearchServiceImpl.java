package com.qf.v16searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.entity.TProduct;
import com.qf.v16.mapper.TProductMapper;
import com.qf.v16.service.ISearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxinmin
 * @Date 2019/8/9
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    @Value("${image.server}")
    private String imageserver;

    /**
     * 全局数据库的数据导入到索引库中（系统初始化时执行一次）
     */
    @Override
    public ResultBean synAllData() {
        List<TProduct> list = productMapper.list();
        System.out.println(list.size());

        for (TProduct product : list) {
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id", product.getId());
            document.addField("product_name", product.getName());
            document.addField("product_price", product.getPrice());
            document.addField("product_sale_point", product.getSalePoint());
            document.addField("product_images", product.getImagess());

            try {
                solrClient.add("collection1",document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("500","添加索引库失败");
            }
            try {
                solrClient.commit("collection1");
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("500","提交到索引库失败");
            }

        }
        return new ResultBean("200","同步成功");
    }

    /**
    * 按关键字搜索
    **/
    @Override
    public ResultBean queryByKeywords(String keywords) {

        System.out.println("imageserver"+imageserver);
        SolrQuery query=new SolrQuery();
        if(!StringUtils.isAnyEmpty(keywords)){
            query.setQuery("product_name:"+keywords);
        }else{
            query.setQuery("product_name:mate30");
        }
        query.setHighlight(true);
        query.addHighlightField("product_name");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        try {
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();
            List<TProduct> productList=new ArrayList<>(results.size());
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            for (SolrDocument document : results) {
                TProduct product=new TProduct();
                //product.setId(Long.valueOf(document.getFieldValue("id").toString()));
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));

                //product.setName(document.getFieldValue("product_name").toString());未设置高亮
                //product.setPrice(Long.valueOf(document.getFieldValue("product_price").toString()));
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setImagess(imageserver+document.getFieldValue("product_images").toString());
                //设置高亮
                Map<String, List<String>> idHighloght = highlighting.get(document.getFieldValue("id"));
                List<String> productNameHighLighting = idHighloght.get("product_name");
                if(productNameHighLighting!=null&&productNameHighLighting.size()>0){
                    product.setName(productNameHighLighting.get(0));
                }else{
                    product.setName(document.getFieldValue("product_name").toString());
                }
                productList.add(product);
            }
            return new ResultBean("200",productList);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500","查询失败");
        }

    }

    @Override
    public ResultBean updateById(Long id) {
        TProduct product = productMapper.selectByPrimaryKey(id);

        SolrInputDocument document=new SolrInputDocument();
        document.addField("id", product.getId());
        document.addField("product_name", product.getName());
        document.addField("product_price", product.getPrice());
        document.addField("product_sale_point", product.getSalePoint());
        document.addField("product_images", product.getImagess());

        try {
            solrClient.add("collection1",document);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500","添加索引库失败");
        }
        try {
            solrClient.commit("collection1");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500","提交到索引库失败");
        }
        return new ResultBean("200","同步成功");
    }


}

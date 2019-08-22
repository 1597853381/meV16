package com.qf.v16productservice;

import com.qf.v16.entity.TProduct;
import com.qf.v16.entity.TProductType;
import com.qf.v16.service.IProductService;
import com.qf.v16.service.IProductTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V16ProductServiceApplicationTests {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductTypeService productTypeService;

    @Autowired
    private DataSource dataSource;
    @Test
    public void contextLoads() {
        TProduct product=productService.selectByPrimaryKey(1L);
        System.out.println(product.getName()+"    "+product.getPrice());
    }

    @Test
    public void testConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testPage(){
        List<TProduct> productList = productService.page(1, 1).getList();
        for (TProduct product : productList) {
            System.out.println(product.getName()+"   "+product.getPrice());
        }
    }

    @Test
    public void testList(){
        List<TProductType> productTypes=productTypeService.list();
        for (TProductType productType : productTypes) {
            System.out.println(productType.getName()+"  "+productType.getId());
        }
    }

}

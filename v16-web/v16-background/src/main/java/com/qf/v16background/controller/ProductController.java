package com.qf.v16background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qf.v16.common.base.constant.RabbitMQConstant;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.entity.TProduct;
import com.qf.v16.service.IProductService;
import com.qf.v16.service.ISearchService;
import com.qf.v16.service.vo.ProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xiaoxinmin
 * @Date 2019/8/5
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private ISearchService searchService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("getById/{id}")
    @ResponseBody
    public TProduct getById(@PathVariable("id") Long id) {
        return productService.selectByPrimaryKey(id);
    }

    @RequestMapping("list")
    public String list(Model model) {
        List<TProduct> productList = productService.list();
        model.addAttribute("productList", productList);
        return "product/list";
    }

    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(Model model, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize) {
        PageInfo<TProduct> pageInfo = productService.page(pageIndex, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "product/list";
    }

    //添加商品
    @RequestMapping("add")
    public String add(ProductVO vo) {
        Long newId = productService.add(vo);
//        searchService.updateById(newId);
        //发送消息到中间件
        rabbitTemplate.convertAndSend(RabbitMQConstant.BACKGROUND_PRODUCT_EXCHANGE, "product.add",newId);


        return "redirect:/product/page/1/1";
    }

    //根据id删除单个商品
    @RequestMapping("delById/{id}")
    @ResponseBody
    public ResultBean delById(@PathVariable("id") Long id) {
        int count = productService.deleteByPrimaryKey(id);
        if (count > 0) {
            return new ResultBean("200", "删除成功");
        }
        return new ResultBean("500", "删除失败");
    }

    //多选删除商品
    @RequestMapping("delByIds")
    @ResponseBody
    public ResultBean delByIds(@RequestParam("ids") List<Long> ids) {
        int count = productService.delByIds(ids);
        if (count > 0) {
            return new ResultBean("200", "删除成功");
        }
        return new ResultBean("500", "删除失败");
    }



}

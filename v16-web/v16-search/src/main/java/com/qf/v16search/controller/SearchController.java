package com.qf.v16search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoxinmin
 * @Date 2019/8/10
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("searchByKeywords")
    public String searchByKeywords(String keywords, Model model){
        ResultBean resultBean = searchService.queryByKeywords(keywords);
        model.addAttribute("result",resultBean);
        return "list";
    }
}

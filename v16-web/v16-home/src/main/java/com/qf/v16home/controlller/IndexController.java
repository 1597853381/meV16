package com.qf.v16home.controlller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v16.entity.TProductType;
import com.qf.v16.service.IProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xiaoxinmin
 * @Date 2019/8/8
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("home")
    public String showHome(Model model){
        List<TProductType> productTypes = productTypeService.list();
        model.addAttribute("productTypeList",productTypes);
//        for (TProductType productType : productTypes) {
//            System.out.println(productType.getName());
//        }
        return "home";
    }



}

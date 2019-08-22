package com.example.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.entity.TProduct;
import com.qf.v16.service.IProductService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xiaoxinmin
 * @Date 2019/8/12
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Reference
    private IProductService productService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ThreadPoolExecutor pool;

//    @Autowired
//    private ResultBean resultBean;;

    //创建静态页面
    @RequestMapping("creatById/{id}")
    @ResponseBody
    public ResultBean creatById(@PathVariable("id") Long id){
        Template template = null;
        try {
            template = configuration.getTemplate("item.ftl");
            TProduct product = productService.selectByPrimaryKey(id);
            Map<String,Object> data=new HashMap<>();
            data.put("product", product);
            //static路径
            String path = ResourceUtils.getURL("classpath:static").getPath();
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(path);
            stringBuilder.append(File.separator);
            stringBuilder.append(product.getId());
            stringBuilder.append(".html");
            String decode = URLDecoder.decode(stringBuilder.toString(), "utf-8");
            FileWriter out=new FileWriter(decode);
            template.process(data, out);
            return new ResultBean("200","生成成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }


        return new ResultBean("500","生成失败");
    }


    @RequestMapping("batchCreatByIds")
    public ResultBean batchCreatByIds(@PathVariable("ids") List<Long> ids) {
        //结果集和
        List<Future<Long>> results=new ArrayList<>(ids.size());
        for (Long id:ids) {
            Future<Long> future = pool.submit(new creatTask(id));
            results.add(future);
        }
        /*保存生成页面错误的id集合*/
        List<Long> errors=new ArrayList<>();
        for (Future<Long> result : results) {
            try {
                Long resultNo = result.get();
                if(resultNo != 0){
                    errors.add(resultNo);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //处理生成失败的任务
        if(errors.size()>0){
            //TODO 记录日志表中（id，product_id，creatDate,resend） 负责记录
            //开启定时任务 扫描日志表 讲生成失败的重新执行 最大重置次数
            return new ResultBean("500","部分页面生成失败 请查看日志");
        }
        return new ResultBean("200","生成队列成功");
    }

    /**
     * 创建静态页面的任务
    */
    class creatTask implements Callable<Long> {

        private Long id;

        public creatTask(Long id){
            this.id=id;
        }

        @Override
        public Long call() throws Exception {
            Template template = null;
            try {
                template = configuration.getTemplate("item.ftl");
                TProduct product = productService.selectByPrimaryKey(id);
                Map<String,Object> data=new HashMap<>();
                data.put("product", product);
                //static路径
                String path = ResourceUtils.getURL("classpath:static").getPath();
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(path);
                stringBuilder.append(File.separator);
                stringBuilder.append(product.getId());
                stringBuilder.append(".html");
                String decode = URLDecoder.decode(stringBuilder.toString(), "utf-8");
                FileWriter out=new FileWriter(decode);
                template.process(data, out);
                return 0L;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }


            return id;
        }
        }
    }


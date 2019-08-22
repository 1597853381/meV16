package com.qf.v16search.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v16.common.base.constant.RabbitMQConstant;
import com.qf.v16.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxinmin
 * @Date 2019/8/13
 * 处理消息
 */
@Component
public class MessageHandler {

    @Reference
    private ISearchService searchService;

    @RabbitListener(queues = RabbitMQConstant.ADDORUPDATE_SEARCH_QUEUE)
    @RabbitHandler
    public void processAdd(Long id){
        System.out.println(id);
        searchService.updateById(id);
    }
}

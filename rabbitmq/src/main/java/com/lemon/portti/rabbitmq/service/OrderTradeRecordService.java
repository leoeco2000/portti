package com.lemon.portti.rabbitmq.service;


import com.lemon.portti.persistence.entity.OrderTradeRecord;
import com.lemon.portti.persistence.entity.User;
import com.lemon.portti.persistence.mapper.OrderTradeRecordMapper;
import com.lemon.portti.dto.OrderTradeRecordRequest;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class OrderTradeRecordService {

  private static final Logger log = LoggerFactory.getLogger(OrderTradeRecordService.class);

  @Autowired
  private Environment env;

  @Autowired
  public OrderTradeRecordMapper orderTradeRecordMapper;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void createTradeRecord(OrderTradeRecordRequest requestData) throws Exception{
    //TODO:其余业务逻辑上的校验。。

    //TODO：创建交易记录
    OrderTradeRecord record=new OrderTradeRecord();
    BeanUtils.copyProperties(requestData,record);
    record.setCreateTime(new Date());
    record.setStatus(1);
    orderTradeRecordMapper.insertSelective(record);

    //TODO：设置超时，用mq处理已超时的下单记录（一旦记录超时，则处理为无效）
    final Long ttl=env.getProperty("trade.record.ttl",Long.class);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    rabbitTemplate.setExchange(env.getProperty("register.delay.exchange.name"));
    rabbitTemplate.setRoutingKey("");
    rabbitTemplate.convertAndSend(record, new MessagePostProcessor() {
      @Override
      public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, User.class.getName());
        message.getMessageProperties().setExpiration(ttl+"");
        return message;
      }
    });
  }

}
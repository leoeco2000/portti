package com.lemon.portti.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class DeadQueueConfig {

  @Autowired
  private Environment env;

  //TODO：死信队列消息模型

  //创建死信队列
  @Bean
  public Queue simpleDeadQueue() {
    Map<String, Object> args = new HashMap();

    args.put("x-dead-letter-exchange", env.getProperty("simple.dead.exchange.name"));
    args.put("x-dead-letter-routing-key", env.getProperty("simple.dead.routing.key.name"));
    args.put("x-message-ttl", 10000);

    return new Queue(env.getProperty("simple.dead.queue.name"), true, false, false, args);
  }

  //绑定死信队列-面向生产端
  @Bean
  public TopicExchange simpleDeadExchange() {
    return new TopicExchange(env.getProperty("simple.produce.exchange.name"), true, false);
  }

  @Bean
  public Binding simpleDeadBinding() {
    return BindingBuilder.bind(simpleDeadQueue()).to(simpleDeadExchange())
        .with(env.getProperty("simple.produce.routing.key.name"));
  }


  //创建并绑定实际监听消费队列
  @Bean
  public Queue simpleDeadRealQueue() {
    return new Queue(env.getProperty("simple.dead.real.queue.name"), true);
  }

  @Bean
  public TopicExchange simpleDeadRealExchange() {
    return new TopicExchange(env.getProperty("simple.dead.exchange.name"), true, false);
  }

  @Bean
  public Binding simpleDeadRealBinding() {
    return BindingBuilder.bind(simpleDeadRealQueue()).to(simpleDeadRealExchange())
        .with(env.getProperty("simple.dead.routing.key.name"));
  }

  //TODO：用户下单支付超时死信队列模型

  @Bean
  public Queue userOrderDeadQueue() {
    Map<String, Object> args = new HashMap();
    args.put("x-dead-letter-exchange", env.getProperty("user.order.dead.exchange.name"));
    args.put("x-dead-letter-routing-key", env.getProperty("user.order.dead.routing.key.name"));
    args.put("x-message-ttl", 10000);

    return new Queue(env.getProperty("user.order.dead.queue.name"), true, false, false, args);
  }

  //绑定死信队列-面向生产端
  @Bean
  public TopicExchange userOrderDeadExchange() {
    return new TopicExchange(env.getProperty("user.order.dead.produce.exchange.name"), true, false);
  }

  @Bean
  public Binding userOrderDeadBinding() {
    return BindingBuilder.bind(userOrderDeadQueue()).to(userOrderDeadExchange())
        .with(env.getProperty("user.order.dead.produce.routing.key.name"));
  }

  //创建并绑定实际监听消费队列-面向消费端
  @Bean
  public Queue userOrderDeadRealQueue() {
    return new Queue(env.getProperty("user.order.dead.real.queue.name"), true);
  }


  @Bean
  public TopicExchange userOrderDeadRealExchange() {
    return new TopicExchange(env.getProperty("user.order.dead.exchange.name"));
  }

  @Bean
  public Binding userOrderDeadRealBinding() {
    return BindingBuilder.bind(userOrderDeadRealQueue()).to(userOrderDeadRealExchange())
        .with(env.getProperty("user.order.dead.routing.key.name"));
  }

  //TODO：死信队列动态设置TTL消息模型

  @Bean
  public Queue dynamicDeadQueue() {
    Map<String, Object> args = new HashMap();
    args.put("x-dead-letter-exchange", env.getProperty("dynamic.dead.exchange.name"));
    args.put("x-dead-letter-routing-key", env.getProperty("dynamic.dead.routing.key.name"));

    return new Queue(env.getProperty("dynamic.dead.queue.name"), true, false, false, args);
  }

  @Bean
  public TopicExchange dynamicDeadExchange() {
    return new TopicExchange(env.getProperty("dynamic.dead.produce.exchange.name"), true, false);
  }

  @Bean
  public Binding dynamicDeadBinding() {
    return BindingBuilder.bind(dynamicDeadQueue()).to(dynamicDeadExchange())
        .with(env.getProperty("dynamic.dead.produce.routing.key.name"));
  }


  @Bean
  public Queue dynamicDeadRealQueue() {
    return new Queue(env.getProperty("dynamic.dead.real.queue.name"), true);
  }


  @Bean
  public TopicExchange dynamicDeadRealExchange() {
    return new TopicExchange(env.getProperty("dynamic.dead.exchange.name"));
  }

  @Bean
  public Binding dynamicDeadRealBinding() {
    return BindingBuilder.bind(dynamicDeadRealQueue()).to(dynamicDeadRealExchange())
        .with(env.getProperty("dynamic.dead.routing.key.name"));
  }

}

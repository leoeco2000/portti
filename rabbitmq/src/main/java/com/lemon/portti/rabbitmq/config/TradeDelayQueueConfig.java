package com.lemon.portti.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class TradeDelayQueueConfig {

  @Autowired
  private Environment env;

  /**延迟队列配置**/

  @Bean(name = "registerDelayQueue")
  public Queue registerDelayQueue(){
    Map<String, Object> params = new HashMap<>();
    params.put("x-dead-letter-exchange",env.getProperty("register.exchange.name"));
    params.put("x-dead-letter-routing-key","all");
    return new Queue(env.getProperty("register.delay.queue.name"), true,false,false,params);
  }

  @Bean
  public DirectExchange registerDelayExchange(){
    return new DirectExchange(env.getProperty("register.delay.exchange.name"));
  }

  @Bean
  public Binding registerDelayBinding(){
    return BindingBuilder.bind(registerDelayQueue()).to(registerDelayExchange()).with("");
  }

  /**延迟队列配置**/

  /**指标消费队列配置**/

  @Bean
  public TopicExchange registerTopicExchange(){
    return new TopicExchange(env.getProperty("register.exchange.name"));
  }

  @Bean(name = "registerQueue")
  public Queue registerQueue(){
    return new Queue(env.getProperty("register.queue.name"),true);
  }

  @Bean
  public Binding registerBinding(){
    return BindingBuilder.bind(registerQueue()).to(registerTopicExchange()).with("all");
  }

  /**指标消费队列配置**/

}

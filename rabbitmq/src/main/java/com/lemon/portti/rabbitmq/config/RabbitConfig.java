package com.lemon.portti.rabbitmq.config;

import com.lemon.portti.rabbitmq.consumer.SimpleListener;
import com.lemon.portti.rabbitmq.consumer.UserOrderListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * rabbitmq配置类
 */
@Configuration
@Slf4j
public class RabbitConfig {

  @Autowired
  private Environment env;

  @Autowired
  private CachingConnectionFactory connectionFactory;

  @Autowired
  private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

  @Autowired
  private SimpleListener simpleListener;

  @Autowired
  private UserOrderListener userOrderListener;


  /**
   * 单一消费者
   */
  @Bean(name = "singleListenerContainer")
  public SimpleRabbitListenerContainerFactory listenerContainer() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    factory.setConcurrentConsumers(1);
    factory.setMaxConcurrentConsumers(1);
    factory.setPrefetchCount(1);
    factory.setTxSize(1);
    factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
    return factory;
  }

  /**
   * 多个消费者
   */
  @Bean(name = "multiListenerContainer")
  public SimpleRabbitListenerContainerFactory multiListenerContainer() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factoryConfigurer.configure(factory, connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());

    factory.setAcknowledgeMode(AcknowledgeMode.NONE);
    factory.setConcurrentConsumers(
        env.getProperty("spring.rabbitmq.listener.simple.concurrency", int.class));
    factory.setMaxConcurrentConsumers(
        env.getProperty("spring.rabbitmq.listener.simple.max-concurrency", int.class));
    factory
        .setPrefetchCount(env.getProperty("spring.rabbitmq.listener.simple.prefetch", int.class));
    return factory;
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    connectionFactory.setPublisherConfirms(true);
    connectionFactory.setPublisherReturns(true);
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
      @Override
      public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
      }
    });
    rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
      @Override
      public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
          String routingKey) {
        log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange,
            routingKey, replyCode, replyText, message);
      }
    });
    return rabbitTemplate;
  }

  //TODO：用户操作日志消息模型

  @Bean(name = "logUserQueue")
  public Queue logUserQueue(){
    return new Queue(env.getProperty("log.user.queue.name"),true);
  }

  @Bean
  public DirectExchange logUserExchange(){
    return new DirectExchange(env.getProperty("log.user.exchange.name"),true,false);
  }

  @Bean
  public Binding logUserBinding(){
    return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(env.getProperty("log.user.routing.key.name"));
  }

  //TODO：发送邮件消息模型
  @Bean
  public Queue mailQueue(){
    return new Queue(env.getProperty("mail.queue.name"),true);
  }

  @Bean
  public DirectExchange mailExchange(){
    return new DirectExchange(env.getProperty("mail.exchange.name"),true,false);
  }

  @Bean
  public Binding mailBinding(){
    return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(env.getProperty("mail.routing.key.name"));
  }

  //TODO：基本消息模型构建
  @Bean
  public DirectExchange basicExchange(){
    return new DirectExchange(env.getProperty("basic.info.mq.exchange.name"), true,false);
  }

  @Bean(name = "basicQueue")
  public Queue basicQueue(){
    return new Queue(env.getProperty("basic.info.mq.queue.name"), true);
  }

  @Bean
  public Binding basicBinding(){
    return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("basic.info.mq.routing.key.name"));
  }

  //TODO：商品抢单消息模型创建
  @Bean
  public DirectExchange robbingExchange(){
    return new DirectExchange(env.getProperty("product.robbing.mq.exchange.name"), true,false);
  }

  @Bean(name = "robbingQueue")
  public Queue robbingQueue(){
    return new Queue(env.getProperty("product.robbing.mq.queue.name"), true);
  }

  @Bean
  public Binding robbingBinding(){
    return BindingBuilder.bind(robbingQueue()).to(robbingExchange()).with(env.getProperty("product.robbing.mq.routing.key.name"));
  }

  //TODO：并发配置-消息确认机制-listener
  @Bean(name = "simpleQueue")
  public Queue simpleQueue(){
    return new Queue(env.getProperty("simple.mq.queue.name"),true);
  }

  @Bean
  public TopicExchange simpleExchange(){
    return new TopicExchange(env.getProperty("simple.mq.exchange.name"),true,false);
  }

  @Bean
  public Binding simpleBinding(){
    return BindingBuilder.bind(simpleQueue()).to(simpleExchange()).with(env.getProperty("simple.mq.routing.key.name"));
  }

  @Bean(name = "simpleContainer")
  public SimpleMessageListenerContainer simpleContainer(@Qualifier("simpleQueue") Queue simpleQueue){
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    //TODO 现在必须用 ListenerAdapter
//    container.setMessageConverter(new Jackson2JsonMessageConverter());
    //TODO 支持json格式的转换器
    MessageListenerAdapter adapter = new MessageListenerAdapter(simpleListener);
//     adapter.setDefaultListenerMethod("consumeMessage");

    Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    adapter.setMessageConverter(jackson2JsonMessageConverter);

//    container.setMessageListener(userOrderListener);
    container.setMessageListener(adapter);

    //TODO：并发配置
    container.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.concurrency",Integer.class));
    container.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.max-concurrency",Integer.class));
    container.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.simple.prefetch",Integer.class));

    //TODO：消息确认-确认机制种类
    container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    container.setQueues(simpleQueue);
//    container.setMessageListener(simpleListener);

    return container;
  }


  //TODO：用户商城抢单实战
  @Bean(name = "userOrderQueue")
  public Queue userOrderQueue(){
    return new Queue(env.getProperty("user.order.queue.name"),true);
  }

  @Bean
  public TopicExchange userOrderExchange(){
    return new TopicExchange(env.getProperty("user.order.exchange.name"),true,false);
  }

  @Bean
  public Binding userOrderBinding(){
    return BindingBuilder.bind(userOrderQueue()).to(userOrderExchange()).with(env.getProperty("user.order.routing.key.name"));
  }

  @Bean
  public SimpleMessageListenerContainer listenerContainerUserOrder(@Qualifier("userOrderQueue") Queue userOrderQueue){
    SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);

    //TODO：并发配置
    container.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.concurrency",Integer.class));
    container.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.max-concurrency",Integer.class));
    container.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.simple.prefetch",Integer.class));

    //TODO 方法已经废止，现在必须用 ListenerAdapter
    //TODO 如果需要 MessageConverter , 就要将message处理类作为委托类
//    container.setMessageConverter(new Jackson2JsonMessageConverter());

    //TODO 支持json格式的转换器
     MessageListenerAdapter adapter = new MessageListenerAdapter(userOrderListener);
//     adapter.setDefaultListenerMethod("consumeMessage");

     Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
     adapter.setMessageConverter(jackson2JsonMessageConverter);

//    container.setMessageListener(userOrderListener);
     container.setMessageListener(adapter);


    //TODO:消息确认机制
    container.setQueues(userOrderQueue);

    container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

    return container;
  }



}


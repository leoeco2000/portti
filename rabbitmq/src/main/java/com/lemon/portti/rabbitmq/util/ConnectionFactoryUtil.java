package com.lemon.portti.rabbitmq.util;

import com.rabbitmq.client.ConnectionFactory;

public class ConnectionFactoryUtil {

  private static class ConnectionFactoryHolder {

    // 创建连接
    static final ConnectionFactory instance = new ConnectionFactory();
    // 设置 RabbitMQ 的主机名
    static {
      instance.setHost("118.25.37.36");
      instance.setPort(5672);
      instance.setUsername("test");
      instance.setPassword("123456");
      instance.setVirtualHost("vhost_test");
    }

  }

  public static ConnectionFactory instance(){
    return ConnectionFactoryHolder.instance;
  }
}

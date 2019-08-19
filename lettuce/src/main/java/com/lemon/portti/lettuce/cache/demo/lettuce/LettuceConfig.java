package com.lemon.portti.lettuce.cache.demo.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.RedisURI.Builder;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


//@Primary
//@Configuration
public class LettuceConfig {

  private static RedisURI redisUri;

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${redis.host:127.0.0.1}")
  private String hostName;

  @Value("${redis.domainsocket:}")
  private String socket;

  @Value("${redis.port:6379}")
  private int port;

  private int dbIndex = 2;

  @Value(value = "${redis.pass:}")
  private String password;

  @Bean(destroyMethod = "shutdown")
  ClientResources clientResources() {
    return DefaultClientResources.create();
  }

  @Bean(destroyMethod = "close")
  StatefulRedisConnection<String, String> redisConnection(RedisClient redisClient) {
    return redisClient.connect();
  }

  private RedisURI createRedisURI() {
    Builder builder = null;
    // 判断是否有配置UDS信息，以及判断Redis是否有支持UDS连接方式，是则用UDS，否则用TCP
    if (StringUtils.isNotBlank(socket) && Files.exists(Paths.get(socket))) {
      builder = Builder.socket(socket);
      System.out.println("connect with Redis by Unix domain Socket");
      log.info("connect with Redis by Unix domain Socket");
    } else {
      builder = Builder.redis(hostName, port);
      System.out.println("connect with Redis by TCP Socket");
      log.info("connect with Redis by TCP Socket");
    }
    builder.withDatabase(dbIndex);
    if (StringUtils.isNotBlank(password)) {
      builder.withPassword(password);
    }
    return builder.build();
  }

  @PostConstruct
  void init() {
    redisUri = createRedisURI();
    log.info("连接Redis成功！\n host:" + hostName + ":" + port + " pass:" + password + " dbIndex:" + dbIndex);
  }

  @Bean(destroyMethod = "shutdown")
  RedisClient redisClient(ClientResources clientResources) {
    return RedisClient.create(clientResources, redisUri);
  }

  public void setDbIndex(int dbIndex) {
    this.dbIndex = dbIndex;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setSocket(String socket) {
    this.socket = socket;
  }

}

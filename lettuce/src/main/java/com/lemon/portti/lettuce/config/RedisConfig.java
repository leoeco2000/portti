package com.lemon.portti.lettuce.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//@AutoConfigureAfter(RedisAutoConfiguration.class)
// 必须加，使配置生效
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

//  @Value("${spring.redis.cache.nodes:}")
//  private String nodes;
//  @Value("${spring.redis.cache.host:}")
//  private String host;
//  @Value("${spring.redis.cache.password:}")
//  private String password;
//  @Value("${spring.redis.cache.maxIdle:}")
//  private Integer maxIdle;
//  @Value("${spring.redis.cache.minIdle:}")
//  private Integer minIdle;
//  @Value("${spring.redis.cache.maxTotal:}")
//  private Integer maxTotal;
//  @Value("${spring.redis.cache.maxWaitMillis:}")
//  private Long maxWaitMillis;


  private Duration timeToLive = Duration.ofSeconds(60);

  @Autowired
  RedisProperties redisProperties;

  @Autowired
  LettuceConnectionFactory factory;
/*
  @Bean
  LettuceConnectionFactory lettuceConnectionFactory() {
    // 连接池配置
    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    poolConfig.setMaxIdle(maxIdle == null ? 8 : maxIdle);
    poolConfig.setMinIdle(minIdle == null ? 1 : minIdle);
    poolConfig.setMaxTotal(maxTotal == null ? 8 : maxTotal);
    poolConfig.setMaxWaitMillis(maxWaitMillis == null ? 5000L : maxWaitMillis);
    LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
        .poolConfig(poolConfig)
        .build();
    // 单机redis
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName(host==null||"".equals(host)?"localhost":host.split(":")[0]);
    redisConfig.setPort(Integer.valueOf(host==null||"".equals(host)?"6379":host.split(":")[1]));
    if (password != null && !"".equals(password)) {
      redisConfig.setPassword(password);
    }

    // 哨兵redis
    // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();

    // 集群redis
        *//*RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        Set<RedisNode> nodeses = new HashSet<>();
        String[] hostses = nodes.split("-");
        for (String h : hostses) {
            h = h.replaceAll("\\s", "").replaceAll("\n", "");
            if (!"".equals(h)) {
                String host = h.split(":")[0];
                int port = Integer.valueOf(h.split(":")[1]);
                nodeses.add(new RedisNode(host, port));
            }
        }
        redisConfig.setClusterNodes(nodeses);
        // 跨集群执行命令时要遵循的最大重定向数量
        redisConfig.setMaxRedirects(3);
        redisConfig.setPassword(password);*//*

    return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
  }*/

  /**
   * RedisTemplate配置
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(factory);

    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);

    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    // key采用String的序列化方式
    template.setKeySerializer(stringRedisSerializer);
    // hash的key也采用String的序列化方式
    template.setHashKeySerializer(stringRedisSerializer);
    // value序列化方式采用jackson
    template.setValueSerializer(jackson2JsonRedisSerializer);
    // hash的value序列化方式采用jackson
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    // 如果有些属性没有设置，通过这个方法判断然后补全
    template.afterPropertiesSet();

    return template;
  }

  @Bean
  public RedisTemplate<String, Serializable> redisCacheTemplate(
      LettuceConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Serializable> template = new RedisTemplate<>();
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    //  设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
    // 使用：进行分割，可以很多显示出层级关系
    // 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下
    return (target, method, params) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(target.getClass().getName());
      sb.append(":");
      sb.append(method.getName());
      for (Object obj : params) {
        sb.append(":" + String.valueOf(obj));
      }
      String rsToUse = String.valueOf(sb);
      log.info("自动生成Redis Key -> [{}]", rsToUse);
      return rsToUse;
    };
  }

  @Bean
  @Override
  public CacheManager cacheManager() {

    // 初始化缓存管理器，在这里我们可以缓存的整体过期时间什么的，我这里默认没有配置
    log.info("初始化 -> [{}]", "CacheManager RedisCacheManager Start");

    //关键点，spring cache的注解使用的序列化都从这来，没有这个配置的话使用的jdk自己的序列化，实际上不影响使用，只是打印出来不适合人眼识别
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))//key序列化方式
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))//value序列化方式
        .disableCachingNullValues()
        .entryTtl(timeToLive);//缓存过期时间

    RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
        .RedisCacheManagerBuilder
        .fromConnectionFactory(factory)
        //TODO
        .cacheDefaults(config)
        .transactionAware();

//    Set<String> cacheNames = new HashSet<String>() {
//      {
//        add("codeNameCache");
//      }
//    };
//    builder.initialCacheNames(cacheNames);

    return builder.build();
  }

  private RedisSerializer<String> keySerializer() {
    return new StringRedisSerializer();
  }

  private RedisSerializer<Object> valueSerializer() {
    // 设置序列化
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
        Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    return jackson2JsonRedisSerializer;

    //或者使用GenericJackson2JsonRedisSerializer
    //return new GenericJackson2JsonRedisSerializer();
  }

  @Override
  @Bean
  public CacheErrorHandler errorHandler() {
    // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
    log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
    CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
      @Override
      public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
        log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
      }

      @Override
      public void handleCachePutError(RuntimeException e, Cache cache, Object key,
          Object value) {
        log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value,
            e);
      }

      @Override
      public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
        log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
      }

      @Override
      public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.error("Redis occur handleCacheClearError：", e);
      }
    };
    return cacheErrorHandler;
  }

}
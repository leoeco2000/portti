package com.lemon.portti.lettuce.cache.demo;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class RedisCache implements Cache {

  private String name;
  private static JdkSerializationRedisSerializer redisSerializer;

  @Autowired
  private StatefulRedisConnection<String, String> redisConnection;

  public RedisCache() {
    redisSerializer = new JdkSerializationRedisSerializer();
    name = RedisCacheConst.REDIS_CACHE_NAME;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Object getNativeCache() {
    // 返回redis连接看似奇葩，但redis连接就是操作底层实现缓存的对象
    return getRedisConnection();
  }

  @Override
  public ValueWrapper get(Object key) {
    RedisCommands<String, String> redis = redisConnection.sync();
    String redisKey = (String) key;

    String serializable = redis.get(redisKey);
    if (serializable == null) {
      System.out.println("-------缓存不存在------");
      return null;
    }
    System.out.println("---获取缓存中的对象---");
    Object value = null;
    // 序列化转化成字节时，声明编码RedisCacheConst.SERIALIZE_ENCODE（ISO-8859-1），
    // 否则转换很容易出错（编码为UTF-8也会转换错误）
    try {
      value = redisSerializer
          .deserialize(serializable.getBytes(RedisCacheConst.SERIALIZE_ENCODE));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return new SimpleValueWrapper(value);

  }

  @Override
  public <T> T get(Object key, Class<T> type) {
    System.out.println("------未实现get(Object key, Class<T> type)------");
    return null;
  }

  @Override
  public <T> T get(Object key, Callable<T> valueLoader) {
    System.out.println("---未实现get(Object key, Callable<T> valueLoader)---");
    return null;
  }

  @Override
  public void put(Object key, Object value) {
    System.out.println("-------加入缓存------");
    RedisCommands<String, String> redis = redisConnection.sync();
    String redisKey = (String) key;
    byte[] serialize = redisSerializer.serialize(value);
    try {
      redis.set(redisKey, new String(serialize, RedisCacheConst.SERIALIZE_ENCODE));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ValueWrapper putIfAbsent(Object key, Object value) {
    System.out.println("---未实现putIfAbsent(Object key, Object value)---");
    return null;
  }

  @Override
  public void evict(Object key) {
    System.out.println("-------删除缓存 key=" + key.toString() + " ------");
    RedisCommands<String, String> redis = redisConnection.sync();
    String redisKey = key.toString();
    // RedisCacheConst.WILDCARD是Redis中键的通配符“*”，用在这里使键值删除也能使用通配方式
    if (redisKey.contains(RedisCacheConst.WILDCARD)) {
      List<String> caches = redis.keys(redisKey);
      if (!caches.isEmpty()) {
        redis.del(caches.toArray(new String[caches.size()]));
      }
    } else {
      redis.del(redisKey);
    }
  }

  @Override
  public void clear() {
    System.out.println("-------清空缓存------");
    RedisCommands<String, String> redis = redisConnection.sync();
    redis.flushdb();
  }

  public void setName(String name) {
    this.name = name;
  }

  public StatefulRedisConnection<String, String> getRedisConnection() {
    return redisConnection;
  }

  public void setRedisConnection(StatefulRedisConnection<String, String> redisConnection) {
    this.redisConnection = redisConnection;
  }
}
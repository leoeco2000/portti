package com.lemon.portti.lettuce.cache.demo;

public class RedisCacheConst {
  public final static String REDIS_CACHE_NAME = "Redis Cache";
  public final static String SERIALIZE_ENCODE = "ISO-8859-1";
  public final static String WILDCARD = "*";
  public final static String SPRING_KEY_TAG = "'";
  // SpEL中普通的字符串要加上单引号，如一个键设为kanarien，应为key="'kanarien'"
}
package com.lemon.portti.lettuce;


//@RestController
//@RequestMapping(value = "/string")
public class RedisStringController {
//
//  @Autowired
//  private StringRedisTemplate stringRedisTemplate;
//
//  @Autowired
//  private RedisTemplate redisTemplate;
//
//  @PutMapping(value = "/put")
//  public void put(String key, @RequestParam(required = false, defaultValue = "default") String value) {
//    stringRedisTemplate.opsForValue().set(key, value);
//  }
//
//  @GetMapping(value = "/get")
//  public Object get(String key) {
//    return stringRedisTemplate.opsForValue().get(key);
//  }
//
//  //动态切换16个库------------------------------------------------------------------------------------------------------------------------------------------------
//  @RequestMapping(value = "test")
//  public void test(){
////    LettuceConnectionFactory lettuce = (LettuceConnectionFactory) stringRedisTemplate.getConnectionFactory();
////    lettuce.setDatabase(5);
////    LettuceClientConfiguration old = lettuce.getClientConfiguration();
////    RedisStandaloneConfiguration newone =
//////    stringRedisTemplate.setConnectionFactory(lettuce);
////    lettuce.resetConnection();
//
//    stringRedisTemplate.opsForValue().set("aaaa","测试一下，能出来嘛，哇，出来了呢"/*,1, TimeUnit.MINUTES*/);
//
//  }
}
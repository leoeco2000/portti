package com.lemon.portti.web.controller.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rabbit")
public class TestController {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @GetMapping("/test")
  public String test(){
    CorrelationData correlationData = new CorrelationData("0987654321");
    rabbitTemplate.convertAndSend("local.log.user.exchange", "local.log.user.routing.key", "my message....", correlationData);
    return null;
  }
}

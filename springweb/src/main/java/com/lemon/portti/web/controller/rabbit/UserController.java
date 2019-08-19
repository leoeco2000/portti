package com.lemon.portti.web.controller.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.portti.persistence.entity.User;
import com.lemon.portti.persistence.entity.UserLog;
import com.lemon.portti.persistence.service.UserService;
import com.lemon.portti.response.BaseResponse;
import com.lemon.portti.response.StatusCode;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @PostMapping("/login")
    public BaseResponse login(@RequestParam("userName") String userName,@RequestParam("password") String password){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            User user = userService.selectByUserNamePassword(userName,password);
            if (user!=null){

                //TODO：异步写用户日志
                try {
                    UserLog userLog=new UserLog(userName,"Login","login",objectMapper.writeValueAsString(user));
                    userLog.setCreateTime(new Date());
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(env.getProperty("log.user.exchange.name"));
                    rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name"));

                    Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(userLog)).setDeliveryMode(
                        MessageDeliveryMode.PERSISTENT).build();
                    message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON); //发送消息写法二
                    rabbitTemplate.convertAndSend(message);


                    /*UserLog log=new UserLog(userName,"Login","login",objectMapper.writeValueAsString(user));
                    userLogMapper.insertSelective(log);*/ //同步

                    /*MessageProperties properties=new MessageProperties();
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    Message message=new Message(objectMapper.writeValueAsBytes(userLog),properties);*/ //发送消息写法一
                }catch (Exception e){
                    e.printStackTrace();
                }

                //TODO：塞权限数据-资源数据-视野数据

            }else{
                response=new BaseResponse(StatusCode.Fail);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


}




























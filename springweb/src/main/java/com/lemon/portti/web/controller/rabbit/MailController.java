package com.lemon.portti.web.controller.rabbit;

import com.lemon.portti.response.BaseResponse;
import com.lemon.portti.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Administrator on 2018/9/1.
 */
@RestController
@RequestMapping("/mail")
@Slf4j
public class MailController {
//    private static final Logger log= LoggerFactory.getLogger(MailController.class);

//    private static final String Prefix="mail";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

//    @RequestMapping(value = Prefix+"/send",method = RequestMethod.GET)
    @GetMapping("/login")
    public BaseResponse sendMail(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            rabbitTemplate.setExchange(env.getProperty("mail.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mail.routing.key.name"));
            rabbitTemplate.convertAndSend(MessageBuilder.withBody("mail发送".getBytes("UTF-8")).build());

        }catch (Exception e){
            e.printStackTrace();
        }

        log.info("邮件发送完毕----");
        return response;
    }
}
























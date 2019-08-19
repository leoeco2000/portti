package com.lemon.portti.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.portti.persistence.entity.UserLog;
import com.lemon.portti.persistence.service.UserLogService;
import com.lemon.portti.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CommonMqListener {

    private static final Logger log= LoggerFactory.getLogger(CommonMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private MailService mailService;

    /**
     * 监听消费用户日志
     * @param message
     */
    @RabbitListener(queues = "${log.user.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(@Payload byte[] message){
        try {
            UserLog userLog=objectMapper.readValue(message, UserLog.class);
            log.info("监听消费用户日志 监听到消息： {} ",userLog);

            userLogService.save(userLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 监听消费邮件发送
     * @param message
     */
    @RabbitListener(queues = "${mail.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMailQueue(@Payload byte[] message){
        try {
            log.info("监听消费邮件发送 监听到消息： {} ",new String(message,"UTF-8"));

            mailService.sendEmail();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 监听消费死信队列中的消息
     * @param message
     */
//    @RabbitListener(queues = "${simple.dead.real.queue.name}",containerFactory = "singleListenerContainer")
//    public void consumeDeadQueue(@Payload byte[] message){
//        try {
//            log.info("监听消费死信队列中的消息： {} ",new String(message,"UTF-8"));
//
//            mailService.sendEmail();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


}































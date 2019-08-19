package com.lemon.portti.web.controller.rabbit;

import com.lemon.portti.rabbitmq.service.InitService;
import com.lemon.portti.response.BaseResponse;
import com.lemon.portti.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("concurrency")
@Slf4j
public class ConcurrencyController {

//    private static final Logger log= LoggerFactory.getLogger(ConcurrencyController.class);

//    private static final String Prefix="concurrency";

    @Autowired
    private InitService initService;

    @GetMapping(value = "/robbing/thread")
    public BaseResponse robbingThread(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        initService.generateMultiThread();
        return response;
    }
}








































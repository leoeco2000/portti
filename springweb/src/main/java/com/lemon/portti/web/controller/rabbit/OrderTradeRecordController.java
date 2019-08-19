package com.lemon.portti.web.controller.rabbit;

import com.lemon.portti.dto.OrderTradeRecordRequest;
import com.lemon.portti.rabbitmq.service.OrderTradeRecordService;
import com.lemon.portti.response.BaseResponse;
import com.lemon.portti.response.StatusCode;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单交易记录controller
 * Created by steadyjack on 2017/12/11.
 */
@RestController
public class OrderTradeRecordController {

  private static final Logger log= LoggerFactory.getLogger(OrderTradeRecordController.class);

  private static final String prefix="order/trade/record";

  @Autowired
  private OrderTradeRecordService orderTradeRecordService;

  /**
   * 创建用户下单记录
   * @param requestData
   * @param bindingResult
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "order/trade/record/create",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public BaseResponse createRecord(@Valid @RequestBody OrderTradeRecordRequest requestData, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()){
      return new BaseResponse(StatusCode.Invalid_Params);
    }
    BaseResponse response=new BaseResponse(StatusCode.Success);
    try {
      orderTradeRecordService.createTradeRecord(requestData);
    }catch (Exception e){
      log.error("用户下单记录异常：{} ",requestData,e.fillInStackTrace());
      return new BaseResponse(StatusCode.Fail);
    }
    return response;
  }


}

package com.lemon.portti.dto;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单交易记录request
 * Created by steadyjack on 2017/12/11.
 */
public class OrderTradeRecordRequest implements Serializable{

  @NotNull
  private Integer customerId;
  @NotNull
  private Integer orderId;
  @NotNull
  private BigDecimal price;

  private Integer status=0;


  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "OrderTradeRecordRequest{" +
        "customerId=" + customerId +
        ", orderId=" + orderId +
        ", price=" + price +
        ", status=" + status +
        '}';
  }
}
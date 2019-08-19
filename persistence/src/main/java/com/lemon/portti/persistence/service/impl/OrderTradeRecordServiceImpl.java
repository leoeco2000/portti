package com.lemon.portti.persistence.service.impl;

import com.lemon.portti.persistence.entity.OrderTradeRecord;
import com.lemon.portti.persistence.mapper.OrderTradeRecordMapper;
import com.lemon.portti.persistence.service.OrderTradeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单交易记录 服务实现类
 * </p>
 *
 * @author lemon
 * @since 2019-08-18
 */
@Service
public class OrderTradeRecordServiceImpl extends ServiceImpl<OrderTradeRecordMapper, OrderTradeRecord> implements
    OrderTradeRecordService {

}

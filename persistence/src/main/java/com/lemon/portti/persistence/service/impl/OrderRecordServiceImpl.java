package com.lemon.portti.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.portti.persistence.entity.OrderRecord;
import com.lemon.portti.persistence.mapper.OrderRecordMapper;
import com.lemon.portti.persistence.service.OrderRecordService;
import org.springframework.stereotype.Service;

@Service
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord> implements
    OrderRecordService {

}

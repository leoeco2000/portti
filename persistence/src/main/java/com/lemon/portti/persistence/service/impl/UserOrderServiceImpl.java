package com.lemon.portti.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.portti.persistence.entity.UserOrder;
import com.lemon.portti.persistence.mapper.UserOrderMapper;
import com.lemon.portti.persistence.service.UserOrderService;
import org.springframework.stereotype.Service;

@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {

}

package com.lemon.portti.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.portti.persistence.entity.UserLog;
import com.lemon.portti.persistence.mapper.UserLogMapper;
import com.lemon.portti.persistence.service.UserLogService;
import org.springframework.stereotype.Service;

@Service
public class UserLogServiceImpl extends
    ServiceImpl<UserLogMapper, UserLog> implements UserLogService {

}
